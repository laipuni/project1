package project.project1.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.project1.service.MemberService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/member")
    @PreAuthorize(value = "hasRole('ROLE_Admin')")
    public String adminMember(Model model){
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
