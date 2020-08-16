package tr.com.yigithanbalci.shoppingcartservice.unit.dto.report;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pojo.tester.api.assertion.Method;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;

@ExtendWith(SpringExtension.class)
public class CustomerAnalysisTests {

  @Test
  public void testDataMethods(){
    assertPojoMethodsFor(CustomerAnalysis.class).testing(Method.values()).areWellImplemented();
  }
}
