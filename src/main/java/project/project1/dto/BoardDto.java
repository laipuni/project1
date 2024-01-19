package project.project1.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import project.project1.domain.Board;
import project.project1.domain.UploadFileEntity;
import project.project1.file.UploadFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class BoardDto {
    private Long boardId;
    private String userName;
    private String loginId;
    private String title;
    private int view;
    private int heart;
    private LocalDate creatDate;

    @QueryProjection
    public BoardDto(Long boardId,String userName,String loginId, String title, int view,int heart,LocalDate creatDate) {
        this.boardId = boardId;
        this.userName = userName;
        this.loginId = loginId;
        this.title = title;
        this.view = view;
        this.heart = heart;
        this.creatDate = creatDate;
    }
}
