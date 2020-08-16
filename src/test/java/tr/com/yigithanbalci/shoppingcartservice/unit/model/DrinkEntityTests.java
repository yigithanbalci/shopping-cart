package tr.com.yigithanbalci.shoppingcartservice.unit.model;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.model.DrinkEntity;

@ExtendWith(SpringExtension.class)
public class DrinkEntityTests {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(DrinkEntity.class).testing(Method.values()).areWellImplemented();
  }
}
