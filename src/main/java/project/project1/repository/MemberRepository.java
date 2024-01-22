package project.project1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.project1.admin.MemberAdminPageDto;
import project.project1.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

    @EntityGraph(attributePaths = {"role"})
    Optional<Member> findByLoginId(String LoginId);
}
