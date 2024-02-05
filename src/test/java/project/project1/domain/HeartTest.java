package project.project1.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.project1.heart.HeartRepository;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class HeartTest {

    @Autowired private HeartRepository heartRepository;

    @Test
    void existsByBoardIdAndMemberId(){
        //given

        //when
        boolean isHearting = heartRepository.existsByBoardIdAndMemberId(2L, 3L);

        //then
        assertThat(isHearting).isEqualTo(false);

    }
}
