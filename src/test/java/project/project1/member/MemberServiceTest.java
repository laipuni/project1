package project.project1.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.project1.admin.MemberAdminModifyDto;
import project.project1.role.MemberRole;
import project.project1.role.MemberRoleEntity;
import project.project1.role.Role;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@Rollback()
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("유저 권한만 있는 유저에게 어드민권한 추가")
    void updateAdmin(){
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

        MemberRoleEntity memberRoleEntity = new MemberRoleEntity(member,userRole);
        em.persist(memberRoleEntity);
        em.flush();
        em.clear();

        List<MemberRole> roles = new ArrayList<>();
        roles.add(MemberRole.ADMIN);
        roles.add(MemberRole.USER);

        MemberAdminModifyDto modifyDto = new MemberAdminModifyDto();

        modifyDto.setLoginId("321");
        modifyDto.setBirth(LocalDate.of(2022,1,1));
        modifyDto.setUserName("jinho");
        modifyDto.setPhoneNumber("010-5678-5678");
        modifyDto.setRoles(roles);

        //when
        memberService.updateAdmin(member.getId(), modifyDto);
        em.flush();
        em.clear();

        //then
        Member findMember = em.find(Member.class, member.getId());
        List<MemberRoleEntity> memberRoleEntities = findMember.getMemberRoleEntities();

        assertThat(memberRoleEntities.get(0).getMemberRole()).isEqualTo(MemberRole.ADMIN);
        assertThat(memberRoleEntities.get(1).getMemberRole()).isEqualTo(MemberRole.USER);
    }

    @Test
    @DisplayName("유저의 어드민 권한을 유저 권한만 가지게 수정")
    void updateUser(){
        //given
        Role userRole = new Role(MemberRole.USER);
        Role adminRole = new Role(MemberRole.ADMIN);

        em.persist(userRole);
        em.persist(adminRole);

        Member member = Member.builder()
                .loginId("admin")
                .birth(LocalDate.of(2000,11,23))
                .password("admin")
                .phoneNumber("010-1234-1234")
                .userName("laipuni_admin")
                .build();
        em.persist(member);

        MemberRoleEntity userMemberRoleEntity = new MemberRoleEntity(member,userRole);
        MemberRoleEntity adminMemberRoleEntity = new MemberRoleEntity(member,userRole);

        em.persist(userMemberRoleEntity);
        em.persist(adminMemberRoleEntity);
        em.flush();
        em.clear();

        List<MemberRole> roles = new ArrayList<>();
        roles.add(MemberRole.USER);

        MemberAdminModifyDto modifyDto = new MemberAdminModifyDto();
        modifyDto.setLoginId("321");
        modifyDto.setBirth(LocalDate.of(2022,1,1));
        modifyDto.setUserName("jinho");
        modifyDto.setPhoneNumber("010-5678-5678");
        modifyDto.setRoles(roles);

        //when
        memberService.updateAdmin(member.getId(), modifyDto);
        em.flush();
        em.clear();

        //then
        Member findMember = em.find(Member.class, member.getId());
        List<MemberRoleEntity> memberRoleEntities = findMember.getMemberRoleEntities();

        assertThat(memberRoleEntities.get(0).getMemberRole()).isEqualTo(MemberRole.USER);
    }

    @Test
    @DisplayName("유저의 권한을 전부 박탈")
    void updateEmpty(){
        //given
        Role userRole = new Role(MemberRole.USER);
        Role adminRole = new Role(MemberRole.ADMIN);

        em.persist(userRole);
        em.persist(adminRole);

        Member member = Member.builder()
                .loginId("admin")
                .birth(LocalDate.of(2000,11,23))
                .password("admin")
                .phoneNumber("010-1234-1234")
                .userName("laipuni_admin")
                .build();
        em.persist(member);

        MemberRoleEntity userMemberRoleEntity = new MemberRoleEntity(member,userRole);
        MemberRoleEntity adminMemberRoleEntity = new MemberRoleEntity(member,userRole);

        em.persist(userMemberRoleEntity);
        em.persist(adminMemberRoleEntity);
        em.flush();
        em.clear();

        MemberAdminModifyDto modifyDto = new MemberAdminModifyDto();
        modifyDto.setLoginId("321");
        modifyDto.setBirth(LocalDate.of(2022,1,1));
        modifyDto.setUserName("jinho");
        modifyDto.setPhoneNumber("010-5678-5678");

        //when
        memberService.updateAdmin(member.getId(), modifyDto);
        em.flush();
        em.clear();

        //then
        Member findMember = em.find(Member.class, member.getId());
        List<MemberRoleEntity> memberRoleEntities = findMember.getMemberRoleEntities();

        assertThat(memberRoleEntities.size()).isEqualTo(0);
    }
}