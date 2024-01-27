package project.project1.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

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
