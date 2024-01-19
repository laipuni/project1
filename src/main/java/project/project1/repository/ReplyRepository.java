package project.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import project.project1.domain.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long>,ReplyRepositoryCustom {

    List<Reply> findByBoardId(Long boardId);

    //답글이 아닌 댓글을 작성할 경우, 해당 게시글에 댓글 총 그룹 개수를 가져옴
    @Query("select max(r.group) from Reply r where r.board.id =:boardId")
    Long getGroup(@Param("boardId") Long boardId);

    //부모 댓글의 자식 댓글이 있다면 Order 최댓값, 없다면 null
    @Query("select max(r.order) from Reply  r where r.board.id=:boardId and r.group=:group and r.depth=:depth")
    Long getMaxOrderByGroupAndDepth(@Param("boardId")Long boardId,@Param("group") Long group,@Param("depth")Long depth);

    //답글을 작성할 경우, 사이에 끼워 넣기 위해 넣을 위치를 기준으로 밑 댓글들의 그룹내 순서를 +1함
    @Modifying
    @Query("update Reply r set r.order = r.order + 1 where r.board.id =:boardId and r.group=:group and r.order >=:order")
    void increaseOrderByDepthEq(@Param("boardId")Long boardId,@Param("group")Long group,@Param("order")Long order);

}
