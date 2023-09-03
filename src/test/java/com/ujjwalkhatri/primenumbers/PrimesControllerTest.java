package com.ujjwalkhatri.primenumbers;

import com.ujjwalkhatri.primenumbers.controller.PrimesController;
import com.ujjwalkhatri.primenumbers.enums.Algorithm;
import com.ujjwalkhatri.primenumbers.exceptions.GlobalExceptionHandler;
import com.ujjwalkhatri.primenumbers.model.PrimesResponse;
import com.ujjwalkhatri.primenumbers.service.PrimesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrimesControllerTest {

    private static MockMvc mockMvc;
    @InjectMocks
    private static PrimesController primeNumberController;
    @Mock
    private PrimesService primesService;
    private static List<Integer> primeList = new ArrayList<>();

    int inputNumber = 10;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(primeNumberController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        primeList.add(2);
        primeList.add(3);
        primeList.add(5);
        primeList.add(7);
    }

    @Test
    public void calculatePrimeNumberJsonResponse() throws Exception {
        String acceptHeader = MediaType.APPLICATION_JSON_VALUE;
        when(primesService.getPrimeNumbers(anyInt(), any())).thenReturn(new PrimesResponse(primeList.size(), primeList));
        mockMvc.perform(get("/api/v1/primes/" + inputNumber)
                        .header("Accept", acceptHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Primes").value(primeList));
    }

    @Test
    public void calculatePrimeNumberWithSieveAlgorithm() throws Exception {
        when(primesService.getPrimeNumbers(anyInt(), any())).thenReturn(new PrimesResponse(primeList.size(), primeList));

        mockMvc.perform(get("/api/v1/primes/" + inputNumber)
                        .param("algorithm", String.valueOf(Algorithm.SIEVE))
                        .header("Accept", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Primes").value(primeList));

    }

    @Test
    public void calculatePrimeNumberXmlResponse() throws Exception {
        String acceptHeader = MediaType.APPLICATION_XML_VALUE;
        when(primesService.getPrimeNumbers(anyInt(), any())).thenReturn(new PrimesResponse(inputNumber, primeList));
        String expectedXmlResponse = new String(Files.readAllBytes(Paths.get(getClass().getResource("/XmlResponse.xml").toURI())));

        mockMvc.perform(get("/api/v1/primes/" + inputNumber)
                        .header("Accept", acceptHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/PrimesResponse/Initial").string("10"))
                .andExpect(content().xml(expectedXmlResponse));
    }

    @Test
    public void calculatePrimeNumberWithDefaultAlgorithm() throws Exception {
        when(primesService.getPrimeNumbers(anyInt(), any())).thenReturn(new PrimesResponse(primeList.size(), primeList));

        mockMvc.perform(get("/api/v1/primes/" + inputNumber)
                        .header("Accept", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Primes").value(primeList));;

    }

    @Test
    public void calculatePrimeNumberForInvalidInput() throws Exception {
        String inputNumber = "sample";
        mockMvc.perform(get("/api/v1/primes/" + inputNumber)
                        .header("Accept", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invalid parameter provided: sample"));
    }

    @Test
    public void calculatePrimeNumberJsonResponseForInvalidInput() throws Exception {
        String acceptHeader = MediaType.APPLICATION_JSON_VALUE;
        mockMvc.perform(get("/api/v1/primes/-1")
                        .header("Accept", acceptHeader))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Input number cannot be negative"));
    }

    @Test
    public void calculatePrimeNumberJsonResponseForInvalidAlgorithm() throws Exception {
        String acceptHeader = MediaType.APPLICATION_JSON_VALUE;
        mockMvc.perform(get("/api/v1/primes/-1?algorithm=invalid")
                        .header("Accept", acceptHeader))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invalid parameter provided: invalid"));
    }

}

