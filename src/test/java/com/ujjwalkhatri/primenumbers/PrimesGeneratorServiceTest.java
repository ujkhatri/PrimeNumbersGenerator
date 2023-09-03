package com.ujjwalkhatri.primenumbers;

import com.ujjwalkhatri.primenumbers.enums.Algorithm;
import com.ujjwalkhatri.primenumbers.generators.PrimesGenerator;
import com.ujjwalkhatri.primenumbers.generators.PrimesGeneratorDefault;
import com.ujjwalkhatri.primenumbers.generators.PrimesGeneratorSieve;
import com.ujjwalkhatri.primenumbers.service.PrimesGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PrimesGeneratorServiceTest {

    @Mock
    private PrimesGenerator mockPrimesGenerator;

    @Mock
    private PrimesGeneratorService primesGeneratorService;

    private final PrimesGenerator primeNumbersSieve = Mockito.spy(PrimesGeneratorSieve.class);
    private final PrimesGenerator primeNumbersDefault = Mockito.spy(PrimesGeneratorDefault.class);

    @Mock
    private CacheManager cacheManager;

    @BeforeEach
    public void setup() {
        when(primeNumbersSieve.getPrimeNumbers(anyInt(), anyInt())).thenReturn(Arrays.asList(2, 3, 5));
        when(primeNumbersDefault.getPrimeNumbers(anyInt(), anyInt())).thenReturn(Arrays.asList(2, 3, 5, 7));
        primesGeneratorService = new PrimesGeneratorService(Arrays.asList(primeNumbersSieve, primeNumbersDefault));
    }

    @Test
    public void testFetchOrComputePrimes() {
        List<Integer> expectedPrimes = List.of(2, 3, 5, 7);
        when(mockPrimesGenerator.getPrimeNumbers(anyInt(), anyInt())).thenReturn(expectedPrimes);

        List<Integer> actualPrimes = primesGeneratorService.fetchOrComputePrimes(2, 10, Algorithm.DEFAULT);
        assertEquals(expectedPrimes, actualPrimes);
    }

    @Test
    public void testFetchOrComputePrimesSieve() {
        List<Integer> expectedPrimes = List.of(2, 3, 5);
        when(mockPrimesGenerator.getPrimeNumbers(anyInt(), anyInt())).thenReturn(expectedPrimes);

        List<Integer> actualPrimes = primesGeneratorService.fetchOrComputePrimes(2, 6, Algorithm.SIEVE);
        assertEquals(expectedPrimes, actualPrimes);
    }
}
