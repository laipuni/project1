package project.project1.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.project1.Security.SecurityMember;
import project.project1.file.UploadFileEntityService;
import project.project1.heart.HeartService;
import project.project1.member.Member;
import project.project1.dto.*;
import project.project1.file.FileStore;
import project.project1.file.UploadFile;
import project.project1.file.UploadFileEntity;
import project.project1.member.MemberService;
import project.project1.reply.ReplyService;
import project.project1.utils.PagingUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final ReplyService replyService;
    private final FileStore fileStore;
    private final UploadFileEntityService uploadFileEntityService;
    private final HeartService heartService;

    /**
     * 게시글 목록 조회
     * @param model
     * @param condition
     * @return
     */
    @GetMapping
    public String boardsList(Model model,
                             @ModelAttribute(value = "condition") BoardSearchCondition condition
    ){
        Page<BoardDto> boardDto = boardService.findAllSearchCondition(condition);

        int startPage = PagingUtils.getStartPage(condition.getPage(),10);
        int endPage = PagingUtils.getEndPage(startPage,boardDto.getTotalPages());


        model.addAttribute("boards",boardDto);
        model.addAttribute("totalPages",boardDto.getTotalPages());
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "/board/boardList";
    }

    /**
     * @param form 등록할 게시글의 내용을 담는 form
     * @return 게시글 등록 뷰로 감
     */
    @GetMapping("/add")
    public String boardAddForm(@ModelAttribute("board") BoardAddForm form){
        return "/board/boardAddForm";
    }

    @PostMapping("/add")
    public String boardAdd(@Valid @ModelAttribute("board") BoardAddForm form,
                           BindingResult bindingResult,
                           @AuthenticationPrincipal SecurityMember securityMember,
                           RedirectAttributes attributes) throws IOException {

        log.info("게시글 생성 = {}",securityMember.getName());

        if(bindingResult.hasErrors()){
            return "/board/boardAddForm";
        }

        Member findMember = memberService.findByLoginId(securityMember.getUsername());
        Board board = Board.createBoard(findMember,form.getTitle(),form.getComments());

        //board Entity에 파일 저장
        List<UploadFile> uploadFiles = fileStore.storeFiles(form.getImageFiles());
        List<UploadFileEntity> files = new ArrayList<>();

        uploadFiles.iterator()
                .forEachRemaining(u -> files.add(new UploadFileEntity(u, board)));

        boardService.save(board);
        //h2 DB는 Id생성 전략을 identity을 기본값으로 한다.
        //영속성 컨텍스트에 들어갈 떄 식별키인 ID가 필요하다.
        //identity 전략 특성상 DB에 ID 생성권한을 넘겼기 때문에 board를 먼저 저장하고,
        // 연관관계주인인 UploadFileEntity를 저장해야 한다.
        files.iterator()
                .forEachRemaining(uploadFileEntityService::save);

        attributes.addAttribute("boardId",board.getId());

        return "redirect:/boards/{boardId}";
    }

    /**
     * 게시판 상세 조회 Form
     * @param boardId 조회할 게시판 Id
     * @param model DB에서 가져온 board값을 DTO로 변환 후, 뷰에 넘긴다
     * @param pageable 조회한 게시판의 댓글들의 정렬과 페이징
     * @return 게시판 상세 조회 viewPath를 반환 할 경우 해당 경로의 html 렌더링
     */
    @GetMapping("/{boardId}")
    public String boardDetail(Model model,
                              @PathVariable("boardId") Long boardId,
                              @AuthenticationPrincipal SecurityMember securityMember,
                              @PageableDefault(sort = "group",direction = Direction.DESC) Pageable pageable){

        //조회수 증가
        boardService.increaseBoardView(boardId);

        String loginId = securityMember.getUsername();
        Member findMember = memberService.findByLoginId(loginId);

        Board findBoard = boardService.findById(boardId);
        BoardDetailDto boardDetailDto = new BoardDetailDto(findBoard);
        Page<ReplyDto> findReplies = replyService.findByBoardId(pageable, findBoard.getId());

        //게시판 좋아요 조회
        boolean isHearting = heartService.isExist(boardId, findMember.getId());
        Long heartCount = heartService.getCountHeart(boardId);

        int startPage = PagingUtils.getStartPage(pageable.getPageNumber() + 1,10);
        int endPage = PagingUtils.getEndPage(startPage,findReplies.getTotalPages());

        model.addAttribute("board",boardDetailDto);
        model.addAttribute("replies",findReplies);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("isHearting",isHearting);
        model.addAttribute("heartCount",heartCount);

        return "/board/boardDetail";
    }

    /**
     * 게시글 삭제
     * @param boardId 삭제할 게시글 Id
     * @return 전체 게시글 조회
     */
    @PreAuthorize("hasRole('ROLE_Admin') or principal.username == #userId")
    @PostMapping("/delete")
    public String delete(
            @RequestParam("boardId")Long boardId,
            @RequestParam("loginId")String userId
    ){
        boardService.delete(boardId);

        return "redirect:/boards";
    }

    /**
     * 게시글 수정 폼
     * @param boardId
     * @param model
     * @return
     */
    @PreAuthorize(value = "principal.username == #userId or hasRole('ROLE_Admin')")
    @GetMapping("/modify")
    public String boardModifyForm(
            @RequestParam("boardId") Long boardId,
            @RequestParam("loginId") String userId,
            Model model
    ){
        Board findBoard = boardService.findById(boardId);
        BoardModifyForm form = new BoardModifyForm(boardId, findBoard.getTitle(), findBoard.getComments());
        model.addAttribute("board",form);

        return "board/boardModifyForm";
    }

    @PostMapping("/modify")
    public String boardModify(
            @Valid @ModelAttribute("board") BoardModifyForm form,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "board/boardModifyForm";
        }

        boardService.update(form);

        return "redirect:/boards";
    }
}
