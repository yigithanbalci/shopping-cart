package tr.com.yigithanbalci.shoppingcartservice.unit.model;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.model.Customer;

public class CustomerTest {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(Customer.class).testing(Method.values()).areWellImplemented();
  }
}
