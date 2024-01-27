package project.project1.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.project1.board.BoardRepository;
import project.project1.member.MemberRepository;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback()
public class memberBoardTest {

    @Autowired
    EntityManager em;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    MemberRepository memberRepository;

}
