package tr.com.yigithanbalci.shoppingcartservice.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.CustomerAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.dto.report.DrinkAnalysis;
import tr.com.yigithanbalci.shoppingcartservice.repository.CustomerRepository;
import tr.com.yigithanbalci.shoppingcartservice.repository.DrinkRepository;
import tr.com.yigithanbalci.shoppingcartservice.service.ReportService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  @NonNull private final CustomerRepository customerRepository;
  @NonNull private final DrinkRepository drinkRepository;

  @Override
  public CustomerAnalysis customerAnalysisReport() {
    return null;
  }

  @Override
  public DrinkAnalysis drinkAnalysisReport() {
    return null;
  }
}
