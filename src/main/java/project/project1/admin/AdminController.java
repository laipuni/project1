package project.project1.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project1.member.Member;
import project.project1.member.MemberService;
import project.project1.role.MemberRole;
import project.project1.role.MemberRoleEntityService;
import project.project1.utils.PagingUtils;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final MemberRoleEntityService memberRoleEntityService;

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

        return "/admin/member/adminMember";
    }

    @GetMapping("/member/{id}/modify")
    @PreAuthorize(value = "hasRole('ROLE_Admin')")
    public String adminMemberModifyForm(@PathVariable("id") Long memberId,Model model){
        MemberAdminModifyDto memberAdminModifyDto = memberService.findMemberAdminModifyDtoById(memberId);

        model.addAttribute("memberRoles", MemberRole.values());
        model.addAttribute("form",memberAdminModifyDto);

        return "admin/member/adminMemberModify";
    }

    @PostMapping("/member/{id}/modify")
    public String adminMemberModify(
            @PathVariable("id") Long memberId,
            @Valid @ModelAttribute("form") MemberAdminModifyDto memberAdminModifyDto,
            BindingResult bindingResult, RedirectAttributes attributes,
            Model model
    ){
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            model.addAttribute("memberRoles",MemberRole.values());
            return "/admin/member/adminMemberModify";
        }

        memberRoleEntityService.deleteAllByMemberId(memberId);
        memberService.updateAdmin(memberId,memberAdminModifyDto);
        attributes.addAttribute("status",true);

        return "redirect:/admin/member";
    }

    @GetMapping("/resource")
    @PreAuthorize(value = "hasRole('ROLE_Admin')")
    public String adminResource(){
        return "admin/resource/adminResource";
    }

    @GetMapping("/FAQ")
    @PreAuthorize(value = "hasRole('ROLE_Admin')")
    public String adminFAQ(){
        return "admin/FAQ/adminFAQ";
    }
}
