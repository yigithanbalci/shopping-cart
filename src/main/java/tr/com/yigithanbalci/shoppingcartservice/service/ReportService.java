package tr.com.yigithanbalci.shoppingcartservice.service;

import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAnalysis;

public interface ReportService {

  CustomerAnalysis customerAnalysisReport();
  DrinkAnalysis drinkAnalysisReport();
}
