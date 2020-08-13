package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.Drink;

public class DrinkTest {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(Drink.class).testing(Method.values()).areWellImplemented();
  }
}
