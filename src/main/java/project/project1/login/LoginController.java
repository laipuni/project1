package project.project1.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.project1.Security.SecurityMember;
import project.project1.domain.Member;
import project.project1.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@RequestParam(name = "error", defaultValue = "false")boolean error,
                            @RequestParam(name = "exception",required = false)String exception,
                            Model model){

        model.addAttribute("error",error);
        model.addAttribute("exception",exception);

        log.info("error={},exception={}",error,exception);

        return "/login/loginForm";
    }
}
