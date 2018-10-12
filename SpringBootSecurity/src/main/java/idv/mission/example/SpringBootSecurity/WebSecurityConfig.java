// package idv.mission.example.SpringBootSecurity;
//
// import javax.sql.DataSource;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.core.userdetails.UserDetailsService;
//
// @EnableWebSecurity
// public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
// @Autowired
// private UserDetailsService userDetailsService;
//
// @Autowired
// private DataSource dataSource;
//
// // Custom UserDetails Authentication
// // @Override
// @Autowired
// protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// auth.userDetailsService(userDetailsService);
// }
//
// // Custom UserDetails Authentication
// // @Override
// // @Autowired
// // protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// // auth.userDetailsService(userDetailsService);
// // }
//
// // JDBC Authentication
// // @Autowired
// // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// // String accountSql = "select username, password, 'true' from account where username = ?";
// // String authoritySql = "select username, role from authority where username = ?";
// // auth.jdbcAuthentication().dataSource(dataSource)
// // .usersByUsernameQuery(accountSql)
// // .authoritiesByUsernameQuery(authoritySql)
// // .passwordEncoder(NoOpPasswordEncoder.getInstance());
// // }
//
// // In-Memory Authentication
// // @Autowired
// // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// // // It's deprecated because spring doesn't recommend you use this in produciton environment.
// // auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("mission").password("mission").roles("USER");
// // // auth.inMemoryAuthentication().withUser("mission").password("{noop}mission").roles("USER");
// // }
//
// @Override
// protected void configure(HttpSecurity http) throws Exception {
// http.authorizeRequests().antMatchers("/hello/**").hasAnyRole("ADMIN", "USER").and().formLogin();
// }
//
// }
