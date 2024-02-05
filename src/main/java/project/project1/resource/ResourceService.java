package project.project1.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResourceService {

    private final ResourceRepository resourceRepository;

    @Transactional
    public Resource save(Resource resource){
        resourceRepository.save(resource);
        return resource;
    }

    public Resource findById(Long resourceId){
        return resourceRepository.findById(resourceId)
                .orElseThrow(() -> new IllegalArgumentException("해당 자원은 없는 자원입니다. id = " + resourceId));
    }

    @Transactional
    public void deleteById(Long resourceId){
        resourceRepository.deleteById(resourceId);
    }

}

