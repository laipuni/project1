package project.project1.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
