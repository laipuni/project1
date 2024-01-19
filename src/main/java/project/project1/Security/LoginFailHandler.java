package project.project1.Security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMassage = "아이디 혹은 비밀번호가 잘못되었습니다.";

        if(exception instanceof BadCredentialsException){
            errorMassage = "아이디 혹은 비밀번호가 잘못되었습니다.";
        } else if(exception instanceof UsernameNotFoundException){
            errorMassage = "동록되지 않은 회원입니다.";
        }

        errorMassage = URLEncoder.encode(errorMassage, StandardCharsets.UTF_8);
        setDefaultFailureUrl("/login?error=true&exception=" + errorMassage);
        
        super.onAuthenticationFailure(request,response,exception);
    }
}
