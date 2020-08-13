package tr.com.yigithanbalci.shoppingcartservice.unit.model;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.model.Customer;

@RunWith(SpringRunner.class)
public class CustomerTest {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(Customer.class).testing(Method.values()).areWellImplemented();
  }
}
