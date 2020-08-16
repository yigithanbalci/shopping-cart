package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;
import tr.com.yigithanbalci.shoppingcartservice.dto.Item;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;

@ExtendWith(SpringExtension.class)
public class ItemTests {
  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(Item.class).testing(Method.values()).areWellImplemented();
  }

  @Test
  public void testSetDrink(){
    Drink latte = Drink.createWithIdAndNameAndPrice(6L, "Latte", BigDecimal.valueOf(5.0));
    Drink mocha = Drink.createWithIdAndNameAndPrice(7L, "Mocha", BigDecimal.valueOf(6.0));

    Item latteItem = Item.createWithDrink(latte);

    assertEquals(latteItem.getAmount(), BigDecimal.valueOf(5.0));
    latteItem.setDrink(mocha);
    assertEquals(latteItem.getAmount(), BigDecimal.valueOf(6.0));
  }

  @Test
  public void testAddTopping(){
    Drink latte = Drink.createWithIdAndNameAndPrice(6L, "Latte", BigDecimal.valueOf(5.0));

    Item latteItem = Item.createWithDrink(latte);

    Topping milk = Topping.createWithIdAndNameAndPrice(5L, "Milk", BigDecimal.valueOf(4.0));

    assertEquals(latteItem.getAmount(), BigDecimal.valueOf(5.0));
    latteItem.addTopping(milk);
    assertEquals(latteItem.getAmount(), BigDecimal.valueOf(9.0));
  }
}