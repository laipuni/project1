package project.project1.admin;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberAdminPageCondition {

    private int page = 1;
    private String keyword;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private String sort = "creatTime";
    private String direction = "ACS";

}
