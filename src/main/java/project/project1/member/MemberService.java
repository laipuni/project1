package project.project1.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project1.admin.MemberAdminPageCondition;
import project.project1.admin.MemberAdminPageDto;
import project.project1.member.Member;
import project.project1.member.MemberRepository;

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

    public Page<MemberAdminPageDto> findAllBySearchCondition(MemberAdminPageCondition condition){
        return memberRepository
                .findAllByAdminSearchCondition(condition);
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
