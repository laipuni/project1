package project.project1.Security;

import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

@Getter
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private Long memberId;
    private String memberName;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.memberId = (Long) request.getAttribute("id");
        this.memberName =(String) request.getAttribute("memberName");
    }

}
