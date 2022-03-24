package jungjin.board.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import jungjin.M2Application;
import jungjin.board.domain.Board;
import jungjin.board.domain.BoardFile;
import jungjin.board.domain.BoardMaster;
import jungjin.board.domain.BoardReply;
import jungjin.board.service.BoardMasterService;
import jungjin.board.service.BoardReplyService;
import jungjin.board.service.BoardService;
import jungjin.user.service.UserCustom;
import jungjin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/admin/board"})
public class BoardAdminController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardReplyService boardReplyService;

    @Autowired
    private BoardMasterService boardMasterService;

    @Autowired
    private UserService userService;

    @GetMapping({"/list/{board_master_id}"})
    public String boardList(@RequestParam(value = "page", defaultValue = "1") int page, Model model, @PathVariable int board_master_id) {
        BoardMaster boardMaster = this.boardMasterService.showBoardMaster(board_master_id);
        Page<Board> boardList = this.boardService.listBoard(page, 10, board_master_id);
        model.addAttribute("boardList", boardList);
        model.addAttribute("boardMaster", boardMaster);
        model.addAttribute("page", Integer.valueOf(page));
        return "/admin/board/list";
    }

    @RequestMapping(value = {"/register/{board_master_id}"}, method = {RequestMethod.GET})
    public String boardRegister(Model model, @PathVariable int board_master_id) {
        BoardMaster boardMaster = this.boardMasterService.showBoardMaster(board_master_id);
        model.addAttribute("boardForm", new Board());
        model.addAttribute("boardMaster", boardMaster);
        return "/admin/board/register";
    }

    @RequestMapping(value = {"/register/{board_master_id}"}, method = {RequestMethod.POST})
    public String boardInsert(@ModelAttribute("boardForm") Board board, @PathVariable int board_master_id, BindingResult bindingResult, @RequestParam("file") MultipartFile[] files, Model model) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        board.setUser(principal.getUser());
        this.boardService.saveBoard(board);
        if (files != null && files.length > 0)
            for (int i = 0; i < files.length; i++) {
                try {
                    if (true != files[i].isEmpty()) {
                        byte[] bytes = files[i].getBytes();
                        BoardFile boardFile = new BoardFile();
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                        String fileName = String.valueOf(sdf.format(date)) + "_" + String.valueOf(board.getId()) + "_" + String.valueOf(i + 1);
                        String ext = files[i].getOriginalFilename();
                        ext = ext.substring(ext.indexOf("."));
                        BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(M2Application.UPLOAD_DIR + "/board/" + fileName + ext)));
                        boardFile.setName(fileName + ext);
                        boardFile.setExt(ext);
                        boardFile.setOriName(files[i].getOriginalFilename());
                        boardFile.setBoard(board);
                        boardFile.setCreateDate(LocalDateTime.now());
                        this.boardService.saveBoardFile(boardFile);
                        buffStream.write(bytes);
                        buffStream.close();
                    }
                } catch (Exception exception) {}
            }
        return "redirect:/admin/board/list/{board_master_id}";
    }

    @RequestMapping(value = {"/modify/{board_master_id}/{id}"}, method = {RequestMethod.GET})
    public String boardModify(Model model, @PathVariable int board_master_id, @PathVariable Long id) {
        BoardMaster boardMaster = this.boardMasterService.showBoardMaster(board_master_id);
        Board board = this.boardService.showBoard(id);
        model.addAttribute("board", board);
        model.addAttribute("boardMaster", boardMaster);
        return "/admin/board/modify";
    }

    @RequestMapping(value = {"/modify/{board_master_id}/{id}"}, method = {RequestMethod.POST})
    public String boardupdate(@PathVariable Long id, @PathVariable int board_master_id, Board board, @RequestParam(value = "fileDeleteArray", required = false) Long[] fileDeleteArray, @RequestParam("file") MultipartFile[] files) {
        this.boardService.updateBoard(id, board);
        if (files != null && files.length > 0)
            for (int i = 0; i < files.length; i++) {
                try {
                    if (true != files[i].isEmpty()) {
                        byte[] bytes = files[i].getBytes();
                        BoardFile boardFile = new BoardFile();
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                        String fileName = String.valueOf(sdf.format(date)) + "_" + String.valueOf(board.getId()) + "_" + String.valueOf(i + 1);
                        String ext = files[i].getOriginalFilename();
                        ext = ext.substring(ext.indexOf("."));
                        BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(M2Application.UPLOAD_DIR + "/board/" + fileName + ext)));
                        boardFile.setName(fileName + ext);
                        boardFile.setExt(ext);
                        boardFile.setOriName(files[i].getOriginalFilename());
                        boardFile.setBoard(board);
                        boardFile.setCreateDate(LocalDateTime.now());
                        this.boardService.saveBoardFile(boardFile);
                        buffStream.write(bytes);
                        buffStream.close();
                    }
                } catch (Exception exception) {}
            }
        if (fileDeleteArray != null)
            this.boardService.deleteBoardFile(fileDeleteArray);
        return "redirect:/admin/board/list/" + board_master_id;
    }

    @GetMapping({"/remove/{id}"})
    @ResponseBody
    public String boardRemove(@PathVariable Long id) {
        Board board = this.boardService.deleteBoard(id);
        String success = "success";
        if (board == null)
            success = "fail";
        return success;
    }

    @RequestMapping(value = {"/reply/{board_id}"}, method = {RequestMethod.GET})
    public String boardReplyRegister(Model model, @PathVariable Long board_id) {
        Board board = this.boardService.showBoard(board_id);
        BoardMaster boardMaster = this.boardMasterService.showBoardMaster(board.getBoardMaster().getId());
        List<BoardReply> boardReplyList = this.boardReplyService.listBoardReply(board_id);
        model.addAttribute("boardMaster", boardMaster);
        model.addAttribute("question", board);
        model.addAttribute("replyList", boardReplyList);
        model.addAttribute("boardForm", new BoardReply());
        return "/admin/board/reply";
    }

    @RequestMapping(value = {"/reply/{board_id}"}, method = {RequestMethod.POST})
    public String boardReplyInsert(@ModelAttribute("boardForm") BoardReply boardReply, @PathVariable Long board_id, BindingResult bindingResult, Model model) {
        this.boardReplyService.saveBoardReply(boardReply);
        return "redirect:/admin/board/list/2";
    }

    @GetMapping({"/reply/remove/{id}"})
    @ResponseBody
    public String boardReplyRemove(@PathVariable Long id) {
        BoardReply boardReply = this.boardReplyService.deleteBoardReply(id);
        String success = "success";
        if (boardReply == null)
            success = "fail";
        return success;
    }
}
