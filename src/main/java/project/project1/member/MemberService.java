package project.project1.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project1.admin.MemberAdminModifyDto;
import project.project1.admin.MemberAdminPageCondition;
import project.project1.admin.MemberAdminPageDto;
import project.project1.dto.MemberForm;
import project.project1.member.Member;
import project.project1.member.MemberRepository;
import project.project1.role.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleEntityRepository memberRoleEntityRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Member signUp(MemberForm form){
        Member member = Member.builder()
                .loginId(form.getLoginId())
                .userName(form.getUserName())
                .password(encoder.encode(form.getPassword()))
                .phoneNumber(form.getPhoneNumber())
                .birth(form.getBirth())
                .build();


        Role userRole = roleRepository.findByMemberRole(MemberRole.USER)
                .orElseThrow(() -> new IllegalArgumentException("해당 권한은 없는 권한입니다. memberRole" + MemberRole.USER));

        MemberRoleEntity roleEntity = new MemberRoleEntity(member,userRole);

        memberRepository.save(member);
        memberRoleEntityRepository.save(roleEntity);

        return member;
    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. id=" + memberId));
    }

    public MemberAdminModifyDto findMemberAdminModifyDtoById(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. id=" + memberId));

        return new MemberAdminModifyDto(member);
    }

    public Member findByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 loginId를 가진 유저는 없습니다. loginId=" + loginId));
    }

    public Page<MemberAdminPageDto> findAllBySearchCondition(MemberAdminPageCondition condition){
        Page<MemberAdminPageDto> result = memberRepository.findAllByAdminSearchCondition(condition);

        List<Long> memberIds = memberIdtoList(result);

        Map<Long, List<MemberRole>> memberRoleMap = memberRoleEntityRepository.findAllInMemberIds(memberIds);

        result.iterator()
                .forEachRemaining(m->m.setAuthorizes(memberRoleMap.get(m.getMemberId())));

        return result;
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    @Transactional
    public void updateAdmin(Long memberId, MemberAdminModifyDto memberAdminModifyDto){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다. id = "+memberId));

        member.update(
                memberAdminModifyDto.getLoginId(),
                memberAdminModifyDto.getBirth(),
                memberAdminModifyDto.getUserName(),
                memberAdminModifyDto.getPhoneNumber()
        );

        //수정된 권환을 부여
        for(MemberRole memberRole : memberAdminModifyDto.getRoles()){
            Role role = roleRepository.findByMemberRole(memberRole)
                    .orElseThrow(IllegalArgumentException::new);

            memberRoleEntityRepository.save(new MemberRoleEntity(member,role));
        }
    }

    private List<Long> memberIdtoList(Page<MemberAdminPageDto> result) {
        return result
                .stream()
                .map(MemberAdminPageDto::getMemberId)
                .collect(Collectors.toList());
    }
}
