package com.sylo.controller;

import com.sylo.model.StockHistoryRequest;
import com.sylo.model.UiStockHistoryResponse;
import com.sylo.model.YahooStockHistoryResponse;
import com.sylo.service.StockApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dhanavenkateshgopal on 15/5/20.
 * @project sylostats
 */
@RestController
@Slf4j
public class StockController {

  private StockApiClient stockApiClient;

  @Autowired
  public StockController(StockApiClient stockApiClient) {
    this.stockApiClient = stockApiClient;
  }

  @GetMapping("/api/equity/history")
  public List<UiStockHistoryResponse> getEquityHistory(
      @RequestParam(value = "stockExchange", required = true) String stockExchange,
      @RequestParam(value = "stockId", required = true) String stockId,
      @RequestParam(value = "stockInterval", required = true) String stockInterval,
      @RequestParam(value = "fromDate", required = true) String fromDate,
      @RequestParam(value = "toDate", required = true) String toDate)
      throws Exception {
    log.debug("StockExchange: {}", stockExchange);
    log.debug("stockId: {}", stockId);
    log.debug("stockInterval: {}", stockInterval);
    log.debug("fromDate: {}", fromDate);
    log.debug("toDate: {}", toDate);

    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    log.debug("Date format: {}", format.parse(fromDate));
    log.debug("From Epoch date: {}", format.parse(fromDate).toInstant().getEpochSecond());
    log.debug("To Epoch date: {}", format.parse(toDate).toInstant().getEpochSecond());
    StockHistoryRequest request =
        new StockHistoryRequest(
            stockId, stockInterval, "history", getEpochTime(fromDate), getEpochTime(toDate));
    YahooStockHistoryResponse response = stockApiClient.getHistory(request);
    if (response != null) {
      return response.getPrices().stream()
          .map(
              it ->
                  new UiStockHistoryResponse(
                      "BSE",
                      stockId,
                      toString(epochToDate(it.getDate()).getYear()),
                      epochToDate(it.getDate()).getMonth().name(),
                      toString(it.getOpen()),
                      toString(it.getHigh()),
                      toString(it.getHigh()),
                      toString(it.getClose())))
          .collect(Collectors.toList());
    } else {
      throw new Exception("Invalid input arguments. No response from server");
    }
  }

  private Optional<String> getEpochTime(String date) {
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    Optional<String> dateOptional = Optional.ofNullable(date);
    return dateOptional.map(
        given_date -> {
          try {
            log.info("Epoch time: {}", format.parse(date).toInstant().getEpochSecond());
            long value = format.parse(date).toInstant().getEpochSecond();
            return Long.toString(value);
          } catch (ParseException e) {
            log.error("Date parse error: {} ", date, e);
          }
          return null;
        });
  }

  private LocalDate epochToDate(Integer epochTime) {

    return Instant.ofEpochSecond(Long.valueOf(epochTime))
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
  }

  private String toString(Number number) {
    return String.valueOf(number);
  }
}
