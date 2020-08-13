package tr.com.yigithanbalci.shoppingcartservice.unit.dto.report;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;

@RunWith(SpringRunner.class)
public class CustomerAnalysisTest {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(CustomerAnalysis.class).testing(Method.values()).areWellImplemented();
  }
}
