package project.project1.member;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import project.project1.admin.MemberAdminPageCondition;
import project.project1.admin.MemberAdminPageDto;
import project.project1.role.MemberRole;
import project.project1.role.MemberRoleEntity;


import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static project.project1.member.QMember.*;


@Rollback(value = false)
@Transactional
@SpringBootTest
class MemberRepositoryCustomImplTest {

    @Autowired
    MemberRepositoryCustomImpl memberRepositoryCustom;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init(){
        for (int memberIdx = 0; memberIdx < 15; memberIdx++) {
            String str = String.valueOf(memberIdx);

            Member member = new Member(str,str,str,memberIdx,str);
            member.setCreateTime(LocalDate.of(2005+memberIdx,1,1));
            member.setUpdateTime(LocalDate.of(2005+memberIdx,1,1));
            em.persist(member);

            MemberRoleEntity roleUser = new MemberRoleEntity(MemberRole.USER,member);
            em.persist(roleUser);

            if(memberIdx % 2 == 0){
                MemberRoleEntity roleAdmin = new MemberRoleEntity(MemberRole.ADMIN,member);
                em.persist(roleAdmin);
            }
        }
    }

    @Test
    void 생성날짜기준_오름차순_조회(){
        //given
        MemberAdminPageCondition condition = MemberAdminPageCondition
                .builder()
                .page(1)
                .direction("ASC")
                .sort("creatTime")
                .startDate(null)
                .endDate(null)
                .keyword(null)
                .role(null)
                .build();

        //when
        Page<MemberAdminPageDto> result = memberRepositoryCustom.findAllByAdminSearchCondition(condition);
        List<MemberAdminPageDto> content = result.getContent();

        if(content.isEmpty()){
            fail("Wrong Test");
        }
        //then
        for (MemberAdminPageDto memberAdminPageDto : result) {
            System.out.println("id = " + memberAdminPageDto.getMemberId());
            System.out.println("name = " + memberAdminPageDto.getMemberName());
            System.out.println("loginId" + memberAdminPageDto.getLoginId());
            System.out.println("authorizes = " + memberAdminPageDto.getAuthorizes());
            System.out.println("getCreateTime = " + memberAdminPageDto.getCreateTime());
        }
    }

    @Test
    void 생성날짜기준_내림차순_조회(){
        //given
        MemberAdminPageCondition condition = MemberAdminPageCondition
                .builder()
                .page(1)
                .direction("DESC")
                .sort("creatTime")
                .startDate(null)
                .endDate(null)
                .keyword(null)
                .role(null)
                .build();

        //when
        Page<MemberAdminPageDto> result = memberRepositoryCustom.findAllByAdminSearchCondition(condition);
        List<MemberAdminPageDto> content = result.getContent();

        if(content.isEmpty()){
            fail("Wrong Test");
        }
        //then
        for (MemberAdminPageDto memberAdminPageDto : result) {
            System.out.println("id = " + memberAdminPageDto.getMemberId());
            System.out.println("name = " + memberAdminPageDto.getMemberName());
            System.out.println("loginId" + memberAdminPageDto.getLoginId());
            System.out.println("authorizes = " + memberAdminPageDto.getAuthorizes());
            System.out.println("getCreateTime = " + memberAdminPageDto.getCreateTime());
        }
    }

    @Test
    void 지정날짜부터_가입한_유저들(){
        //given
        LocalDate startDate = LocalDate.of(2012,1,5);
        MemberAdminPageCondition condition = MemberAdminPageCondition
                .builder()
                .page(1)
                .direction("ASC")
                .sort("createTime")
                .startDate(startDate)
                .endDate(null)
                .keyword(null)
                .role(null)
                .build();

        //when
        Page<MemberAdminPageDto> result = memberRepositoryCustom.findAllByAdminSearchCondition(condition);
        List<MemberAdminPageDto> content = result.getContent();

        if(content.isEmpty()){
            fail("Wrong Test");
        }

        //then
        for (MemberAdminPageDto memberAdminPageDto : result) {
            if(memberAdminPageDto.getCreateTime().isBefore(startDate)){
                fail("Wrong Test");
            }

            System.out.println("id = " + memberAdminPageDto.getMemberId());
            System.out.println("name = " + memberAdminPageDto.getMemberName());
            System.out.println("loginId" + memberAdminPageDto.getLoginId());
            System.out.println("authorizes = " + memberAdminPageDto.getAuthorizes());
            System.out.println("getCreateTime = " + memberAdminPageDto.getCreateTime());
        }
    }

    @Test
    void 지정날짜전에_가입한_유저들(){
        //given
        LocalDate endDate = LocalDate.of(2020,5,5);
        MemberAdminPageCondition condition = MemberAdminPageCondition
                .builder()
                .page(1)
                .direction("ASC")
                .sort("createTime")
                .startDate(null)
                .endDate(endDate)
                .keyword(null)
                .role(null)
                .build();

        //when
        Page<MemberAdminPageDto> result = memberRepositoryCustom.findAllByAdminSearchCondition(condition);
        List<MemberAdminPageDto> content = result.getContent();

        if(content.isEmpty()){
            fail("Wrong Test");
        }

        //then
        for (MemberAdminPageDto memberAdminPageDto : content) {
            if(memberAdminPageDto.getCreateTime().isAfter(endDate)){
                fail("Wrong Test");
            }

            System.out.println("id = " + memberAdminPageDto.getMemberId());
            System.out.println("name = " + memberAdminPageDto.getMemberName());
            System.out.println("loginId" + memberAdminPageDto.getLoginId());
            System.out.println("authorizes = " + memberAdminPageDto.getAuthorizes());
            System.out.println("getCreateTime = " + memberAdminPageDto.getCreateTime());

        }
    }

