package idv.mission.example.SpringBootSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import idv.mission.example.SpringBootSecurity.dao.Account;
import idv.mission.example.SpringBootSecurity.dao.AccountService;
import idv.mission.example.SpringBootSecurity.dao.Authority;
import idv.mission.example.SpringBootSecurity.dao.AuthorityService;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findByUsername(username);
        Authority authority = authorityService.findByUsername(username);
        String role = authority.getRole();
        System.out.println("accountService = " + accountService);
        System.out.println("authorityService = " + authorityService);
        System.out.println("role = " + role);
        role = role.substring(role.indexOf("_") + 1, role.length()); // "ROLE_USER" -> "USER"
        System.out.println(role);
        UserBuilder builder = null;
        if( account != null ) {
            builder = User.withUsername(username);
            PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
            builder.passwordEncoder(p -> "{noop}" + p);
            builder.password(NoOpPasswordEncoder.getInstance().encode(account.getPassword()));
            builder.roles(role);
        }
        else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }

}