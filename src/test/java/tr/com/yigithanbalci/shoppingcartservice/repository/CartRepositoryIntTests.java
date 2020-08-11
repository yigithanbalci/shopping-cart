package tr.com.yigithanbalci.shoppingcartservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.repository.impl.CartRepositoryImpl;

@RunWith(SpringRunner.class)
public class CartRepositoryIntTests {

  @Test
  public void whenFindByUserId_thenReturnCart_thenDeleteCart() {
    CartRepository cartRepository = new CartRepositoryImpl();
    Cart cart = new Cart();
    Item item = new Item(Drink.builder().name("White Chocolate").price(5.0f).build());
    item.addTopping(Topping.builder().name("Milk").price(2.0f).build());
    cart.addItem(item);

    cartRepository.updateByUserId(cart, 1L);
    Cart found = cartRepository.findByUserId(1L);

    assertThat(found).isEqualTo(cart);

    cartRepository.deleteByUserId(1L);
    Cart retreivedToNull = cartRepository.findByUserId(1L);

    assertThat(retreivedToNull).isNull();
  }
}
