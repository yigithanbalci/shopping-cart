package tr.com.yigithanbalci.shoppingcartservice.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tr.com.yigithanbalci.shoppingcartservice.auth.UserDetailsImpl;
import tr.com.yigithanbalci.shoppingcartservice.model.User;
import tr.com.yigithanbalci.shoppingcartservice.repository.UserRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.impl.UserDetailsServiceImpl;

@ExtendWith(SpringExtension.class)
public class UserDetailsServiceTests {

  private UserDetailsServiceImpl userDetailsService;

  @MockBean
  private UserRepository userRepository;

  @BeforeEach
  public void setUp() {
    userDetailsService = new UserDetailsServiceImpl(userRepository);
  }

  @Test
  public void whenValidName_thenUserShouldBeFound() {
    User user = new User();
    user.setId(1L);
    user.setUsername("yigit");
    user.setPassword("yigit");
    user.setRole("USER");
    user.setOrders(5L);

    UserDetailsImpl userDetails = new UserDetailsImpl(user);

    Mockito.when(userRepository.getUserByUsername("yigit")).thenReturn(user);

    assertThat(userDetailsService.loadUserByUsername("yigit")).isEqualTo(userDetails);
  }

  @Test
  public void whenNotName_thenExceptionShouldBeThrown() {
    Mockito.when(userRepository.getUserByUsername("yigit")).thenReturn(null);
    assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> userDetailsService.loadUserByUsername("yigit"));
  }
}
