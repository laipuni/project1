package project.project1.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import project.project1.member.Member;
import project.project1.role.MemberRole;
import project.project1.role.MemberRoleEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class MemberAdminPageDto {

    private Long memberId;
    private String memberName;
    private LocalDate birth;
    private String phoneNumber;
    private String loginId;
    private LocalDate createTime;
    private List<MemberRole> authorizes;

    public MemberAdminPageDto(Member member) {
        this.memberId = member.getId();
        this.memberName = member.getUserName();
        this.birth = member.getBirth();
        this.phoneNumber = member.getPhoneNumber();
        this.loginId = member.getLoginId();
        this.createTime = member.getCreateTime();
        authorizes = getAuthorizes(member.getRole());
    }

    private List<MemberRole> getAuthorizes(List<MemberRoleEntity> roleEntities) {
        List<MemberRole> authorizes = new ArrayList<>();
        roleEntities
                .iterator()
                .forEachRemaining(e -> authorizes.add(e.getRole()));
        return authorizes;
    }

    public boolean hasAuthorize(MemberRole role){
        return authorizes.contains(role);
    }
}
