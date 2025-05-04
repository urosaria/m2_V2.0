package jungjin.board.controller;

import java.beans.ConstructorProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jungjin.M2Application;
import jungjin.board.domain.Board;
import jungjin.board.domain.BoardFile;
import jungjin.board.domain.BoardMaster;
import jungjin.board.service.BoardMasterService;
import jungjin.board.service.BoardReplyService;
import jungjin.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@Controller
@RequestMapping({"/board"})
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardReplyService boardReplyService;

    @Autowired
    private BoardMasterService boardMasterService;

    @ConstructorProperties({"boardService", "boardReplyService", "boardMasterService"})
    public BoardController(BoardService boardService, BoardReplyService boardReplyService, BoardMasterService boardMasterService) {
        this.boardService = boardService;
        this.boardReplyService = boardReplyService;
        this.boardMasterService = boardMasterService;
    }

    @GetMapping({"/list"})
    public String boardList(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Page<Board> boardList = this.boardService.listBoard(page, 10, 1);
        Page<Board> communityList = this.boardService.listBoard(page, 10, 2);
        model.addAttribute("noticeList", boardList);
        model.addAttribute("communityList", communityList);
        model.addAttribute("page", Integer.valueOf(page));
        return "/board/list";
    }

    @GetMapping({"/listContents"})
    public String boardListContents(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Page<Board> boardList = this.boardService.listBoard(page, 10, 1);
        model.addAttribute("noticeList", boardList);
        model.addAttribute("page", Integer.valueOf(page));
        return "/board/listContents";
    }

    @RequestMapping(value = {"/show/{board_master_id}/{id}"}, method = {RequestMethod.GET})
    public String boardShow(Model model, @PathVariable int board_master_id, @PathVariable Long id) {
        Board board = this.boardService.showBoard(id);
        model.addAttribute("board", board);
        return "/board/show";
    }

    @RequestMapping(value = {"/register/{board_master_id}"}, method = {RequestMethod.GET})
    public String boardRegister(Model model, @PathVariable int board_master_id) {
        BoardMaster boardMaster = this.boardMasterService.showBoardMaster(board_master_id);
        model.addAttribute("boardForm", new Board());
        model.addAttribute("boardMaster", boardMaster);
        return "/board/register";
    }

    @RequestMapping(value = {"/register/{board_master_id}"}, method = {RequestMethod.POST})
    public String boardInsert(@ModelAttribute("boardForm") Board board, @PathVariable int board_master_id, BindingResult bindingResult, Model model) {
        this.boardService.saveBoard(board);
        return "redirect:/board/list";
    }

    @RequestMapping(value = {"/modify/{board_master_id}/{id}"}, method = {RequestMethod.GET})
    public String boardModify(Model model, @PathVariable int board_master_id, @PathVariable Long id) {
        BoardMaster boardMaster = this.boardMasterService.showBoardMaster(board_master_id);
        Board board = this.boardService.showBoard(id);
        model.addAttribute("board", board);
        model.addAttribute("boardMaster", boardMaster);
        return "/board/modify";
    }

    @RequestMapping(value = {"/modify/{board_master_id}/{id}"}, method = {RequestMethod.POST})
    public String boardupdate(@PathVariable Long id, @PathVariable int board_master_id, Board board) {
        this.boardService.updateBoard(id, board);
        return "redirect:/board/list";
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

    @RequestMapping({"/fileDown/{bno}"})
    private void fileDown(@PathVariable Long bno, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        try {
            ServletOutputStream servletOutputStream = null;
            String fileUrl = M2Application.UPLOAD_DIR + "/board/";
            String fileName = "";
            String oriFileName = "";
            BoardFile fileVO = this.boardService.fileDetailService(bno);
            fileName = fileVO.getName();
            oriFileName = fileVO.getOriName();
            String savePath = fileUrl;
            InputStream in = null;
            OutputStream os = null;
            File file = null;
            boolean skip = false;
            String client = "";
            try {
                file = new File(savePath, fileName);
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }
            client = request.getHeader("User-Agent");
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "JSP Generated Data");
            if (!skip) {
                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else if (client.indexOf("Trident") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" +
                            URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(oriFileName
                            .getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length", "" + file.length());
                servletOutputStream = response.getOutputStream();
                byte[] b = new byte[(int)file.length()];
                int leng = 0;
                while ((leng = in.read(b)) > 0)
                    servletOutputStream.write(b, 0, leng);
            } else {
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다.');history.back();</script>");
            }
            in.close();
            servletOutputStream.close();
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }
}
