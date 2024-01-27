package project.project1.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project1.Security.SecurityMember;
import project.project1.board.Board;
import project.project1.member.Member;
import project.project1.reply.Reply;
import project.project1.board.BoardService;
import project.project1.member.MemberService;
import project.project1.reply.ReplyService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final ReplyService replyService;


    /**
     * 댓글 작성
     * @param boardId 해당 게시글의 Id
     * @param comment 작성한 댓글의 내용
     * @param redirect 새로고침했을 때, 댓글이 중복 등록방지를 위해 PGR
     * @return 해당 게시글로 redirect
     */
    @PostMapping("/boards/{boardId}/comment")
    public String comment(@PathVariable("boardId")Long boardId,
                          @RequestParam("comment")String comment,
                          @AuthenticationPrincipal SecurityMember securityMember,
                          RedirectAttributes redirect){

        Board findBoard = boardService.findById(boardId);
        Member findmember = memberService.findByLoginId(securityMember.getUsername());

        Long currentGroup = getNullLongConverter(replyService.getGroup(boardId));
        Reply reply = new Reply(findmember,findBoard,comment, currentGroup,0L, 0L);

        replyService.save(reply);
        redirect.addAttribute("boardId",boardId);

        return "redirect:/boards/{boardId}";
    }

    /**
     * 댓글의 답글을 작성
     * @param replycomment 작성한 댓글의 내용
     * @param boardId 해당 게시글의 Id
     * @param redirect 새로고침했을 때, 댓글이 중복 등록방지를 위해 PGR
     * @return 해당 게시글로 redirect
     */
    @PostMapping("/boards/{boardId}/reply")
    public String reply(@RequestParam("replycomment") String replycomment,
                        @PathVariable("boardId") Long boardId,
                        @RequestParam("replyId") Long replyId,
                        @AuthenticationPrincipal SecurityMember securityMember,
                        RedirectAttributes redirect){

        log.info("댓글 작성 = 답글");
        Board findBoard = boardService.findById(boardId);
        Member findmember = memberService.findByLoginId(securityMember.getUsername());
        Reply parent = replyService.findByID(replyId);

        //부모 댓글의 첫번째 댓글일 경우 null
        Long lastOrder = replyService.getMaxOrderByGroupEqAndDepthEq(boardId, parent.getGroup(),
                parent.getDepth() + 1);

        //부모의 deptH + 1과 같은 댓글 중에 order값을 찾는다
        //null일 경우 부모의 order + 1, 나머지는 최대 order값 + 1 반환
        if(lastOrder == null){
            lastOrder = parent.getOrder() + 1;
        } else {
            lastOrder = lastOrder + 1;
        }

        log.info("lastOrder={}",lastOrder);

        Reply reply = new Reply(findmember,findBoard, replycomment,
                parent.getGroup(),parent.getDepth() + 1, lastOrder);

        //부모 댓글 연관관계 설정
        parent.addChild(reply);

        //부모 댓글 밑에 끼워 넣기 위해서 밑에 댓글들 순서를 밑으로 밀기
        replyService.increaseOrder(boardId,reply.getGroup(),lastOrder);

        replyService.save(reply);
        redirect.addAttribute("boardId",boardId);

        return "redirect:/boards/{boardId}";
    }

    /**
     * 댓글 삭제
     * @param deleteReplyId 삭제할 댓글 Id
     * @param boardId 돌아갈 해당 게시글 Id
     * @param redirect PRG를 위해 선언
     * @return 해당 게시글로 redirect
     */
    @PreAuthorize(value = "hasRole('ROLE_Admin') or principal.username == #userId")
    @PostMapping("/boards/{boardId}/comment/delete")
    public String delete(
            @RequestParam("deleteReplyId") Long deleteReplyId,
            @RequestParam("loginId")String userId,
            @PathVariable("boardId") Long boardId,
            RedirectAttributes redirect
    ){
        log.info("댓글 삭제={}",deleteReplyId);
        replyService.delete(deleteReplyId);

        redirect.addAttribute("boardId",boardId);
        return "redirect:/boards/{boardId}";
    }

    /**
     * 댓글 수정
     * @param boardId 해당 게시글 ID
     * @param editcomment 수정할 댓글의 내용
     * @param editReplyId 수정할 댓글의 ID
     * @param redirect Redirect
     * @return 해당 게시글로 redirect
     */
    @PreAuthorize(value = "hasRole('ROLE_Admin') or principal.username == #userId")
    @PostMapping("/boards/{boardId}/comment/modify")
    public String modify(
            @PathVariable("boardId") Long boardId,
            @RequestParam("loginId") String userId,
            @RequestParam("editcomment")String editcomment,
            @RequestParam("editReply") Long editReplyId,
            RedirectAttributes redirect
    ){

        replyService.modify(editReplyId,editcomment);

        redirect.addAttribute("boardId",boardId);

        return "redirect:/boards/{boardId}";
    }


    /**
     * @param num null인지 아닌지 확인 할 Long Parameter
     * @return 첫 댓글일 경우 null로 넘어오니 0L로 바꾼다
     * 나머지는 현재 게시글의 댓글 그룹의 최대값 + 1을 반환
     */
    private Long getNullLongConverter(Long num){
        //첫 댓글일 경우 group은 null로 넘어온다.
        return num == null ? 0L : num + 1;
    }

}
