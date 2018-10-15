package idv.mission.example.SpringBootSecurity.jwt;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static idv.mission.example.SpringBootSecurity.jwt.JWT_Constants.HEADER_STRING;
import static idv.mission.example.SpringBootSecurity.jwt.JWT_Constants.SECRET;
import static idv.mission.example.SpringBootSecurity.jwt.JWT_Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;

import idv.mission.example.SpringBootSecurity.dao.Account;
import idv.mission.example.SpringBootSecurity.dao.AccountService;
import idv.mission.example.SpringBootSecurity.dao.Authority;
import idv.mission.example.SpringBootSecurity.dao.AuthorityService;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private AccountService accountService;

    private AuthorityService authorityService;

    public AuthorizationFilter(AuthenticationManager authManager, AccountService accountService, AuthorityService authorityService) {
        super(authManager);
        this.accountService = accountService;
        this.authorityService = authorityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilterInternal");
        String header = request.getHeader(HEADER_STRING);
        if( header == null || !header.startsWith(TOKEN_PREFIX) ) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if( token != null ) {
            String username = JWT.require(HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
            if( username != null ) {
                Account account = accountService.findByUsername(username);
                String password = account.getPassword();
                Authority authority = authorityService.findByUsername(username);
                String role = authority.getRole();
                SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
                roles.add(grantedAuthority);
                return new UsernamePasswordAuthenticationToken(username, password, roles);
            }
            return null;
        }
        return null;
    }
}
