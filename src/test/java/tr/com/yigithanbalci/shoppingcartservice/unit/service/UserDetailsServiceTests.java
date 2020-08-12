package tr.com.yigithanbalci.shoppingcartservice.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import tr.com.yigithanbalci.shoppingcartservice.auth.UserDetailsImpl;
import tr.com.yigithanbalci.shoppingcartservice.model.User;
import tr.com.yigithanbalci.shoppingcartservice.repository.UserRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.impl.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
public class UserDetailsServiceTests {

  private UserDetailsServiceImpl userDetailsService;

  @MockBean
  private UserRepository userRepository;

  @Before
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

  @Test(expected = UsernameNotFoundException.class)
  public void whenNotName_thenExceptionShouldBeThrown() {
    Mockito.when(userRepository.getUserByUsername("yigit")).thenReturn(null);
    userDetailsService.loadUserByUsername("yigit");
  }
}
