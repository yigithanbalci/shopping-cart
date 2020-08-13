package tr.com.yigithanbalci.shoppingcartservice.service;

import java.util.List;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAndMostUsedTopping;

public interface ReportService {

  List<CustomerAnalysis> getCustomerAnalysisReport();
  List<DrinkAndMostUsedTopping> getDrinkAnalysisReport();
}
