package project.project1.role;

import java.util.List;
import java.util.Map;

public interface MemberRoleEntityRepositoryCustom {

    Map<Long, List<MemberRole>> findAllInMemberIds(List<Long> memberIds);
}
