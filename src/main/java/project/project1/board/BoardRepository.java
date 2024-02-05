package project.project1.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board,Long>,BoardRepositoryCustom {

    @Modifying
    @Query("update Board b set b.view = b.view + 1 where b.id=:boardId")
    void increaseBoardView(@Param("boardId") Long boardId);

    boolean existsByIdAndMemberId(Long Id,Long memberId);
}
