package jungjin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    public static final String REMEMBER_ME_KEY = "REMEBMER_ME_KEY";

    public static final String REMEMBER_ME_COOKE_NAME = "REMEMBER_ME_COOKE";

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers(new String[] { "/h2console/**" });
        http.headers().frameOptions().sameOrigin();
        http.csrf().disable();
        ((HttpSecurity)((HttpSecurity)((HttpSecurity)((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http
                .authorizeRequests()

                .antMatchers(new String[] { "/**.html", "/user/findId", "/user/register", "/admin/login", "/js/**", "/css/**", "/images/**", "/resources/**", "/loginError", "/console/**" })).permitAll()

                .antMatchers(new String[] { "/admin/**" })).hasAnyAuthority(new String[] { "ADMIN" }).anyRequest()).authenticated()
                .and())
                .formLogin()
                .loginPage("/login")
                .successHandler(new CustomAuthenticationSuccess()))
                .failureHandler(new CustomAuthenticationFailure()))
                .permitAll())
                .and())
                .rememberMe()
                .key("REMEBMER_ME_KEY")
                .rememberMeServices(tokenBasedRememberMeServices())
                .and())
                .logout()
                .deleteCookies(new String[] { "REMEMBER_ME_COOKE" }).deleteCookies(new String[] { "JSESSIONID" }).invalidateHttpSession(true)
                .logoutUrl("/logout")
                .permitAll()
                .and())
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder((PasswordEncoder)bCryptPasswordEncoder());
    }

    @Bean
    public RememberMeServices tokenBasedRememberMeServices() {
        TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices("REMEBMER_ME_KEY", userDetailsService());
        tokenBasedRememberMeServices.setAlwaysRemember(true);
        tokenBasedRememberMeServices.setTokenValiditySeconds(2678400);
        tokenBasedRememberMeServices.setCookieName("REMEMBER_ME_COOKE");
        return (RememberMeServices)tokenBasedRememberMeServices;
    }
}
