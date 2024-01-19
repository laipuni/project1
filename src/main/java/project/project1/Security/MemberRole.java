package project.project1.Security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
    USER("User"),ADMIN("Admin");

    private String name;
}
