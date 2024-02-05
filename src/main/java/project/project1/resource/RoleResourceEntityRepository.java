package project.project1.resource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourceEntityRepository extends JpaRepository<RoleResourceEntity,Long> {
    void deleteByResourceId(Long resourceId);
}
