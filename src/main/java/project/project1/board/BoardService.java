package project.project1.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project1.dto.BoardDetailDto;
import project.project1.dto.BoardModifyForm;
import project.project1.dto.BoardSearchCondition;
import project.project1.board.Board;
import project.project1.dto.BoardDto;
import project.project1.board.BoardRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<BoardDto> findAllSearchCondition(BoardSearchCondition condition){
        return boardRepository.findAllBySearchCondition(condition);
    }

    public Board findById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));
    }

    public void increaseBoardView(Long boardId) {
        boardRepository.increaseBoardView(boardId);
    }

    public boolean exitsByIdAndMemberId(Long boardId, Long memberId){
        return boardRepository.existsByIdAndMemberId(boardId,memberId);
    }

    public BoardDetailDto findBoardDetailDtoById(Long boardId) {
        return boardRepository.findBoardDetailDtoById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 없습니다. id = " + boardId));
    }

    public BoardModifyForm findBoardModifyFormById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다. id=" + boardId));

        return new BoardModifyForm(board);
    }

    @Transactional
    public Board save(Board board){
        boardRepository.save(board);
        return board;
    }

    @Transactional
    public void delete(Long boardId){
       boardRepository.deleteById(boardId);
    }

    @Transactional
    public void update(BoardModifyForm form) {
        boardRepository.findById(form.getBoardId())
                .ifPresent(b->b.changeBoard(form.getTitle(), form.getComments()));
    }
}
