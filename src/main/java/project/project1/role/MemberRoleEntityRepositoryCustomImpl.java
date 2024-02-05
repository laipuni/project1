package project.project1.role;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static project.project1.role.QMemberRoleEntity.memberRoleEntity;

public class MemberRoleEntityRepositoryCustomImpl implements MemberRoleEntityRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MemberRoleEntityRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Map<Long, List<MemberRole>> findAllInMemberIds(List<Long> memberIds) {

        List<MemberRoleEntity> result = queryFactory
                .select(memberRoleEntity)
                .from(memberRoleEntity)
                .join(memberRoleEntity.member).fetchJoin()
                .where(memberRoleEntity.member.id.in(memberIds))
                .fetch();

        return result.stream()
                .collect(Collectors.groupingBy(
                        MemberRoleEntity::getMemberId,
                        Collectors.mapping(MemberRoleEntity::getMemberRole,Collectors.toList())
                ));
    }
}
