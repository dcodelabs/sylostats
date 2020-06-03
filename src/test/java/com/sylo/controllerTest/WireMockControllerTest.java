package com.sylo.controllerTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dhanavenkateshgopal on 31/5/20.
 * @project sylostats
 */
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WireMockControllerTest {

    RestTemplate restTemplate = new RestTemplate();
  private WireMockServer wireMockServer;

  @BeforeEach
  void configureSystemUnderTest() {
    this.wireMockServer = new WireMockServer(options().dynamicPort());
    this.wireMockServer.start();
    configureFor(this.wireMockServer.port());
  }

  @AfterEach
  void stopWireMockServer() {
    this.wireMockServer.stop();
  }

  @Test
  public void givenProgrammaticallyManagedServer_whenUsingSimpleStubbing_thenCorrect()
      throws IOException {
    givenThat(
        get(urlEqualTo("/greeting"))
            .willReturn(aResponse().withStatus(200).withBody("Hello World!!")));
    String serverUrl = buildUrl("/greeting");
    ResponseEntity<String> response = restTemplate.getForEntity(serverUrl, String.class);

    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody(), "Hello World!!");
  }

  @Test
  public void getStockHistory() throws IOException{
    givenThat(get(urlEqualTo("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-historical-data"))
        .withHeader("x-rapidapi-key",equalTo("22ad13d323msh2ec2c756f6f0a46p18d9cbjsn49bd8f2ac5cd"))
        .withHeader("useQueryString",equalTo("true"))
        .withHeader("Content-Type",equalTo("application/json"))
        .withHeader("",equalTo(""))
        .withHeader("x-rapidapi-host", equalTo("apidojo-yahoo-finance-v1.p.rapidapi.com")).willReturn(
            aResponse()
            .withBodyFile("test-stock-repo/samplestock.json")
        )
    );
  }

  private String buildUrl(String path) {
    return String.format("http://localhost:%d" + path, this.wireMockServer.port());
  }
}
