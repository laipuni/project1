package project.project1.admin;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import project.project1.role.MemberRole;

import java.time.LocalDate;

@Data
public class MemberAdminPageCondition {

    private Integer page = 1;
    private String keywordType;
    private String keyword;

    private MemberRole role;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthEndDate;

    private String sort = "creatTime";
    private String direction = "ACS";
}
