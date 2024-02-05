package project.project1.role.converter;

import org.springframework.core.convert.converter.Converter;
import project.project1.role.MemberRole;

public class MemberRoleToStringConverter implements Converter<MemberRole,String> {
    @Override
    public String convert(MemberRole source) {
        return source.getName();
    }
}
