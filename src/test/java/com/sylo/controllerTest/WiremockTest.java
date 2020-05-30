package com.sylo.controllerTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dhanavenkateshgopal on 23/5/20.
 * @project sylostats
 */
public class WiremockTest {

    private WireMockServer wireMockServer = new WireMockServer();
    private CloseableHttpClient httpClient = HttpClients.createDefault();

    @Test
    public void givenProgrammaticallyManagedServer_whenUsingSimpleStubbing_thenCorrect() throws IOException {
        wireMockServer.start();

        configureFor("localhost", 8080);
        stubFor(get(urlEqualTo("/greeting")).willReturn(aResponse().withBody("my controller!")));

        HttpGet request = new HttpGet("http://localhost:8080/greeting");
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertResponseToString(httpResponse);

        verify(getRequestedFor(urlEqualTo("/greeting")));
        assertEquals("my controller!", stringResponse);

        wireMockServer.stop();
    }

    private static String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String stringResponse = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return stringResponse;
    }

}
