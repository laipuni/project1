package project.project1.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import project.project1.admin.MemberAdminPageCondition;
import project.project1.admin.MemberAdminPageDto;
import project.project1.admin.QMemberAdminPageDto;
import project.project1.domain.QMember;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static project.project1.domain.QMember.member;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MemberAdminPageDto> findAllByAdminSearchCondition(MemberAdminPageCondition condition) {
        Pageable page = getPageble(condition);

        List<MemberAdminPageDto> result = queryFactory
                .select(
                        new QMemberAdminPageDto(
                                member.id,
                                member.userName,
                                member.loginId,
                                member.role
                        )
                )
                .from(member)
                .join(member.role)
                .where(getMemberSearchFilter(condition))
                .orderBy(getSortBuilder(page.getSort()))
                .fetch();

        JPAQuery<MemberAdminPageDto> countQuery = queryFactory
                .select(
                        new QMemberAdminPageDto(
                                member.id,
                                member.userName,
                                member.loginId,
                                member.role
                        )
                )
                .from(member)
                .join(member.role)
                .where(getMemberSearchFilter(condition));

        return PageableExecutionUtils.getPage(result,page,countQuery::fetchCount);
    }

    private OrderSpecifier[] getSortBuilder(Sort sort){
        if(sort == null){
            return new OrderSpecifier[0];
        }

        List<OrderSpecifier> orders = new ArrayList<>();

        for (Sort.Order order : sort) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;

            switch (order.getProperty()){
                case "creatTime":
                    orders.add(new OrderSpecifier(direction,member.createTime));
                    break;
                case "":
                    break;
            }
        }

        return orders.toArray(new OrderSpecifier[orders.size()]);
    }

    private BooleanBuilder getMemberSearchFilter(MemberAdminPageCondition condition) {
        return dateEq(condition.getStartDate(),condition.getEndDate())
                .and(roleEq(condition.getRole()))
                .and(keyWordContain(condition.getKeyword()));
    }

    private BooleanBuilder roleEq(String role){
        if(!StringUtils.hasText(role)){
            return new BooleanBuilder();
        }

        return new BooleanBuilder();
    }

    private BooleanBuilder dateEq(LocalDate startDate, LocalDate endDate){
        return startDateGOE(startDate).and(endDateGOE(endDate));
    }

    private BooleanBuilder endDateGOE(LocalDate endDate){
        if(endDate == null){
            return new BooleanBuilder();
        }

        return new BooleanBuilder(member.createTime.loe(endDate));
    }

    private BooleanBuilder startDateGOE(LocalDate startDate){
        if(startDate == null){
            return new BooleanBuilder();
        }

        return new BooleanBuilder(member.createTime.goe(startDate));
    }

    private BooleanBuilder keyWordContain(String keyword){
        if(!StringUtils.hasText(keyword)){
            return new BooleanBuilder();
        }

        return new BooleanBuilder(member.userName.contains(keyword));
    }

    private Pageable getPageble(MemberAdminPageCondition condition) {
        Sort.Direction direction = condition.getDirection().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort.Order order = new Sort.Order(direction, condition.getSort());
        Pageable page = PageRequest.of(condition.getPage() - 1,10,Sort.by(order));
        return page;
    }
}
