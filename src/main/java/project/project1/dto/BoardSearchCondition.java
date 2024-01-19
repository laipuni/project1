package project.project1.dto;

import lombok.Data;

@Data
public class BoardSearchCondition {
    private int page = 1;
    private String field = "title";
    private String keyword;
    private String sort = "creatTime";
    private String direction = "ACS";
}
