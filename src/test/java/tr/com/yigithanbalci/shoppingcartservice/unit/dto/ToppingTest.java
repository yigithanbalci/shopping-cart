package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;

public class ToppingTest {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(Topping.class).testing(Method.values()).areWellImplemented();
  }
}
