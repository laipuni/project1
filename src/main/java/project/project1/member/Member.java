package project.project1.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.project1.admin.MemberAdminModifyDto;
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
    private List<MemberRoleEntity> memberRoleEntities = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Heart> hearts = new ArrayList<>();

    @Builder
    public Member(String loginId, String password, String userName, LocalDate birth, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
    }

    public void addMemberRoleEntity(MemberRoleEntity role){
        this.memberRoleEntities.add(role);
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

    //<--수정 메소드-->>
    public void update(String loginId,LocalDate birth, String userName,String phoneNumber){
        this.loginId = loginId;
        this.birth = birth;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

}
