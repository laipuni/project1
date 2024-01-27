package project.project1.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRoleEntityService {

    private MemberRoleEntityRepository memberRoleEntityRepository;

    @Transactional
    public MemberRoleEntity save(MemberRoleEntity memberRoleEntity){
        memberRoleEntityRepository.save(memberRoleEntity);
        return memberRoleEntity;
    }

    public MemberRoleEntity findById(Long memberRoleEntityId){
        return memberRoleEntityRepository.findById(memberRoleEntityId)
                .orElseThrow(() -> new IllegalArgumentException("부여받은 Role이 없습니다."));
    }

    public MemberRoleEntity findByMemberId(Long memberId){
        return memberRoleEntityRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("부여받은 Role이 없습니다."));
    }

    @Transactional
    public void removeRole(MemberRoleEntity memberRoleEntity){
       memberRoleEntityRepository.delete(memberRoleEntity);
    }

}
