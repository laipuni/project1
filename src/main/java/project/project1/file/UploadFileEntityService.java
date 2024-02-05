package project.project1.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project1.file.UploadFileEntity;
import project.project1.file.UploadFileEntityRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UploadFileEntityService {

    private final UploadFileEntityRepository uploadFileEntityRepository;

    @Transactional
    public UploadFileEntity save(UploadFileEntity uploadFileEntity){
        uploadFileEntityRepository.save(uploadFileEntity);
        return uploadFileEntity;
    }

    public UploadFileEntity findById(Long FileId){
        return uploadFileEntityRepository.findById(FileId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파일입니다."));
    }

    @Transactional
    public void delete(Long fileId){
        uploadFileEntityRepository.findById(fileId)
                .ifPresent(uploadFileEntityRepository::delete);
    }

}
