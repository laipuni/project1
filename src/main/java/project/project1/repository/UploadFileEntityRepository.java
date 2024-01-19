package project.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project1.domain.UploadFileEntity;

public interface UploadFileEntityRepository extends JpaRepository<UploadFileEntity,Long> {
}
