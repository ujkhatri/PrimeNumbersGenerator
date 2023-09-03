package com.ujjwalkhatri.primenumbers;

import com.ujjwalkhatri.primenumbers.enums.Algorithm;
import com.ujjwalkhatri.primenumbers.model.PrimesResponse;
import com.ujjwalkhatri.primenumbers.service.PrimesGeneratorService;
import com.ujjwalkhatri.primenumbers.service.PrimesService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PrimesServiceTest {

    @InjectMocks
    private PrimesService primesService;

    @Mock
    private PrimesGeneratorService primesGeneratorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPrimeNumbersForSieveAlgorithm() {
        int inputNumber = 120_000;
        Algorithm algorithm = Algorithm.SIEVE;

        when(primesGeneratorService.fetchOrComputePrimes(anyInt(), anyInt(), eq(algorithm)))
                .thenReturn(Arrays.asList(2, 3));

        PrimesResponse response = primesService.getPrimeNumbers(inputNumber, algorithm);

        assertEquals(2 * 2, response.getPrimes().size());
        assertEquals((Integer) 2, response.getPrimes().get(0));
        assertEquals((Integer) 3, response.getPrimes().get(1));
    }

    @Test
    public void getPrimeNumbersForDefaultAlgorithm() {
        int inputNumber = 120_000;
        Algorithm algorithm = Algorithm.DEFAULT;

        when(primesGeneratorService.fetchOrComputePrimes(anyInt(), anyInt(), eq(algorithm)))
                .thenReturn(Arrays.asList(2, 3));

        PrimesResponse response = primesService.getPrimeNumbers(inputNumber, algorithm);

        assertEquals(2 * 2, response.getPrimes().size());
        assertEquals((Integer) 2, response.getPrimes().get(0));
        assertEquals((Integer) 3, response.getPrimes().get(1));
    }
}

