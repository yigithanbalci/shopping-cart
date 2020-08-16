package tr.com.yigithanbalci.shoppingcartservice.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;
import tr.com.yigithanbalci.shoppingcartservice.repository.CartRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.impl.CartRepositoryImpl;

@ExtendWith(SpringExtension.class)
public class CartRepositoryTests {

  @Test
  public void whenFindByUserId_thenReturnCart_thenDeleteCart() {
    CartRepository cartRepository = new CartRepositoryImpl();
    Cart cart = new Cart();
    Item item = Item.createWithDrink(
        Drink.createWithIdAndNameAndPrice(5L, "White Chocolate", BigDecimal.valueOf(5.0)));
    item.addTopping(Topping.createWithIdAndNameAndPrice(5L, "Milk", BigDecimal.valueOf(2.0)));
    cart.addItem(item);

    cartRepository.updateByUserId(cart, 1L);
    Cart found = cartRepository.findByUserId(1L);

    assertThat(found).isEqualTo(cart);

    cartRepository.deleteByUserId(1L);
    Cart retrieved = cartRepository.findByUserId(1L);

    assertThat(retrieved.getItems()).isEmpty();
  }
}
