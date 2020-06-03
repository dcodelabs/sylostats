package com.sylo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sylo.model.EquityHistory;
import com.sylo.model.EquityPrice;
import com.sylo.model.Example;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author dhanavenkateshgopal on 15/5/20.
 * @project sylostats
 */
@RestController
@Slf4j
public class StockController {
  private final RestTemplate restTemplate;
  private final ObjectMapper mapper ;

  public StockController(RestTemplate restTemplate, ObjectMapper mapper) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;
  }
  @GetMapping("/api/prices")
  public Example getprices() {

    Example histories =null;
    try {
       histories = mapper.readValue(new ClassPathResource("stock-history.json").getInputStream(), Example.class);

    } catch (IOException e) {
      System.out.println("Unable to save users: " + e.getMessage());
    }
    return histories;
  }

  @GetMapping("/test")
  public String greeting() {
    return "Hello World!!";
  }
  @GetMapping("/api/equity/history")
  public ResponseEntity<List<EquityHistory>> getEquityHistory(
      //      @RequestHeader("AppID") String appId,
      @RequestParam(value = "stockExchange", required = true) String stockExchange,
      @RequestParam(value = "stockId", required = true) String stockId,
      @RequestParam(value = "stockInterval", required = true) String stockInterval,
      @RequestParam(value = "fromDate", required = true) String fromDate,
      @RequestParam(value = "toDate", required = true) String toDate)
      throws ParseException, URISyntaxException {
    List<EquityHistory> histories = new ArrayList<>();
    try {
      log.info("StockExchange: {}", stockExchange);
      log.info("stockId: {}", stockId);
      log.info("stockInterval: {}", stockInterval);
      log.info("fromDate: {}", fromDate);
      log.info("toDate: {}", toDate);

      SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
      log.info("Date format: {}", format.parse(fromDate));
      log.info("Epoch date: {}", format.parse(fromDate).toInstant().toEpochMilli());
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(format.parse(fromDate));

      for (EquityPrice price : getEquityPrices()) {
        EquityHistory history = new EquityHistory();
        history.setExchange(stockExchange);
        history.setStockId(stockId);
        history.setInterval(stockInterval);
        history.setMonth(calendar.get(Calendar.MONTH));
        history.setYear(calendar.get(Calendar.YEAR));
        history.setOpen(price.getOpen());
        history.setClose(price.getClose());
        history.setHigh(price.getHigh());
        history.setLow(price.getLow());
        histories.add(history);
      }

    } catch (Exception ex) {
      throw ex;
    }
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(histories);
  }

  private List<EquityPrice> getEquityPrices() {

    EquityPrice price = new EquityPrice();
    price.setOpen(4482);
    price.setClose(4571.14990234375);
    price.setDate(1589536785);
    price.setHigh(4615);
    price.setLow(4482);
    price.setVolume(919);
    price.setAdjclose(4571.14990234375);
    return Arrays.asList(price);
  }

  @GetMapping("/api/test/prices")
  public ResponseEntity<Example> getHistory() throws URISyntaxException {
    final String url =
        "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-historical-data";
    UriComponentsBuilder uriComponentsBuilder =
        UriComponentsBuilder.fromUri(new URI(url))
            .queryParam("frequency", "1mo")
            .queryParam("filter", "history")
            .queryParam("period1", "1546448400")
            .queryParam("period2", "1589637600000")
            .queryParam("symbol", "ATUL.BO");

    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com");
    headers.add("x-rapidapi-key", "22ad13d323msh2ec2c756f6f0a46p18d9cbjsn49bd8f2ac5cd");
    headers.add("useQueryString", "true");
    headers.add("Content-Type", "application/json");
    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity<Example> prices =
        restTemplate.exchange(
            uriComponentsBuilder.toUriString(), HttpMethod.GET, entity, Example.class);
    return prices;
  }
}
