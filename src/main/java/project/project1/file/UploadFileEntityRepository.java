package project.project1.file;

import org.springframework.data.jpa.repository.JpaRepository;
import project.project1.file.UploadFileEntity;

public interface UploadFileEntityRepository extends JpaRepository<UploadFileEntity,Long> {
}
