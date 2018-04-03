package com.sb.config;

import com.sb.services.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by Kingsley Kumar on 19/03/2018 at 20:59.
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebSecurity
@Configuration
//@EnableMongoRepositories(basePackageClasses = UsersRepository.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private LogoutHandler logoutHandler;

    public SecurityConfiguration(UserService userService, LogoutHandler logoutHandler) {
        this.userService = userService;
        this.logoutHandler = logoutHandler;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/resources/**")
           .antMatchers("*.js")
           .antMatchers("*.css")
//           .antMatchers("*.png")
           .antMatchers("/js/*")
           .antMatchers("/css/*")
           .antMatchers("/images/*");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        System.out.println("************ SecurityConfiguration.configure");

        auth.userDetailsService(userService)
            .passwordEncoder(getPasswordEncoder());

//        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").authorities("ROLE_USER").and()
//                         .withUser("javainuse").password("{noop}javainuse").authorities("ROLE_USER", "ROLE_ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("/",
                         "/index.jsp",
                         "/test.jsp",
                         "/css/main.css",
                         "/css/jquery.mCustomScrollbar.css",
                         "/jsp/view_bs/navbar-homepage.jsp",
                         "/jsp/view_bs/footer-element.jsp",
                         "/js/jquery.mCustomScrollbar.concat.min.js",
                         "/main",
                         "/main/*",
                         "/webjars/*",
                         "/signup",
                         "/resetemail",
                         "/verifyemail",
                         "/message.jsp",
                         "/message",
                         "/resetpassword",
                         "/welcome",
                         "/contactus",
                         "/contactus/*").permitAll()
//            .antMatchers("/main").hasAnyRole("USER", "ADMIN")
            .anyRequest().fullyAuthenticated()
            .and()
            .formLogin().loginPage("/login").successHandler(new PostLoginURLRedirectionHandler()).permitAll()
            .and()
            .logout()
//            .logoutUrl("/logout")
            .logoutSuccessHandler(logoutHandler)
            .invalidateHttpSession(true)
//            .logoutSuccessUrl("/postlogout")
            .permitAll();

        http.csrf().disable();

        http.addFilterAfter(new RequestFilter(), BasicAuthenticationFilter.class);
    }

    private PasswordEncoder getPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.equals(s);
            }
        };
    }

}
