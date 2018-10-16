package idv.mission.example.SpringBootSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import idv.mission.example.SpringBootSecurity.dao.AccountService;
import idv.mission.example.SpringBootSecurity.dao.AuthorityService;
import idv.mission.example.SpringBootSecurity.jwt.AuthenticationFilter;
import idv.mission.example.SpringBootSecurity.jwt.AuthorizationFilter;

@EnableWebSecurity
public class WebSecurityConfigJWT extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .antMatchers(HttpMethod.POST, "/logout").permitAll()
            .antMatchers("/hello/**").hasAnyRole("ADMIN", "USER")
            .and()
            .addFilter(new AuthenticationFilter(authenticationManager()))
            .addFilter(new AuthorizationFilter(authenticationManager(), accountService, authorityService))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
