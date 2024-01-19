package project.project1.Security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.project1.domain.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRoleEntity {

    @Id @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "memberId")
    private Member member;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public MemberRoleEntity(MemberRole role,Member member){
        this.role = role;
        this.member = member;
        member.addRole(this);
    }
}
