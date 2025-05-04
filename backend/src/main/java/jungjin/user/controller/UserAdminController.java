package jungjin.user.controller;

import jungjin.user.domain.Role;
import jungjin.user.domain.User;
import jungjin.user.service.RoleService;
import jungjin.user.service.SecurityService;
import jungjin.user.service.UserService;
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
@RequestMapping({"/admin/user"})
public class UserAdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RoleService roleService;

    @RequestMapping({"/list"})
    public String userList(Model model, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "searchCondition", defaultValue = "") String searchCondition, @RequestParam(value = "searchText", defaultValue = "") String searchText) {
        Page<User> userList = null;
        if (searchCondition != null && !searchCondition.equals("")) {
            userList = this.userService.listSearchTextUser(searchCondition, searchText, page, 10);
        } else {
            userList = this.userService.listUser(page);
        }
        model.addAttribute("userList", userList);
        model.addAttribute("page", Integer.valueOf(page));
        model.addAttribute("searchCondition", searchCondition);
        model.addAttribute("searchText", searchText);
        return "/admin/user/list";
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.GET})
    public String userRegister(Model model) {
        model.addAttribute("userForm", new User());
        return "/admin/user/register";
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
    public String userInsert(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        Role userRole = this.roleService.showRole(Long.valueOf(1L));
        this.userService.saveUser(userForm, userRole);
        return "redirect:/admin/user/list";
    }

    @RequestMapping(value = {"/modify/{num}"}, method = {RequestMethod.GET})
    public String userModify(Model model, @PathVariable Long num) {
        User user = this.userService.showUser(num);
        model.addAttribute("user", user);
        return "/admin/user/modify";
    }

    @RequestMapping(value = {"/modify/{num}"}, method = {RequestMethod.POST})
    public String update(@PathVariable Long num, User user) {
        this.userService.updateUser(num, user);
        return "redirect:/admin/user/list";
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
