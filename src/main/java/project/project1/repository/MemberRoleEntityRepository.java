package project.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import project.project1.Security.MemberRoleEntity;

import java.util.Optional;

public interface MemberRoleEntityRepository extends JpaRepository<MemberRoleEntity,Long> {
    public Optional<MemberRoleEntity> findByMemberId(Long memberId);
}
