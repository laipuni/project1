package project.project1.resource;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.project1.role.Role;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleResourceEntity {

    @Id @GeneratedValue
    @Column(name = "roleresource_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resource resource;

    public RoleResourceEntity(Role role, Resource resource){
        this.role = role;
        this.resource = resource;

        resource.addResourceEntity(this);
    }
}
