package tr.com.yigithanbalci.shoppingcartservice.dev;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import tr.com.yigithanbalci.shoppingcartservice.model.Customer;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.ToppingEntity;
import tr.com.yigithanbalci.shoppingcartservice.model.User;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.ToppingRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.UserRepository;

@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevEnvInitializer {

  private final UserRepository userRepository;
  private final DrinkRepository drinkRepository;
  private final ToppingRepository toppingRepository;

  @PostConstruct
  public void init(){
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String adminpass = bCryptPasswordEncoder.encode("admin");
    String user1pass = bCryptPasswordEncoder.encode("user1");
    String user2pass = bCryptPasswordEncoder.encode("user2");
    String user3pass = bCryptPasswordEncoder.encode("user3");
    User admin = User.builder().username("admin").password(adminpass).role("ADMIN").enabled(true)
        .build();
    User user1 = User.builder().username("user1").password(user1pass).role("USER").enabled(true)
        .build();
    User user2 = User.builder().username("user2").password(user2pass).role("USER").enabled(true)
        .build();
    User user3 = User.builder().username("user3").password(user3pass).role("USER").enabled(true)
        .build();
    Customer customer1 = new Customer();
    Customer customer2 = new Customer();
    Customer customer3 = new Customer();

    user1.setCustomer(customer1);
    customer1.setUser(user1);
    user2.setCustomer(customer2);
    customer2.setUser(user2);
    user3.setCustomer(customer3);
    customer3.setUser(user3);

    userRepository.save(admin);
    userRepository.save(user1);
    userRepository.save(user2);
    userRepository.save(user3);

    drinkRepository.save(DrinkEntity.builder().name("Black Coffee").price(4.0f).build());
    drinkRepository.save(DrinkEntity.builder().name("Latte").price(5.0f).build());
    drinkRepository.save(DrinkEntity.builder().name("Mocha").price(6.0f).build());
    drinkRepository.save(DrinkEntity.builder().name("Tea").price(3.0f).build());

    toppingRepository.save(ToppingEntity.builder().name("Milk").price(2.0f).build());
    toppingRepository.save(ToppingEntity.builder().name("Hazelnut syrup").price(3.0f).build());
    toppingRepository.save(ToppingEntity.builder().name("Chocolate sauce").price(5.0f).build());
    toppingRepository.save(ToppingEntity.builder().name("Lemon").price(2.0f).build());
  }
}
