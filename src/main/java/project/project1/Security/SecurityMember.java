package project.project1.Security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import project.project1.member.Member;
import project.project1.role.MemberRoleEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SecurityMember extends User {

    private final String name;
    private final Long memberId;
    private static final String ROLE = "ROLE_";

    public SecurityMember(Member member) {
        super(member.getLoginId(), member.getPassword(),makeGrantedAuthority(member.getRole()));
        this.name = member.getUserName();
        this.memberId = member.getId();
    }

    public static List<GrantedAuthority> makeGrantedAuthority(List<MemberRoleEntity> role){
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (MemberRoleEntity memberRoleEntity : role) {
            authorities.add(new SimpleGrantedAuthority(ROLE + memberRoleEntity.getRole().getName()));
        }

        return authorities;
    }
}
