package project.project1.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project1.reply.Reply;
import project.project1.dto.ReplyDto;
import project.project1.reply.ReplyRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Transactional
    public Reply save(Reply reply){
        replyRepository.save(reply);
        return reply;
    }

    public Page<ReplyDto> findReplyDtoByBoardId(Pageable pageable, Long boardId){
        return replyRepository.findReplyBoardId(pageable, boardId);
    }

    public Reply findByID(Long replyId){
        return replyRepository.findById(replyId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 댓글입니다"));
    }

    public void increaseOrder(Long boardId,Long groupId,Long order){
        replyRepository.increaseOrderByDepthEq(boardId,groupId,order);
    }

    public Long getGroup(Long boardId){
        return replyRepository.getGroup(boardId);
    }

    //댓글 삭제 자식 댓글도 함께 삭제
    @Transactional
    public void delete(Long replyId){
        replyRepository.deleteById(replyId);
    }

    //현재 댓글 그룹에 같은 depth가진 댓글중 order가 가장 높은 값을 반환
    public Long getMaxOrderByGroupEqAndDepthEq(Long boardId,Long group,Long depth){
        return replyRepository.getMaxOrderByGroupAndDepth(boardId,group,depth);
    }

    //댓글 내용 수정
    @Transactional
    public void modify(Long replyId,String comment){
        replyRepository.findById(replyId)
                .ifPresent(r -> r.modifyComment(comment));
    }
}
