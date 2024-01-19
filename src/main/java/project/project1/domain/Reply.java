package project.project1.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Reply parent;

    @OneToMany(mappedBy = "parent",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Reply> child = new ArrayList<>();

    //좋아요,추천 수
    private int heart;

    //댓글 그룹
    @Column(name = "group_num")
    private Long group;
    //해당 댓글 그룹 내에서 계층
    @Column(name = "group_depth")
    private Long depth;
    //해당 댓글 그룹 내의 계층에서 순서
    @Column(name = "group_order")
    private Long order;

    //부모,자식(댓글,대댓글) 연관관계 설정 메소드

    public void addChild(Reply child){
        this.child.add(child);
        child.changeParent(this);
    }

    public void changeParent(Reply parent){
        this.parent = parent;
    }

    public Reply(Member member, Board board,String comments,
                 Long group,Long depth,Long order){
        this.comments = comments;
        this.member = member;
        this.board = board;
        this.group = group;
        this.depth = depth;
        this.order = order;

        member.addReply(this);
        board.addReply(this);
    }

    public void modifyComment(String comment){
        this.comments = comment;
    }

}
