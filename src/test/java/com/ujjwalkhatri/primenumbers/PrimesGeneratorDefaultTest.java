package com.ujjwalkhatri.primenumbers;

import com.ujjwalkhatri.primenumbers.generators.PrimesGeneratorDefault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimesGeneratorDefaultTest {

    @InjectMocks
    private PrimesGeneratorDefault primesGeneratorDefault;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPrimeNumbers() {
        List<Integer> primes = primesGeneratorDefault.getPrimeNumbers(0,10);
        assertEquals(Arrays.asList(2, 3, 5, 7), primes);
    }

    @Test
    public void testGetPrimeNumbersForNegativeNumber() {
        List<Integer> primes = primesGeneratorDefault.getPrimeNumbers(0,-5);
        assertEquals(0, primes.size());
    }
}