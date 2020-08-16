package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;

@ExtendWith(SpringExtension.class)
public class FinalizedCartTests {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(FinalizedCart.class).testing(Method.TO_STRING, Method.GETTER, Method.EQUALS, Method.HASH_CODE, Method.CONSTRUCTOR).areWellImplemented();
  }
}
