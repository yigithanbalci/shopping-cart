package tr.com.yigithanbalci.shoppingcartservice.unit.dto;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.FinalizedCart;

@RunWith(SpringRunner.class)
public class FinalizedCartTest {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(FinalizedCart.class).testing(Method.values()).areWellImplemented();
  }
}
