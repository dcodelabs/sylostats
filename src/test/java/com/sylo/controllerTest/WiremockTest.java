package com.sylo.controllerTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dhanavenkateshgopal on 23/5/20.
 * @project sylostats
 */
public class WiremockTest {

  private WireMockServer wireMockServer = new WireMockServer();
  private CloseableHttpClient httpClient = HttpClients.createDefault();
  private RestTemplate restTemplate;
String url = "/api/message";
  @Test
  public void givenProgrammaticallyManagedServer_whenUsingSimpleStubbing_thenCorrect()
      throws IOException {
    wireMockServer.start();

    configureFor("localhost", 8080);
    stubFor(get(urlEqualTo("/greeting")).willReturn(aResponse().withBody("my controller!")));

    HttpGet request = new HttpGet("http://localhost:8080/greeting");
    ResponseEntity<String> response = restTemplate.getForEntity(buildUrl("/greeting"),String.class);


//    verify(getRequestedFor(urlEqualTo("/greeting")));
//    assertEquals("my controller!", stringResponse);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody(),"my controller!");

    wireMockServer.stop();
  }
  @Test
  public void mockStockRepo() throws IOException {
    wireMockServer.start();
    configureFor("localhost", 8080);
    stubFor(
        get(urlEqualTo("/api/equity/history"))
            .willReturn(
                aResponse()
                .withStatus(200)
                .withBodyFile("test-stock-repo/samplestock.json")
            )
    );
    HttpGet request = new HttpGet("http://localhost:8080/api/equity/history");
    HttpResponse httpResponse = httpClient.execute(request);
    String stringResponse = convertResponseToString(httpResponse);

  }

  private static String convertResponseToString(HttpResponse response) throws IOException {
    InputStream responseStream = response.getEntity().getContent();
    Scanner scanner = new Scanner(responseStream, "UTF-8");
    String stringResponse = scanner.useDelimiter("\\Z").next();
    scanner.close();
    return stringResponse;
  }

  private void readFile(String path) {
    ClassPathResource source = new ClassPathResource(path);
  }

  private String buildUrl(String path){
    return String.format("http://localhost:%d"+path, this.wireMockServer.port());
  }
}
