package project.project1.member;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import project.project1.admin.MemberAdminPageCondition;
import project.project1.admin.MemberAdminPageDto;
import project.project1.role.MemberRole;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static project.project1.member.QMember.member;
import static project.project1.role.QMemberRoleEntity.memberRoleEntity;

@Slf4j
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MemberAdminPageDto> findAllByAdminSearchCondition(MemberAdminPageCondition condition) {
        Pageable page = getPageable(condition);

        List<Member> result = queryFactory
                .select(member)
                .from(member)
                .join(member.role).fetchJoin()
                .where(getMemberSearchFilter(condition))
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .orderBy(getSortBuilder(page.getSort()))
                .fetch();

        log.info("result = {}",result);

        List<MemberAdminPageDto> content = new ArrayList<>();

        result.
                iterator()
                .forEachRemaining(m -> content.add(new MemberAdminPageDto(m)));

        JPAQuery<Long> count = queryFactory
                .select(member.count())
                .from(member)
                .join(member.role)
                .where(getMemberSearchFilter(condition));

        return PageableExecutionUtils.getPage(content,page,count::fetchOne);
    }

    private OrderSpecifier[] getSortBuilder(Sort sort){
        if(sort == null){
            return new OrderSpecifier[0];
        }

        List<OrderSpecifier> orders = new ArrayList<>();

        for (Sort.Order order : sort) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;

            switch (order.getProperty()){
                case "createTime":
                    orders.add(new OrderSpecifier(direction,member.createTime));
                case "birth":
                    orders.add(new OrderSpecifier(direction,member.birth));
                    break;
            }
        }

        return orders.toArray(new OrderSpecifier[orders.size()]);
    }

    private BooleanBuilder getMemberSearchFilter(MemberAdminPageCondition condition) {
        return dateEq(condition.getStartDate(),condition.getEndDate())
                .and(roleEq(condition.getRole()))
                .and(keyWordContain(condition.getKeywordType(), condition.getKeyword()))
                .and(birthEq(condition.getBirthStartDate(),condition.getBirthEndDate()));
    }

    private BooleanBuilder roleEq(MemberRole role){
        if(role == null){
            return new BooleanBuilder();
        }

        //EntityRole테이블에 role_name이 "ROLE_Admin"인 EntityRole의 memberId와 같은 member가 존재하는지

        return new BooleanBuilder(
                member.id.in(
                        JPAExpressions
                                .select(memberRoleEntity.member.id)
                                .from(memberRoleEntity)
                                .where(memberRoleEntity.role.eq(role))
                )
        );
    }

    private BooleanBuilder birthEq(LocalDate startBirthDate,LocalDate endBirthDate){
        return startBirthDateGOE(startBirthDate).and(endBirthDateLOE(endBirthDate));
    }

    private BooleanBuilder endBirthDateLOE(LocalDate endBirthDate) {
        if(endBirthDate == null){
            return new BooleanBuilder();
        }

        return new BooleanBuilder(member.birth.loe(endBirthDate));
    }

    private BooleanBuilder startBirthDateGOE(LocalDate startBirthDate) {
        if(startBirthDate == null){
            return new BooleanBuilder();
        }

        return new BooleanBuilder(member.birth.goe(startBirthDate));
    }

    private BooleanBuilder dateEq(LocalDate startDate, LocalDate endDate){
        return startDateGOE(startDate).and(endDateLOE(endDate));
    }

    private BooleanBuilder endDateLOE(LocalDate endDate){
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

    private BooleanBuilder keyWordContain(String keywordType,String keyword){
        if(!StringUtils.hasText(keywordType) || !StringUtils.hasText(keyword)){
            return new BooleanBuilder();
        }

        if("loginId".equals(keywordType)){
            return new BooleanBuilder(member.loginId.contains(keyword));
        } else{
            return new BooleanBuilder(member.userName.contains(keyword));
        }
    }

    private Pageable getPageable(MemberAdminPageCondition condition) {
        Sort.Direction direction = condition.getDirection().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, condition.getSort());
        Pageable page = PageRequest.of(condition.getPage() - 1,10,Sort.by(order));
        return page;
    }
}
