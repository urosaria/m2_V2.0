package jungjin.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccess implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = (RedirectStrategy)new DefaultRedirectStrategy();

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String redirectUrl = "/main";
        String sessionRedirect = (String)session.getAttribute("prevPage");
        if (sessionRedirect != null && !sessionRedirect.equals("")) {
            redirectUrl = sessionRedirect;
            session.removeAttribute("prevPage");
        }
        this.redirectStrategy.sendRedirect(request, response, redirectUrl);
    }
}
