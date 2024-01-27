package project.project1.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.project1.member.MemberService;
import project.project1.utils.PagingUtils;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/member")
    @PreAuthorize(value = "hasRole('ROLE_Admin')")
    public String adminMember(Model model,
                              @ModelAttribute(value = "condition") MemberAdminPageCondition condition
    ) {
        Page<MemberAdminPageDto> members = memberService.findAllBySearchCondition(condition);


        int startPage = PagingUtils.getStartPage(condition.getPage(),10);
        int endPage = PagingUtils.getEndPage(startPage,members.getTotalPages());

        model.addAttribute("members",members);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "/admin/adminMemberPage";
    }

    @GetMapping("/resource")
    @PreAuthorize(value = "hasRole('ROLE_Admin')")
    public String adminResource(){
        return "/admin/adminResourcePage";
    }

    @GetMapping("/FAQ")
    @PreAuthorize(value = "hasRole('ROLE_Admin')")
    public String adminFAQ(){
        return "/admin/adminFAQPage";
    }
}
