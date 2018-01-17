package jungjin.board.controller;

import jungjin.board.domain.Board;
import jungjin.board.domain.BoardMaster;
import jungjin.board.domain.BoardReply;
import jungjin.board.service.BoardMasterService;
import jungjin.board.service.BoardReplyService;
import jungjin.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/board")
public class BoardAdminController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardReplyService boardReplyService;

    @Autowired
    private BoardMasterService boardMasterService;

    @GetMapping(value="/list/{board_master_id}")
    public String boardList(Model model, @PathVariable int board_master_id){

        BoardMaster boardMaster = boardMasterService.showBoardMaster(board_master_id);
        List<Board> boardList = boardService.listBoard(board_master_id);

        model.addAttribute("boardList", boardList);
        model.addAttribute("boardMaster", boardMaster);

        return "/admin/board/list";
    }

    @RequestMapping(value="/register/{board_master_id}", method= RequestMethod.GET)
    public String boardRegister(Model model, @PathVariable int board_master_id){

        BoardMaster boardMaster = boardMasterService.showBoardMaster(board_master_id);

        model.addAttribute("boardForm", new Board());
        model.addAttribute("boardMaster", boardMaster);

        return "/admin/board/register";
    }

    @RequestMapping(value = "/register/{board_master_id}", method = RequestMethod.POST)
    public String boardInsert(@ModelAttribute("boardForm") Board board,
                              @PathVariable int board_master_id,
                              BindingResult bindingResult,
                              Model model) {

        boardService.saveBoard(board);

        return "redirect:/admin/board/list/{board_master_id}";
    }

    @RequestMapping(value="/modify/{board_master_id}/{id}", method=RequestMethod.GET)
    public String boardModify(Model model, @PathVariable int board_master_id, @PathVariable Long id){

        BoardMaster boardMaster = boardMasterService.showBoardMaster(board_master_id);
        Board board=boardService.showBoard(id);

        model.addAttribute("board", board);
        model.addAttribute("boardMaster", boardMaster);

        return "/admin/board/modify";
    }

    @RequestMapping(value = "/modify/{board_master_id}/{id}", method = RequestMethod.POST)
    public String boardupdate(@PathVariable Long id, @PathVariable int board_master_id, Board board) {

        boardService.updateBoard(id, board);

        return "redirect:/admin/board/list/"+board_master_id;
    }

    @GetMapping(value="/remove/{id}")
    @ResponseBody
    public String boardRemove(@PathVariable Long id){

        Board board= boardService.deleteBoard(id);
        String success="success";

        if(board==null){
            success="fail";
        }

        return success;
    }

    @RequestMapping(value="/reply/{board_id}", method= RequestMethod.GET)
    public String boardReplyRegister(Model model, @PathVariable Long board_id){

        Board board = boardService.showBoard(board_id);
        BoardMaster boardMaster = boardMasterService.showBoardMaster(board.getBoardMaster().getId());
        List<BoardReply> boardReplyList = boardReplyService.listBoardReply(board_id);

        model.addAttribute("boardMaster", boardMaster);
        model.addAttribute("question", board);
        model.addAttribute("replyList", boardReplyList);
        model.addAttribute("boardForm", new BoardReply());

        return "/admin/board/reply";
    }

    @RequestMapping(value = "/reply/{board_id}", method = RequestMethod.POST)
    public String boardReplyInsert(@ModelAttribute("boardForm") BoardReply boardReply,
                                   @PathVariable Long board_id,
                                   BindingResult bindingResult,
                                   Model model) {

        boardReplyService.saveBoardReply(boardReply);

        return "redirect:/admin/board/list/2";
    }

    @GetMapping(value="/reply/remove/{id}")
    @ResponseBody
    public String boardReplyRemove(@PathVariable Long id){

        BoardReply boardReply= boardReplyService.deleteBoardReply(id);
        String success="success";

        if(boardReply==null){
            success="fail";
        }

        return success;
    }

}
