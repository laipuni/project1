package project.project1.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import project.project1.board.Board;
import project.project1.file.UploadFileEntity;
import project.project1.file.UploadFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class BoardDetailDto {
    private Long boardId;
    private String userName;
    private String title;
    private String comments;
    private LocalDate creatDate;
    private int heartNum;
    private List<UploadFile> files;

    @QueryProjection
    public BoardDetailDto(Long boardId,String userName, String title, String comments,int heartNum, LocalDate creatDate) {
        this.boardId = boardId;
        this.userName = userName;
        this.title = title;
        this.heartNum = heartNum;
        this.comments = comments;
        this.creatDate = creatDate;
    }
}
