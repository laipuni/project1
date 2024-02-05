package project.project1.dto;

import lombok.Getter;
import lombok.Setter;
import project.project1.board.Board;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class BoardModifyForm {

    private Long boardId;

    @NotBlank
    private String title;

    @NotBlank
    private String comments;

    public BoardModifyForm(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.comments = board.getComments();
    }

}
