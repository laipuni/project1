package project.project1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project1.dto.BoardModifyForm;
import project.project1.dto.BoardSearchCondition;
import project.project1.domain.Board;
import project.project1.dto.BoardDto;
import project.project1.repository.BoardRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<BoardDto> findAllBoard(Pageable pageable){
        return boardRepository.findAllBoard(pageable);
    }

    public Page<BoardDto> findAllSearchCondition(BoardSearchCondition condition){
        return boardRepository.findAllBySearchCondition(condition);
    }

    @Transactional
    public Board save(Board board){
        boardRepository.save(board);
        return board;
    }

    public Board findById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물은 존재 하지않습니다."));
    }

    public boolean exitsByIdAndMemberId(Long boardId, Long memberId){
        log.info("회원ID ={}, 게시판ID={}",memberId,boardId);
        return boardRepository.existsByIdAndMemberId(boardId,memberId);
    }

    @Transactional
    public void delete(Long boardId){
        boardRepository.findById(boardId)
                .ifPresent(boardRepository :: delete);
    }

    public void increaseBoardView(Long boardId) {
        boardRepository.increaseBoardView(boardId);
    }

    @Transactional
    public void update(BoardModifyForm form) {
        boardRepository.findById(form.getBoardId())
                .ifPresent(b->b.changeBoard(form.getTitle(), form.getComments()));
    }
}
