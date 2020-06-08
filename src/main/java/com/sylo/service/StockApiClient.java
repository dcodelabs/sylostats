package com.sylo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sylo.config.ExternalConfig;
import com.sylo.model.StockHistoryRequest;
import com.sylo.model.YahooStockHistoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author dhanavenkateshgopal on 7/6/20.
 * @project sylostats
 */
@Service
@Slf4j
public class StockApiClient {
  private final RestTemplate restTemplate;
  private final ObjectMapper mapper;
  private final ExternalConfig externalConfig;

  @Autowired
  public StockApiClient(
      RestTemplate restTemplate, ObjectMapper mapper, ExternalConfig externalConfig) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;
    this.externalConfig = externalConfig;
  }

  public YahooStockHistoryResponse getHistory(StockHistoryRequest request) throws URISyntaxException {
    final String url = externalConfig.getApi().get("yahoo");
    UriComponentsBuilder uriComponentsBuilder;
     /* uriComponentsBuilder = UriComponentsBuilder.fromUri(new URI(url))
          .queryParam("frequency", "1mo")
          .queryParam("filter", "history")
          .queryParam("period1", "1546448400")
          .queryParam("period2", "1589637600000")
          .queryParam("symbol", "ATUL.BO");*/
  log.info("Stock from date {}",request.getPeriod1().get());
    log.info("Stock to date {}",request.getPeriod2().get());

    uriComponentsBuilder = UriComponentsBuilder.fromUri(new URI(url))
        .queryParam("frequency", request.getFrequency())
        .queryParam("filter", request.getFilter())
        .queryParam("period1", request.getPeriod1().get())
        .queryParam("period2", request.getPeriod2().get())
        .queryParam("symbol", request.getStockId());

      MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com");
    headers.add("x-rapidapi-key", "22ad13d323msh2ec2c756f6f0a46p18d9cbjsn49bd8f2ac5cd");
    headers.add("useQueryString", "true");
    headers.add("Content-Type", "application/json");
    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity<YahooStockHistoryResponse> prices =
        restTemplate.exchange(
            uriComponentsBuilder.toUriString(), HttpMethod.GET, entity, YahooStockHistoryResponse.class);
    return prices.getBody();
  }
}
