package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.Topping;

@ExtendWith(SpringExtension.class)
public class ToppingTests {

  @Test
  public void testDataMethods() {
    assertPojoMethodsFor(Topping.class)
        .testing(Method.HASH_CODE, Method.CONSTRUCTOR, Method.EQUALS, Method.GETTER,
            Method.TO_STRING).areWellImplemented();
  }
}
