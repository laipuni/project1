package project.project1.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

    @EntityGraph(attributePaths = {"memberRoleEntities"})
    Optional<Member> findByLoginId(String LoginId);

    @Override
    @EntityGraph(attributePaths = {"memberRoleEntities"})
    Optional<Member> findById(Long LoginId);
}
