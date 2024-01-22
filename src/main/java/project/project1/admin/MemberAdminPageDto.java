package project.project1.admin;

import com.querydsl.core.annotations.QueryProjection;
import project.project1.Security.MemberRoleEntity;

import java.util.ArrayList;
import java.util.List;

public class MemberAdminPageDto {

    private Long memberId;
    private String memberName;
    private String loginId;
    private List<String> authorizes;

    @QueryProjection
    public MemberAdminPageDto(Long memberId, String memberName, String loginId, List<MemberRoleEntity> roleEntities) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.loginId = loginId;
        this.authorizes = getAuthorizes(roleEntities);
    }

    private List<String> getAuthorizes(List<MemberRoleEntity> roleEntities) {
        List<String> authorizes = new ArrayList<>();

        roleEntities
                .iterator()
                .forEachRemaining(e -> authorizes.add(e.getRole().getName()));

        return authorizes;
    }


}
