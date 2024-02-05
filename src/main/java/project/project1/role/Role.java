package project.project1.role;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    public Role(MemberRole role) {
        this.role = role;
    }
    public MemberRole getRole(){
        return role;
    }
}
