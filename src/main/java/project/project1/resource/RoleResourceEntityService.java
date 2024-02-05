package project.project1.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleResourceEntityService {

    private final RoleResourceEntityRepository roleResourceEntityRepository;

    @Transactional
    public RoleResourceEntity save(RoleResourceEntity roleResourceEntity){
        roleResourceEntityRepository.save(roleResourceEntity);
        return roleResourceEntity;
    }

    @Transactional
    public void deleteByResourceId(Long resourceId){
        roleResourceEntityRepository.deleteByResourceId(resourceId);
    }
}
