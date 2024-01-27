package project.project1.reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.project1.dto.ReplyDto;

import java.util.List;

public interface ReplyRepositoryCustom {
    Page<ReplyDto> findReplyBoardId(Pageable pageable, Long boardId);
}
