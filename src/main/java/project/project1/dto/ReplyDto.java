package project.project1.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReplyDto {
    private Long id;
    private String writer;
    private String loginId;
    private String comment;
    private LocalDate creatTime;
    private Long depth;

    @QueryProjection
    public ReplyDto(Long id,String writer, String loginId, String comment, LocalDate creatTime,Long depth) {
        this.id = id;
        this.writer = writer;
        this.loginId = loginId;
        this.comment = comment;
        this.creatTime = creatTime;
        this.depth = depth;
    }
}
