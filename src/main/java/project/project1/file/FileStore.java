package project.project1.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String path){
        return fileDir + path;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                uploadFiles.add(storeFile(multipartFile));
            }
        }

        return uploadFiles;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename)));
        return new UploadFile(originalFilename,storeFilename);
    }

    public String createStoreFileName(String originalName){
        String ext = extract(originalName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    public String extract(String originalName){
        int pos = originalName.lastIndexOf('.');
        String ext = originalName.substring(pos+1);
        return ext;
    }
}
