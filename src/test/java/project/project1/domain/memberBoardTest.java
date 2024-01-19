package project.project1.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.project1.repository.BoardRepository;
import project.project1.repository.MemberRepository;

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
