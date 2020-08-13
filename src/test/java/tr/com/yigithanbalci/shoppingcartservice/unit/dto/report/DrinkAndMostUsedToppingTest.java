package tr.com.yigithanbalci.shoppingcartservice.unit.dto.report;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAndMostUsedTopping;

public class DrinkAndMostUsedToppingTest {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(DrinkAndMostUsedTopping.class).testing(Method.values()).areWellImplemented();
  }
}