    @Test
    void 일정기간범위에_가입한_유저들(){
        //given
        LocalDate endDate = LocalDate.of(2000,5,5);
        LocalDate startDate = LocalDate.of(2015,1,1);

        MemberAdminPageCondition condition = MemberAdminPageCondition
                .builder()
                .page(1)
                .direction("ASC")
                .sort("createTime")
                .startDate(startDate)
                .endDate(endDate)
                .keyword(null)
                .role(null)
                .build();

        //when
        Page<MemberAdminPageDto> result = memberRepositoryCustom.findAllByAdminSearchCondition(condition);
        List<MemberAdminPageDto> content = result.getContent();

        //then
        for (MemberAdminPageDto memberAdminPageDto : content) {
            System.out.println("id = " + memberAdminPageDto.getMemberId());
            System.out.println("name = " + memberAdminPageDto.getMemberName());
            System.out.println("loginId" + memberAdminPageDto.getLoginId());
            System.out.println("authorizes = " + memberAdminPageDto.getAuthorizes());
            System.out.println("getCreateTime = " + memberAdminPageDto.getCreateTime());

            if(memberAdminPageDto.getCreateTime().isBefore(startDate) || memberAdminPageDto.getCreateTime().isBefore(endDate)){
                fail("Wrong Test");
            }
        }
    }


    @Test
    void 비슷한_닉네임을_가진_유저들_찾기(){
        //given
        MemberAdminPageCondition condition = MemberAdminPageCondition
                .builder()
                .page(1)
                .direction("ASC")
                .sort("createTime")
                .startDate(null)
                .endDate(null)
                .keyword("1")
                .role(null)
                .build();

        //when
        Page<MemberAdminPageDto> pageResult = memberRepositoryCustom.findAllByAdminSearchCondition(condition);
        List<MemberAdminPageDto> content = pageResult.getContent();

        if(content.isEmpty()){
            fail("Wrong Test");
        }
        //then
        for (MemberAdminPageDto memberAdminPageDto : content) {
            System.out.println("id = " + memberAdminPageDto.getMemberId());
            System.out.println("name = " + memberAdminPageDto.getMemberName());
            System.out.println("loginId" + memberAdminPageDto.getLoginId());
            System.out.println("authorizes = " + memberAdminPageDto.getAuthorizes());
            System.out.println("getCreateTime = " + memberAdminPageDto.getCreateTime());

            if(!memberAdminPageDto.getMemberName().contains("1")){
                fail("Wrong Test");
            }
        }
    }


    @Test
    void User권한을_가진_유저들_찾기(){
        //given
        MemberAdminPageCondition condition = MemberAdminPageCondition
                .builder()
                .page(1)
                .direction("ASC")
                .sort("createTime")
                .startDate(null)
                .endDate(null)
                .keyword(null)
                .role(MemberRole.USER)
                .build();

        //when
        Page<MemberAdminPageDto> pageResult = memberRepositoryCustom.findAllByAdminSearchCondition(condition);
        List<MemberAdminPageDto> content = pageResult.getContent();

        if(content.isEmpty()){
            fail("Wrong Test");
        }

        //then
        for (MemberAdminPageDto memberAdminPageDto : content) {
            System.out.println("id = " + memberAdminPageDto.getMemberId());
            System.out.println("name = " + memberAdminPageDto.getMemberName());
            System.out.println("loginId" + memberAdminPageDto.getLoginId());
            System.out.println("authorizes = " + memberAdminPageDto.getAuthorizes());
            System.out.println("getCreateTime = " + memberAdminPageDto.getCreateTime());

            if(!memberAdminPageDto.hasAuthorize(MemberRole.USER)){
                fail("Wrong Test");
            }
        }
    }

    @Test
    void Admin_User_권한을_가진_유저들_찾기(){
        //given
        MemberAdminPageCondition condition = MemberAdminPageCondition
                .builder()
                .page(1)
                .direction("ASC")
                .sort("createTime")
                .startDate(null)
                .endDate(null)
                .keyword(null)
                .role(MemberRole.ADMIN)
                .build();

        //when
        Page<MemberAdminPageDto> pageResult = memberRepositoryCustom.findAllByAdminSearchCondition(condition);
        List<MemberAdminPageDto> content = pageResult.getContent();

        if(content.isEmpty()){
            fail("Wrong Test");
        }
        //then
        for (MemberAdminPageDto memberAdminPageDto : content) {
            System.out.println("id = " + memberAdminPageDto.getMemberId());
            System.out.println("name = " + memberAdminPageDto.getMemberName());
            System.out.println("loginId" + memberAdminPageDto.getLoginId());
            System.out.println("authorizes = " + memberAdminPageDto.getAuthorizes());
            System.out.println("getCreateTime = " + memberAdminPageDto.getCreateTime());

            if(!memberAdminPageDto.hasAuthorize(MemberRole.USER) || !memberAdminPageDto.hasAuthorize(MemberRole.ADMIN)){
                fail("Wrong Test");
            }
        }
    }

}