package jungjin.login.controller;


import jungjin.user.service.SecurityService;
import jungjin.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jungjin.user.domain.User;

@Controller
@RequestMapping(value = "/login")
public class LoginAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value="/main", method = RequestMethod.GET)
    public String userList(Model model){

        return "/admin/main";
    }

    @RequestMapping(value="/loginError")
    public String loginError(Model model, String username ){
        model.addAttribute("error", "Your username and password is invalid.");
        return "/admin/main";
    }

}
