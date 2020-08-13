package tr.com.yigithanbalci.shoppingcartservice.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tr.com.yigithanbalci.shoppingcartservice.model.User;
import tr.com.yigithanbalci.shoppingcartservice.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void whenFindByUsername_thenReturnUser(){
    User user = User.builder().username("yigit").password("yigit").build();
    entityManager.persist(user);
    entityManager.flush();

    User found = userRepository.getUserByUsername(user.getUsername());

    assertThat(found.getUsername()).isEqualTo(user.getUsername());
  }
}