package project.project1.member;

import org.springframework.data.domain.Page;
import project.project1.admin.MemberAdminPageCondition;
import project.project1.admin.MemberAdminPageDto;

public interface MemberRepositoryCustom {

    Page<MemberAdminPageDto> findAllByAdminSearchCondition(MemberAdminPageCondition condition);

}
