package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.ToppingInput;

@ExtendWith(SpringExtension.class)
public class ToppingInputTests {

  @Test
  public void testDataMethods() {
    assertPojoMethodsFor(ToppingInput.class).testing(Method.TO_STRING, Method.GETTER, Method.EQUALS, Method.HASH_CODE, Method.CONSTRUCTOR).areWellImplemented();
  }
}