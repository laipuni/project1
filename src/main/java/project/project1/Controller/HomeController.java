package project.project1.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.project1.Security.SecurityMember;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping(value = {"/","home"})
    public String home( Model model ,
                        @AuthenticationPrincipal SecurityMember securityMember
    ){
        if(securityMember != null){
            log.info("회원 {} loginHome접속",securityMember.getName());
            model.addAttribute("username",securityMember.getName());
            return "login/loginHome";
        }

        log.info("비회원 home");
        return "/home";
    }

}
