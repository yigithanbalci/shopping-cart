package tr.com.yigithanbalci.shoppingcartservice.unit.dto.report;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;

public class CustomerAnalysisTest {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(CustomerAnalysis.class).testing(Method.values()).areWellImplemented();
  }
}
