package project.project1.role.converter;

import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.core.convert.converter.Converter;
import project.project1.role.MemberRole;

public class StringToMemberRoleConverter implements Converter<String, MemberRole> {
    @Override
    public MemberRole convert(String source) throws BindException {
        switch (source){
            case "User": return MemberRole.USER;
            case "Admin": return MemberRole.ADMIN;
        }


        return null;
    }
}
