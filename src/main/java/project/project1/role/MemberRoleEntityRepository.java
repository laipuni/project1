package project.project1.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRoleEntityRepository extends JpaRepository<MemberRoleEntity,Long> {
    public Optional<MemberRoleEntity> findByMemberId(Long memberId);
}
