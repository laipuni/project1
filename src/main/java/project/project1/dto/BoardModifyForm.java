package project.project1.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import project.project1.domain.Board;
import project.project1.domain.UploadFileEntity;
import project.project1.file.UploadFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BoardModifyForm {

    private Long boardId;

    @NotBlank
    private String title;

    @NotBlank
    private String comments;

    public BoardModifyForm(Long boardId, String title, String comments) {
        this.boardId = boardId;
        this.title = title;
        this.comments = comments;
    }

}
