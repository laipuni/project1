package project.project1.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.project1.role.*;
import project.project1.dto.MemberForm;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRoleEntityService memberRoleEntityService;
    private final RoleService roleService;

    @GetMapping("/add")
    public String memberAddForm(@ModelAttribute("member") MemberForm form){
        return "/member/memberForm";
    }

    @PostMapping("/add")
    public String memberAdd(@Valid @ModelAttribute("member") MemberForm form,
                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/member/memberForm";
        }

        memberService.signUp(form);

        return "redirect:/";
    }

    @GetMapping("/{memberId}")
    public String memberProfile(
            @PathVariable("memberId") Long memberId
    ){
        return "/member/memberProfile";
    }
}
