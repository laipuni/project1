package project.project1.heart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project1.heart.Heart;
import project.project1.heart.HeartRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HeartService {

    private final HeartRepository heartRepository;

    @Transactional
    public Heart save(Heart heart){
        heartRepository.save(heart);
        return heart;
    }

    public Heart findById(Long heartId){
        return heartRepository
                .findById(heartId)
                .orElseThrow(() -> new IllegalArgumentException("해당 좋아요는 없습니다. id =" + heartId));
    }

    @Transactional
    public void delete(Long boardId,Long memberId){
        heartRepository.deleteByBoardIdAndMemberId(boardId, memberId);
    }

    public boolean isExist(Long boardId,Long memberId){
        return heartRepository.existsByBoardIdAndMemberId(boardId,memberId);
    }

    public Long getCountHeart(Long boarId){
        return heartRepository.countByBoardId(boarId);
    }
}

