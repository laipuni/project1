package project.project1.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.project1.Security.MemberRole;
import project.project1.Security.MemberRoleEntity;

import javax.persistence.*;
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
    private Integer age;

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

    public Member(String loginId, String password, String userName, int age, String phoneNumber) {
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.age = age;
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
