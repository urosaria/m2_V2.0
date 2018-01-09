package jungjin.login.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
@RequestMapping(value = "/login/admin")
public class LoginAdminController {

    @GetMapping(value="/main")
    public String userList(Model model){

        return "/admin/main";
    }
}
