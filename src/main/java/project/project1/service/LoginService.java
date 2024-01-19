package project.project1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project1.login.LoginForm;
import project.project1.domain.Member;
import project.project1.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(LoginForm loginForm){
        return memberRepository.findByLoginId(loginForm.getLoginId())
                .filter(member -> member.getPassword().equals(loginForm.getPassword()))
                .orElse(null);

    }

}
