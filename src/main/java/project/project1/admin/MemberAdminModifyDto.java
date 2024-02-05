package project.project1.admin;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;
import project.project1.member.Member;
import project.project1.role.MemberRole;
import project.project1.role.MemberRoleEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberAdminModifyDto {

    private Long memberId;

    @NotBlank
    private String loginId;

    @NotBlank
    private String userName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
    private String phoneNumber;

    private List<MemberRole> roles = new ArrayList<>();

    public MemberAdminModifyDto(Member member) {
        this.memberId = member.getId();
        this.loginId = member.getLoginId();
        this.userName = member.getUserName();
        this.birth = member.getBirth();
        this.phoneNumber = member.getPhoneNumber();
        initRoles(member.getMemberRoleEntities());
    }

    public void initRoles(List<MemberRoleEntity> memberRoleEntities) {
        memberRoleEntities
                .iterator()
                .forEachRemaining(r -> roles.add(r.getMemberRole()));
    }

}
