package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.Cart;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;

public class CartTest {
  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(Cart.class).testing(Method.values()).areWellImplemented();
  }

  @Test
  public void testAddItem(){
    Cart cart = new Cart();
    Item item = new Item();
    item.setAmount(5.0f);

    cart.addItem(item);
    assertEquals(cart.getAmount(), 5.0f);
    cart.addItem(item);
    assertEquals(cart.getAmount(), 10.0f);

  }
}
