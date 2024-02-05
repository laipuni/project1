package project.project1.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public Role saveRole(Role role){
        roleRepository.save(role);
        return role;
    }

    public Role findById(Long roleId){
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않습니다."));
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role findByMemberRole(MemberRole role){
        return roleRepository.findByMemberRole(role)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않습니다."));
    }
}
