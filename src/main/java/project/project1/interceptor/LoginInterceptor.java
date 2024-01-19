package project.project1.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("LoginIntercepter = {}",request);

        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);

        if(session == null){
            response.sendRedirect("/login?redirectPath="+requestURI);
            return false;
        }

        return true;
    }
}
