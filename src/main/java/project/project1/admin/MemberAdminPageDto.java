package project.project1.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
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

    @QueryProjection
    public MemberAdminPageDto(Long memberId, String memberName, LocalDate birth, String phoneNumber, String loginId, LocalDate createTime) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.birth = birth;
        this.phoneNumber = phoneNumber;
        this.loginId = loginId;
        this.createTime = createTime;
    }
    public MemberAdminPageDto(Member member) {
        this.memberId = member.getId();
        this.memberName = member.getUserName();
        this.birth = member.getBirth();
        this.phoneNumber = member.getPhoneNumber();
        this.loginId = member.getLoginId();
        this.createTime = member.getCreateTime();
        authorizes = getAuthorizes(member.getMemberRoleEntities());
    }

    private List<MemberRole> getAuthorizes(List<MemberRoleEntity> roleEntities) {
        List<MemberRole> authorizes = new ArrayList<>();
        roleEntities
                .iterator()
                .forEachRemaining(e -> authorizes.add(e.getMemberRole()));
        return authorizes;
    }

    public boolean hasAuthorize(MemberRole role){
        return authorizes.contains(role);
    }
}
