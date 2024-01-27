package project.project1.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.project1.role.MemberRole;
import project.project1.role.MemberRoleEntity;
import project.project1.board.Board;
import project.project1.member.Member;
import project.project1.reply.Reply;
import project.project1.board.BoardRepository;
import project.project1.member.MemberRepository;
import project.project1.role.MemberRoleEntityRepository;
import project.project1.reply.ReplyRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Profile("local")
@Slf4j
@Component
@RequiredArgsConstructor
public class InitComponent {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final MemberRoleEntityRepository memberRoleEntityRepository;
    private final ReplyRepository replyRepository;
    private final PasswordEncoder encoder;

    @PostConstruct
    void init(){
        Member tester = new Member(
                "test",
                encoder.encode("test"),
                "test", LocalDate.of(2020,1,1),
                "010-1234-1234"
        );
        MemberRoleEntity testerAdminRoleEntity = new MemberRoleEntity(MemberRole.ADMIN,tester);
        MemberRoleEntity testerUserRoleEntity = new MemberRoleEntity(MemberRole.USER,tester);

        memberRepository.save(tester);
        memberRoleEntityRepository.save(testerAdminRoleEntity);
        memberRoleEntityRepository.save(testerUserRoleEntity);

        for(int i=0;i<100;i++){
            String name = "회원";
            String str = String.valueOf(i);
            String encodedPassword = encoder.encode(String.valueOf(i));
            Member member = new Member(
                    str,
                    encodedPassword,
                    name + str,
                    LocalDate.of(2020,i/10 + 1,i/4 + 1),
                    "010-1234-1234"
            );
            MemberRoleEntity roleEntity = new MemberRoleEntity(MemberRole.USER,member);
            Board board = Board.createBoard(member,str,str);
            memberRepository.save(member);
            memberRoleEntityRepository.save(roleEntity);
            boardRepository.save(board);
        }

        Board board1 = Board.createBoard(tester,"안녕하세요","반갑습니다");

        Reply reply3 = new Reply(tester,board1,"댓글1의 댓글3",0L,1L,3L);
        Reply reply2 = new Reply(tester,board1,"댓글1의 댓글2",0L,1L,1L);
        Reply reply4 = new Reply(tester,board1,"댓글2의 댓글4",0L,2L,2L);
        Reply reply1 = new Reply(tester,board1,"댓글1",0L,0L,0L);

        reply1.addChild(reply2);
        reply1.addChild(reply3);
        reply2.addChild(reply4);

        boardRepository.save(board1);

        replyRepository.save(reply1);
        replyRepository.save(reply2);
        replyRepository.save(reply3);
        replyRepository.save(reply4);

    }
}
