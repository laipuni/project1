package project.project1.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import project.project1.dto.BoardSearchCondition;
import project.project1.domain.Board;
import project.project1.dto.BoardDto;
import project.project1.dto.QBoardDto;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static project.project1.domain.QBoard.board;
import static project.project1.domain.QMember.member;


public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BoardDto> findAllBoard(Pageable pageable) {
        List<BoardDto> contents = queryFactory
                .select(new QBoardDto(
                        board.id,
                        board.member.userName,
                        board.member.loginId,
                        board.title,
                        board.view,
                        board.hearts.size(),
                        board.createTime
                ))
                .from(board)
                .join(board.member,member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Board> boardJPAQuery = queryFactory
                .selectFrom(board);

        return PageableExecutionUtils.getPage(contents,pageable,boardJPAQuery::fetchCount);
    }

    @Override
    public Page<BoardDto> findAllBySearchCondition(BoardSearchCondition condition) {
        Pageable pageable = createPageable(condition);

        List<BoardDto> contents = queryFactory.
                select(
                        new QBoardDto(
                                board.id,
                                board.member.userName,
                                board.member.loginId,
                                board.title,
                                board.view,
                                board.hearts.size(),
                                board.createTime
                        )
                )
                .from(board)
                .join(board.member)
                .where(filter(condition.getField(), condition.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders(pageable.getSort()))
                .fetch();

        JPAQuery<Board> countQuery = queryFactory
                .selectFrom(board)
                .where(filter(condition.getField(), condition.getKeyword()));

        return PageableExecutionUtils.getPage(contents,pageable,countQuery::fetchCount);
    }

    private static Pageable createPageable(BoardSearchCondition condition) {
        String direction = condition.getDirection();
        Sort.Direction direct = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direct, condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage() - 1, 10,Sort.by(order));
        return pageable;
    }


    private OrderSpecifier[] orders(Sort sort) {
        if(sort.isEmpty()){
            return new OrderSpecifier[0];
        }

        List<OrderSpecifier> spec = new ArrayList<>();

        for (Sort.Order o : sort) {
            Order direction = o.isAscending() ? Order.ASC : Order.DESC;

            switch (o.getProperty()){
                case "view":
                    spec.add(new OrderSpecifier(direction,board.view));
                    //같은 조회수 일경우 최신순으로
                    spec.add(new OrderSpecifier(Order.ASC,board.id));
                    break;
                case "Id":
                    spec.add(new OrderSpecifier(direction,board.id));
                    break;
                case "heart":
                    spec.add(new OrderSpecifier(direction,board.hearts.size()));
                    //같은 조회수 일경우 최신순으로
                    spec.add(new OrderSpecifier(Order.ASC,board.id));
                    break;
            }
        }

        return spec.toArray(new OrderSpecifier[spec.size()]);
    }


    private BooleanBuilder filter(String field,String keyword){
        if(!StringUtils.hasText(keyword)){
            return null;
        }

        if(field.equals("writer")){
            return new BooleanBuilder(board.member.userName.contains(keyword));
        } else {
            //title
            return new BooleanBuilder(board.title.contains(keyword));
        }
    }
}
