package project.project1.Controller;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.project1.Security.SecurityMember;
import project.project1.domain.Board;
import project.project1.domain.Member;
import project.project1.login.SessionConst;
import project.project1.repository.MemberRepository;
import project.project1.service.MemberService;

import java.util.ArrayList;
import java.util.List;

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
