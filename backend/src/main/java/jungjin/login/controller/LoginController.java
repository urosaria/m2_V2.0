package jungjin.login.controller;

import jakarta.servlet.http.HttpServletRequest;
import jungjin.estimate.domain.Structure;
import jungjin.estimate.service.EstimateService;
import jungjin.user.service.SecurityService;
import jungjin.user.service.UserCustom;
import jungjin.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    EstimateService estimateService;

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET})
    public String login(Model model, HttpServletRequest request) {
        request.getSession().removeAttribute("prevPage");
        return "/login";
    }

    @RequestMapping(value = {"/main"}, method = {RequestMethod.GET})
    public String main(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        Page<Structure> listEstimate = this.estimateService.listEstimate(page, 4, userNum);
        model.addAttribute("listEstimate", listEstimate);
        return "/main";
    }

    @RequestMapping(value = {"/main_new"}, method = {RequestMethod.GET})
    public String main_new(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        UserCustom principal = (UserCustom)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userNum = principal.getUser().getNum();
        Page<Structure> listEstimate = this.estimateService.listEstimate(page, 4, userNum);
        model.addAttribute("listEstimate", listEstimate);
        return "/main_new";
    }

    @RequestMapping(value = {"/originalImg2"}, method = {RequestMethod.GET})
    public String img() {
        return "/originalImg";
    }
}
