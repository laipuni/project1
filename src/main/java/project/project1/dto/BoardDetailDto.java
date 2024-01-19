package project.project1.dto;

import lombok.Data;
import project.project1.domain.Board;
import project.project1.domain.UploadFileEntity;
import project.project1.file.UploadFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class BoardDetailDto {
    private Long Id;
    private String userName;
    private String title;
    private String comments;
    private LocalDate creatDate;
    private List<UploadFile> files = new ArrayList<>();

    public BoardDetailDto(Board board){
        this.Id = board.getId();
        this.userName = board.getWriter();
        this.title = board.getTitle();
        this.comments = board.getComments();
        this.creatDate = board.getCreateTime();
        convertFile(board.getFiles());
    }

    private void convertFile(List<UploadFileEntity> uploadFileEntities){
        if(uploadFileEntities.isEmpty()){
            return;
        }

        uploadFileEntities.iterator()
                .forEachRemaining(u->files.add(u.getUploadFile()));
    }
}
