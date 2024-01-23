package com.mieker.wiremock;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest(httpPort = 8082)
@Slf4j
class WiremockApplicationTests {

	public static final String RESPONSE_BODY = "Message from Wiremock stub.";
	public static final String SERVER_URL = "http://localhost:8082";
	public static final String SOURCE_PATH = "/api/source";
	private final RestTemplate restTemplate = new RestTemplate();

	@Test
	void wiremockTest() {

		stubFor(get(SOURCE_PATH).willReturn(ok().withBody(RESPONSE_BODY)));

		ResponseEntity<String> response
				= restTemplate.getForEntity(SERVER_URL + SOURCE_PATH, String.class);

		log.info(response.toString());

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(RESPONSE_BODY, response.getBody());
	}
}
