package project.project1.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import project.project1.domain.QReply;
import project.project1.domain.Reply;
import project.project1.dto.QReplyDto;
import project.project1.dto.ReplyDto;

import javax.persistence.EntityManager;
import java.util.List;

import static project.project1.domain.QReply.reply;

public class ReplyRepositoryCustomImpl implements ReplyRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ReplyRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ReplyDto> findReplyBoardId(Pageable pageable, Long boardId) {
        List<ReplyDto> contents = queryFactory
                .select(new QReplyDto(
                        reply.id,
                        reply.member.userName,
                        reply.member.loginId,
                        reply.comments,
                        reply.createTime,
                        reply.depth
                ))
                .from(reply)
                .join(reply.member)
                .where(reply.board.id.eq(boardId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reply.group.desc(),
                        reply.order.asc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(reply.count())
                .from(reply)
                .where(reply.board.id.eq(boardId));

        return PageableExecutionUtils.getPage(contents,pageable,countQuery::fetchOne);
    }
}
