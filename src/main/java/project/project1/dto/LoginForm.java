package project.project1.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

}
