package project.project1.role;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.project1.member.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRoleEntity {

    @Id @GeneratedValue
    @Column(name = "memberrole_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private MemberRole memberRole;

    public MemberRoleEntity(Member member, Role role){
        this.member = member;
        this.role = role;
        this.memberRole = role.getRole();
        member.addMemberRoleEntity(this);
    }

    public Long getMemberId(){
        return this.member.getId();
    }
}
