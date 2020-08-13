package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;

public class ItemTest {
  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(Item.class).testing(Method.values()).areWellImplemented();
  }

  @Test
  public void testSetDrink(){
    Item item = new Item();
    Drink drink = Drink.builder().price(5.0f).build();
    Drink newDrink = Drink.builder().price(6.0f).build();
    item.setDrink(drink);

    assertEquals(item.getAmount(), 5.0f);
    item.setDrink(newDrink);
    assertEquals(item.getAmount(), 6.0f);
  }

  @Test
  public void testAddTopping(){
    Item item = new Item();
    Topping topping = Topping.builder().price(6.0f).build();
    item.setAmount(5.0f);

    assertEquals(item.getAmount(), 5.0f);
    item.addTopping(topping);
    assertEquals(item.getAmount(), 11.0f);

  }
}
