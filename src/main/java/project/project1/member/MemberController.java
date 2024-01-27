package project.project1.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.project1.role.MemberRole;
import project.project1.role.MemberRoleEntity;
import project.project1.dto.MemberForm;
import project.project1.role.MemberRoleEntityService;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRoleEntityService memberRoleEntityService;

    @GetMapping("/add")
    public String memberAddForm(@ModelAttribute("member") MemberForm form){
        return "/member/memberForm";
    }

    @PostMapping("/add")
    public String memberAdd(@Valid @ModelAttribute("member") MemberForm form,
                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("에러 발생 ={}",bindingResult);
            return "/member/memberForm";
        }

        log.info("form = {}",form);

        Member findMember = memberService.findByLoginId(form.getLoginId());

        if(findMember != null){
            bindingResult.reject(null,"중복된 아이디가 존재합니다.");
            return "member/memberForm";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(form.getPassword());

        Member member = new Member(
                form.getLoginId(),
                encodedPassword,
                form.getUserName(),
                form.getBirth(),
                form.getPhoneNumber()
        );

        MemberRoleEntity roleEntity = new MemberRoleEntity(MemberRole.USER,member);

        memberService.join(member);
        memberRoleEntityService.save(roleEntity);

        return "redirect:/";
    }

    @GetMapping("/{memberId}")
    public String memberProfile(
            @PathVariable("memberId") Long memberId
    ){

        return "/member/memberProfile";
    }
}
