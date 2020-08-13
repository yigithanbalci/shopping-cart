package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;

public class FinalizedCartTest {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(FinalizedCart.class).testing(Method.values()).areWellImplemented();
  }
}
