package project.project1.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.project1.dto.BoardSearchCondition;
import project.project1.dto.BoardDto;

public interface BoardRepositoryCustom {
    Page<BoardDto> findAllBoard(Pageable pageable);

    Page<BoardDto> findAllBySearchCondition(BoardSearchCondition condition);
}
