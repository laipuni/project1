package project.project1.resource;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resource {

    @Id @GeneratedValue
    @Column(name = "resource_id")
    private Long id;

    private String path;

    @Column(name="resource_order",nullable = false)
    private int order;

    @OneToMany(mappedBy = "resource")
    private List<RoleResourceEntity> roleResourceEntities = new ArrayList<>();

    @Builder
    public Resource(String path, int order) {
        this.path = path;
        this.order = order;
    }

    public void addResourceEntity(RoleResourceEntity roleResourceEntity){
        this.roleResourceEntities.add(roleResourceEntity);
    }
}
