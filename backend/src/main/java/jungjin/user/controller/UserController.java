package jungjin.user.controller;

import jungjin.board.service.BoardService;
import jungjin.estimate.service.EstimateService;
import jungjin.user.domain.Role;
import jungjin.user.domain.User;
import jungjin.user.service.RoleService;
import jungjin.user.service.UserCustom;
import jungjin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping({"/user"})
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    EstimateService estimateService;

    @Autowired
    private BoardService boardService;

    @RequestMapping(value = {"/mypage"}, method = {RequestMethod.GET})
    public String mypage(Model model) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        User userInfo = this.userService.showUser(userNum);
        model.addAttribute("user", userInfo);
        return "/user/mypage";
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.GET})
    public String userRegister(Model model) {
        model.addAttribute("userForm", new User());
        return "/user/register";
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
    public String userInsert(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        Role userRole = this.roleService.showRole(Long.valueOf(2L));
        User result = this.userService.saveUser(userForm, userRole);
        if (result != null)
            model.addAttribute("msg", "success");
        return "/login";
    }

    @RequestMapping(value = {"/modify/{num}"}, method = {RequestMethod.GET})
    public String userModify(Model model, @PathVariable Long num) {
        User user = this.userService.showUser(num);
        model.addAttribute("user", user);
        return "/user/modify";
    }

    @RequestMapping(value = {"/modify/{num}"}, method = {RequestMethod.POST})
    public String update(@PathVariable Long num, User user) {
        this.userService.updateUser(num, user);
        return "redirect:/user/mypage";
    }

    @GetMapping({"/remove/{num}"})
    @ResponseBody
    public String userRemove(@PathVariable Long num) {
        User user = this.userService.deleteUser(num);
        String success = "success";
        if (user == null)
            success = "fail";
        return success;
    }

    @GetMapping({"/findId"})
    @ResponseBody
    public String findId(@RequestParam String id) {
        User user = this.userService.findById(id);
        String success = "success";
        if (user != null)
            success = "fail";
        return success;
    }
}
