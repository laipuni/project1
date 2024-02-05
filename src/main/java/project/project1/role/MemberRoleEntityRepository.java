package project.project1.role;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRoleEntityRepository extends JpaRepository<MemberRoleEntity,Long>,MemberRoleEntityRepositoryCustom {

    @Query("select m from MemberRoleEntity m join fetch m.member where m.member.id= :memberId")
    public Optional<MemberRoleEntity> findMemberId(@Param("memberId") Long memberId);

    @Modifying
    @Query(value = "delete from MemberRoleEntity m where m.member.id= :memberId")
    public void deleteAllByMemberId(@Param("memberId") Long memberId);
}
