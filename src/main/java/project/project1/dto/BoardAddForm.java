package project.project1.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class BoardAddForm {
    @NotBlank
    private String title;

    private List<MultipartFile> imageFiles;

    @NotBlank
    private String comments;
}
