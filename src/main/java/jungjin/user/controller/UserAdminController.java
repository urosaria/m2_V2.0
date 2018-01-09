package jungjin.user.controller;

import jungjin.user.domain.User;
import jungjin.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/user/admin")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/list")
    public String userList(Model model){

        List<User> userList =userService.listUser();
        model.addAttribute("userList", userList);

        return "/admin/user/list";
    }

    @GetMapping(value="/register")
    public String userRegister(Model model){

        return "/admin/user/register";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String userInsert(User user) {

        userService.saveUser(user);

        return "redirect:/user/admin/list";
    }

    @GetMapping(value="/modify/{num}")
    public String userModify(Model model, @PathVariable Long num){

        User user=userService.showUser(num);
        model.addAttribute("user", user);

        return "/admin/user/modify";
    }

    @RequestMapping(value = "/update/{num}")
    public String update(@PathVariable Long num, User user) {

        userService.updateUser(num, user);

        return "redirect:/user/admin/list";
    }

    @GetMapping(value="/remove/{num}")
    @ResponseBody
    public String userRemove(@PathVariable Long num){

        User user = userService.deleteUser(num);
        String success="success";

        if(user==null){
            success="fail";
        }

        return success;
    }


    @GetMapping(value="/findId")
    @ResponseBody
    public String findId(@RequestParam String id){

        User user = userService.findById(id);
        String success="success";

        if(user!=null){
            success="fail";
        }

        return success;
    }
}
