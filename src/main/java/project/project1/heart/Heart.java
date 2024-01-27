package project.project1.heart;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.project1.board.Board;
import project.project1.member.Member;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Heart {

    @Id @GeneratedValue
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


    //연관 관계 메소드
    public Heart(Board board, Member member){
        this.board = board;
        board.addHeart(this);

        this.member = member;
        member.addHeart(this);
    }

}
