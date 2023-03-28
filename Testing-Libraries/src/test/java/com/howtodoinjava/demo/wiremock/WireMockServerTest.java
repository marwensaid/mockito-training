package com.howtodoinjava.demo.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WireMockServerTest {

//  @Rule
//  public WireMockRule wireMockRule = new WireMockRule(options().withPort(3333));

  static WireMockServer wireMockServer = new WireMockServer();

  @BeforeAll
  public static void beforeAll() {
    //WireMock.configureFor("custom-host", 9000, "/api-root-url");
    wireMockServer.start();
  }

  @AfterAll
  public static void afterAll() {
    wireMockServer.stop();
  }

  @AfterEach
  public void afterEach() {
    wireMockServer.resetAll();
  }

  @Test
  void contextLoads() {
    assertTrue(true);
  }

  @Test
  void simplestStubTest() {
    String api_url = "/resource";
    String responseBody = "Hello world!";

    stubFor(get(urlEqualTo("/resource"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE)
            .withBody("Hello world!")));


    //Given
    stubFor(get(api_url)
        .willReturn(ok(responseBody)));

    //Test
    ResponseEntity<String> response = getForEntity(api_url);

    //Verify
    assertEquals(response.getBody(), responseBody);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void authStubTest() {
    String api_url = "/protected-url";

    stubFor(get(api_url)
        .willReturn(unauthorized()));

    ResponseEntity<String> response = getForEntity(api_url);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  void postApiStubTest() {
    String api_url = "/create-resource";
    String requestBody = "{\"data\":\"empty\"}";
    String responseBody = "{\"data\":\"payload\"}";

    stubFor(post(api_url)
        .withRequestBody(equalToJson(requestBody))
        .willReturn(aResponse()
            .withStatus(201)
            .withHeader(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE)
            .withBody(responseBody)));

    ResponseEntity<String> response = postForEntity(api_url, requestBody);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(response.getBody(), responseBody);

    verify(exactly(1), postRequestedFor(urlEqualTo(api_url)));

    verify(lessThan(5), anyRequestedFor(anyUrl()));
    verify(lessThanOrExactly(5), anyRequestedFor(anyUrl()));
    verify(exactly(5), anyRequestedFor(anyUrl()));
    verify(moreThanOrExactly(5), anyRequestedFor(anyUrl()));
    verify(moreThan(5), anyRequestedFor(anyUrl()));
  }

  @Test
  void stubWithJson() {
    stubFor(get(urlEqualTo("/json-resource"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE)
            .withBody("{ \"message\": \"Hello world!\" }")));

    ResponseEntity<String> response = getForEntity("/json-resource");

    assertEquals("{ \"message\": \"Hello world!\" }", response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON_VALUE,
            Objects.requireNonNull(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).get(0));
  }

  private ResponseEntity<String> getForEntity(String url) {

    TestRestTemplate testRestTemplate = new TestRestTemplate();
    return testRestTemplate.
        getForEntity(wireMockServer.baseUrl() + url, String.class);
  }

  private ResponseEntity<String> postForEntity(String url, String requestBody) {

    TestRestTemplate testRestTemplate = new TestRestTemplate();
    return testRestTemplate.
        postForEntity(wireMockServer.baseUrl() + url, requestBody,
            String.class);
  }

}
