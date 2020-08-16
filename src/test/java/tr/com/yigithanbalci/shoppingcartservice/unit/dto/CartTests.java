package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;

@ExtendWith(SpringExtension.class)
public class CartTests {
  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(Cart.class).testing(Method.values()).areWellImplemented();
  }

  @Test
  public void testAddItem(){
    Cart cart = new Cart();
    Item item = Item.createWithDrink(Drink.createWithIdAndNameAndPrice(1L, "Latte", BigDecimal.valueOf(5.0)));

    cart.addItem(item);
    assertEquals(cart.getAmount(), BigDecimal.valueOf(5.0));
    cart.addItem(item);
    assertEquals(cart.getAmount(), BigDecimal.valueOf(10.0));

  }
}
