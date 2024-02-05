package project.project1.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.project1.admin.MemberAdminModifyDto;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Rollback
@Transactional
@SpringBootTest
class MemberTest {

    @Autowired
    EntityManager em;

    @Test
    void updateAdmin() {
        //given
        Member member = new Member(
                "123",
                "123",
                "123",
                LocalDate.of(2022,1,1),
                "010-1234-1234"
                );

        em.persist(member);
        em.flush();
        em.clear();

        String changeLoginId = "321";
        LocalDate changeBirth = LocalDate.of(2000,10,10);
        String changeUserName = "laipuni";
        String changePhoneNumber = "010-4321-4321";

        //when
        Member findMember = em.find(Member.class, member.getId());
        findMember.update(changeLoginId,changeBirth,changeUserName,changePhoneNumber);

        em.flush();
        em.clear();

        Member validMember = em.find(Member.class, member.getId());
        //then
        assertThat(validMember.getLoginId()).isEqualTo(changeLoginId);
        assertThat(validMember.getBirth()).isEqualTo(changeBirth);
        assertThat(validMember.getUserName()).isEqualTo(changeUserName);
        assertThat(validMember.getPhoneNumber()).isEqualTo(changePhoneNumber);
    }
}