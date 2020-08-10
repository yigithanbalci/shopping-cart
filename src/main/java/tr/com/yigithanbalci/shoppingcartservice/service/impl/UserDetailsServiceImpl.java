package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tr.com.yigithanbalci.shoppingcartservice.auth.UserDetailsImpl;
import tr.com.yigithanbalci.shoppingcartservice.model.User;
import tr.com.yigithanbalci.shoppingcartservice.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userRepository.getUserByUsername(s);

    if(user == null){
      throw new UsernameNotFoundException("Could not find user with name: " + s);
    }
    return new UserDetailsImpl(user);
  }
}
