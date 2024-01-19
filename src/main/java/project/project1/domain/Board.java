package project.project1.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import project.project1.dto.BoardModifyForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String comments;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UploadFileEntity> files = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

    private int view;

    public void changeMember(Member member){
        this.member = member;
    }

    //연관 관계 메소드
    public void changeBoard(String title,String comments){
        this.title = title;
        this.comments = comments;
    }

    public static Board createBoard(Member member,String title,String comments){
        Board board = new Board();
        board.changeBoard(title,comments);
        board.changeMember(member);
        member.addBoard(board);

        return board;
    }

    public void addFile(UploadFileEntity uploadFileEntity){
        files.add(uploadFileEntity);
    }

    public void addReply(Reply reply){
        replies.add(reply);
    }

    public void addHeart(Heart heart){
        hearts.add(heart);
    }

    public String getWriter(){
        return member.getUserName();
    }
}
