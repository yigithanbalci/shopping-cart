package tr.com.yigithanbalci.shoppingcartservice.config;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tr.com.yigithanbalci.shoppingcartservice.model.User;
import tr.com.yigithanbalci.shoppingcartservice.repository.UserRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.impl.UserDetailsServiceImpl;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserRepository userRepository;

  @PostConstruct
  public void init() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String adminpass = bCryptPasswordEncoder.encode("admin");
    String userpass = bCryptPasswordEncoder.encode("user");
    String user2pass = bCryptPasswordEncoder.encode("user2");
    User admin = User.builder().username("admin").password(adminpass).role("ADMIN").enabled(true)
        .build();
    User user = User.builder().username("user").password(userpass).role("USER").enabled(true)
        .build();
    User user2 = User.builder().username("user2").password(user2pass).role("USER").enabled(true)
        .build();
    userRepository.save(admin);
    userRepository.save(user);
    userRepository.save(user2);
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl(userRepository);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());

    return authenticationProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/reports/**").hasAuthority("ADMIN")
        .antMatchers("/products/**").hasAuthority("ADMIN").anyRequest().authenticated();
    http.httpBasic();
  }
}
