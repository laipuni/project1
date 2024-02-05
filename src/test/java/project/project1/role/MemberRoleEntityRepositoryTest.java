package project.project1.role;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.project1.member.Member;
import project.project1.member.MemberRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Rollback(value = false)
@Transactional
@SpringBootTest
class MemberRoleEntityRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRoleEntityRepository memberRoleEntityRepository;
    
    @Autowired
    MemberRepository memberRepository;
    
    @Test
    void deleteAllByMemberId(){
        //given
        Role userRole = new Role(MemberRole.USER);
        Role adminRole = new Role(MemberRole.ADMIN);

        em.persist(userRole);
        em.persist(adminRole);

        Member member = Member.builder()
                .loginId("123")
                .birth(LocalDate.of(2000,11,23))
                .password("123")
                .phoneNumber("010-1234-1234")
                .userName("laipuni")
                .build();
        em.persist(member);

        MemberRoleEntity userMemberRoleEntity = new MemberRoleEntity(member,userRole);
        MemberRoleEntity adminMemberRoleEntity = new MemberRoleEntity(member,adminRole);
        em.persist(userMemberRoleEntity);
        em.persist(adminMemberRoleEntity);

        em.flush();
        em.clear();

        //when

        List<MemberRoleEntity> memberRoleEntitiesBeforeDelete = memberRoleEntityRepository.findAll();

        System.out.println("memberRoleEntitiesBeforeDelete = " + memberRoleEntitiesBeforeDelete);
        if(memberRoleEntitiesBeforeDelete.isEmpty()){
            fail("WrongTest");
        }

        memberRoleEntityRepository.deleteAllMemberId(member.getId());
        List<MemberRoleEntity> memberRoleEntitiesAfterDelete = memberRoleEntityRepository.findAll();
        Member member1 = em.find(Member.class, member.getId());

        //then
        System.out.println("memberRoleEntitiesAfterDelete = " + memberRoleEntitiesAfterDelete);
        if(!memberRoleEntitiesAfterDelete.isEmpty()){
            fail("WrongTest");
        }
    }

}