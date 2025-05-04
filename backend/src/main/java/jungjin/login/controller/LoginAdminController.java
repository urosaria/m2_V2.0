package jungjin.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jungjin.user.service.SecurityService;
import jungjin.user.service.UserDetailsServiceImpl;
import jungjin.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/admin"})
public class LoginAdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
    public String adminLoginPage(Model model, HttpServletRequest request) {
        request.getSession().setAttribute("prevPage", "/admin/user/list");
        return "/admin/main";
    }

    @RequestMapping(value = {"/login"}, method = {RequestMethod.POST})
    public String adminLogin(Model model, HttpServletRequest request, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        String autologin = this.securityService.adminLoginCheck(username, password);
        if (autologin != null)
            return "redirect:/admin/user/list";
        return "redirect:/admin/login";
    }

    @RequestMapping({"/loginError"})
    public String loginError(Model model, String username) {
        model.addAttribute("error", "Your username and password is invalid.");
        return "/admin/main";
    }

    @RequestMapping(value = {"/logout"}, method = {RequestMethod.GET})
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null)
            (new SecurityContextLogoutHandler()).logout(request, response, auth);
        return "redirect:/admin/login";
    }
}
