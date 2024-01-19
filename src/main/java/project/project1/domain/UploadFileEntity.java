package project.project1.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.project1.domain.Board;
import project.project1.file.UploadFile;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFileEntity {

    @Id @GeneratedValue
    private Long Id;

    @Embedded
    private UploadFile uploadFile;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public UploadFileEntity(UploadFile uploadFile, Board board) {
        this.uploadFile = uploadFile;
        changeBoard(board);
    }

    public void changeBoard(Board board){
        this.board = board;
        board.addFile(this);
    }
}
