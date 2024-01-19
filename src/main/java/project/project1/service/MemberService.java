package project.project1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project1.domain.Member;
import project.project1.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member join(Member member){
        memberRepository.save(member);
        return member;
    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId)
                .orElse(null);
    }

    public Member findByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId)
                .orElse(null);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

//    todo 회원 수정
//    @Transactional
//    public void update(){
//
//    }
}
