package tr.com.yigithanbalci.shoppingcartservice.unit.model;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkToppingRelation;

public class DrinkToppingRelationTest {
  @Test
  public void testDrinkToppingRelation(){
    assertPojoMethodsFor(DrinkToppingRelation.class).testing(Method.values()).areWellImplemented();
  }

}
