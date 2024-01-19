package project.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.project1.domain.Heart;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart,Long> {

    boolean existsByBoardIdAndMemberId(Long boardId,Long memberId);

    Optional<Heart> findByBoardIdAndMemberId(Long boardId, Long memberId);

    Long countByBoardId(Long boardId);
}
