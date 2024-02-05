package project.project1.role;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Rollback
@Transactional
@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    @DisplayName("MemberRole로 조회하기")
    void findByMemberRole(){
        //given
        Role role = new Role(MemberRole.USER);
        roleRepository.save(role);

        //when
        Role findRole = roleRepository.findByMemberRole(MemberRole.USER).get();

        //then
        assertThat(findRole.getRole()).isEqualTo(MemberRole.USER);
    }

}