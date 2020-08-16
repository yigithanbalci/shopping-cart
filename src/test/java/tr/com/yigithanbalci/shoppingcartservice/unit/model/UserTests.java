package tr.com.yigithanbalci.shoppingcartservice.unit.model;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.model.User;

@ExtendWith(SpringExtension.class)
public class UserTests {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(User.class).testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR).areWellImplemented();
  }
}
