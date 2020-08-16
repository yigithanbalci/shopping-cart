package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.DrinkInput;

@ExtendWith(SpringExtension.class)
public class DrinkInputTests {

  @Test
  public void testDataMethods() {
    assertPojoMethodsFor(DrinkInput.class).testing(Method.TO_STRING, Method.GETTER, Method.EQUALS, Method.HASH_CODE, Method.CONSTRUCTOR).areWellImplemented();
  }
}