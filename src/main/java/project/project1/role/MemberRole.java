package project.project1.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
    USER("User"),ADMIN("Admin");

    private String name;
}
