package com.ujjwalkhatri.primenumbers;

import com.ujjwalkhatri.primenumbers.model.PrimesResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class PrimesControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "http://localhost:";

    private static List<Integer> primeList = new ArrayList<>();

    @BeforeAll
    public void setUp() {
        primeList.add(2);
        primeList.add(3);
        primeList.add(5);
        primeList.add(7);
    }

    @Test
    public void badRequestWithInvalidInput() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(baseUrl + port + "/api/v1/primes/sample", String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid parameter provided: sample"));
    }

    @Test
    public void badRequestWithNegativeInput() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                baseUrl + port + "/api/v1/primes/-2",
                HttpMethod.GET,
                entity,
                String.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Input number cannot be negative"));
    }

    @Test
    public void primeNumbersWithDefaultAlgorithm() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        PrimesResponse expectedResponse = new PrimesResponse(10, primeList);

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<PrimesResponse> response = this.restTemplate.exchange(
                baseUrl + port + "/api/v1/primes/10?algorithm=sieve",
                HttpMethod.GET,
                entity,
                PrimesResponse.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getPrimes(), expectedResponse.getPrimes());
        assertEquals(response.getBody().getInitial(), expectedResponse.getInitial());

    }

    @Test
    public void primeNumbersWithXmlResponse() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));

        String expectedXmlResponse = new String(Files.readAllBytes(Paths.get(getClass().getResource("/XmlResponse.xml").toURI())));

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = this.restTemplate.exchange(
                baseUrl + port + "/api/v1/primes/10?algorithm=sieve",
                HttpMethod.GET,
                entity,
                String.class
        );

        Diff xmlDiff = DiffBuilder.compare(expectedXmlResponse)
                .withTest(response.getBody())
                .checkForSimilar()
                .ignoreWhitespace()
                .build();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(xmlDiff.hasDifferences(), () -> "Differences found: " + xmlDiff.toString());
    }
}

