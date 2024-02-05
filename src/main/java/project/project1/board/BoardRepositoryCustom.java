package project.project1.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.project1.dto.BoardDetailDto;
import project.project1.dto.BoardSearchCondition;
import project.project1.dto.BoardDto;

import java.util.Optional;

public interface BoardRepositoryCustom {

    Page<BoardDto> findAllBySearchCondition(BoardSearchCondition condition);

    Optional<BoardDetailDto> findBoardDetailDtoById(Long boardId);
}
