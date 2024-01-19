package project.project1.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project1.Security.SecurityMember;
import project.project1.domain.Board;
import project.project1.domain.Heart;
import project.project1.domain.Member;
import project.project1.login.SessionConst;
import project.project1.service.BoardService;
import project.project1.service.HeartService;
import project.project1.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {

    private final HeartService heartService;
    private final MemberService memberService;
    private final BoardService boardService;

    @PostMapping("/add")
    public String addHeart(
            @RequestParam("boardId") Long boardId,
            @AuthenticationPrincipal SecurityMember securityMember,
            RedirectAttributes redirect
    ){

        Member findMember = memberService.findByLoginId(securityMember.getUsername());
        Board findBoard = boardService.findById(boardId);

        Heart heart = new Heart(findBoard,findMember);

        heartService.save(heart);

        redirect.addAttribute("boardId",boardId);

        return "redirect:/boards/{boardId}";
    }

    @PostMapping("/delete")
    public String deleteHeart(
            @RequestParam("boardId") Long boardId,
            @AuthenticationPrincipal SecurityMember securityMember,
            RedirectAttributes redirect
    ){

        heartService.delete(boardId, securityMember.getMemberId());

        redirect.addAttribute("boardId",boardId);

        return "redirect:/boards/{boardId}";
    }
}
