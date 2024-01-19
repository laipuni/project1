package project.project1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.project1.domain.Heart;
import project.project1.repository.HeartRepository;

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
        return heartRepository.findById(heartId)
                .orElse(null);
    }

    @Transactional
    public void delete(Long boardId,Long memberId){
        heartRepository.findByBoardIdAndMemberId(boardId, memberId)
                .ifPresent(heartRepository::delete);
    }

    public boolean isExist(Long boardId,Long memberId){
        return heartRepository.existsByBoardIdAndMemberId(boardId,memberId);
    }

    public Long getCountHeart(Long boarId){
        return heartRepository.countByBoardId(boarId);
    }
}

