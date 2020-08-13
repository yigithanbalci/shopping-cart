package tr.com.yigithanbalci.shoppingcartservice.unit.model;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.model.User;

public class UserTest {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(User.class).testing(Method.values()).areWellImplemented();
  }
}
