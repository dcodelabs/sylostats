package com.sylo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sylo.model.EquityPrice;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

@SpringBootApplication
public class SylostatsApplication {

  public static void main(String[] args) {

//    SpringApplication.run(SylostatsApplication.class, args);
    new SpringApplicationBuilder(SylostatsApplication.class).properties("spring.config.name=application,stocks").run(args);
  }
//  @Bean
//  public RestTemplate restTemplate(RestTemplateBuilder builder) {
//    return builder.build();
//  }
@Bean
public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
  TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

  SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
      .loadTrustMaterial(null, acceptingTrustStrategy)
      .build();

  SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

  CloseableHttpClient httpClient = HttpClients.custom()
      .setSSLSocketFactory(csf)
      .build();

  HttpComponentsClientHttpRequestFactory requestFactory =
      new HttpComponentsClientHttpRequestFactory();

  requestFactory.setHttpClient(httpClient);
  RestTemplate restTemplate = new RestTemplate(requestFactory);
  return restTemplate;
}

  @Bean
  public ObjectMapper objectMapperBuilder() {
    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    builder.serializationInclusion(JsonInclude.Include.NON_NULL);
    return builder.build();
  }

}
