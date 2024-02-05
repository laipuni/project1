package project.project1.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberRoleEntityService {

    private final MemberRoleEntityRepository memberRoleEntityRepository;

    @Transactional
    public MemberRoleEntity save(MemberRoleEntity memberRoleEntity){
        memberRoleEntityRepository.save(memberRoleEntity);
        return memberRoleEntity;
    }

    public MemberRoleEntity findById(Long memberRoleEntityId){
        return memberRoleEntityRepository.findById(memberRoleEntityId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Role은 없습니다. id=" + memberRoleEntityId));
    }

    @Transactional
    public void delete(Long memberRoleEntityId){
       memberRoleEntityRepository.deleteById(memberRoleEntityId);
    }

    @Transactional
    public void deleteAllByMemberId(Long memberId) {
        memberRoleEntityRepository.deleteAllByMemberId(memberId);
    }
}
