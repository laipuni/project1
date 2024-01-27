package project.project1.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.project1.role.MemberRoleEntity;
import project.project1.domain.BaseEntity;
import project.project1.board.Board;
import project.project1.heart.Heart;
import project.project1.reply.Reply;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(nullable = false,name ="member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MemberRoleEntity> role = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Heart> hearts = new ArrayList<>();

    public Member(String loginId, String password, String userName, LocalDate birth, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
    }

    public void addRole(MemberRoleEntity role){
        this.role.add(role);
    }

    public void addBoard(Board board) {
        boards.add(board);
    }

    public void addReply(Reply reply){
        replies.add(reply);
    }

    public void addHeart(Heart heart) {
        hearts.add(heart);
    }
}
