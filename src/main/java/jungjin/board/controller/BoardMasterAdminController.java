package jungjin.board.controller;


import jungjin.board.domain.BoardMaster;
import jungjin.board.service.BoardMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/board/master")
public class BoardMasterAdminController {

    @Autowired
    private BoardMasterService boardMasterService;

    @RequestMapping(value="/list")
    public String boardMasterList(Model model){

        List<BoardMaster> masterList =boardMasterService.listBoardMaster();
        model.addAttribute("masterList", masterList);

        return "/admin/board/master/list";
    }

    @RequestMapping(value="/register", method= RequestMethod.GET)
    public String boardMasterRegister(Model model){

        model.addAttribute("boardMasterForm", new BoardMaster());
        return "/admin/board/master/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String boardMasterInsert(@ModelAttribute("boardMasterForm") BoardMaster boardMasterForm,
                                    BindingResult bindingResult,
                                    Model model) {

        boardMasterService.saveBoardMaster(boardMasterForm);
        return "redirect:/admin/board/master/list";
    }

    @RequestMapping(value="/modify/{id}", method=RequestMethod.GET)
    public String boardMasterModify(Model model, @PathVariable int id){

        BoardMaster boardMaster=boardMasterService.showBoardMaster(id);
        model.addAttribute("boardMaster", boardMaster);

        return "/admin/board/master/modify";
    }

    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    public String boardMasterupdate(@PathVariable int id, BoardMaster boardMaster) {

        boardMasterService.updateBoardMaster(id, boardMaster);

        return "redirect:/admin/board/master/list";
    }

    @GetMapping(value="/remove/{id}")
    @ResponseBody
    public String userRemove(@PathVariable int id){

        BoardMaster boardMaster = boardMasterService.deleteBoardMaster(id);
        String success="success";

        if(boardMaster==null){
            success="fail";
        }

        return success;
    }


}
