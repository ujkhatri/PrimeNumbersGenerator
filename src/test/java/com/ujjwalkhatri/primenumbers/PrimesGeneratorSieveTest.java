package com.ujjwalkhatri.primenumbers;

import com.ujjwalkhatri.primenumbers.generators.PrimesGeneratorSieve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimesGeneratorSieveTest {

    @InjectMocks
    private PrimesGeneratorSieve primesGeneratorSieve;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getPrimeNumbers() {
        List<Integer> primes = primesGeneratorSieve.getPrimeNumbers(2,30);
        assertEquals(List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29), primes);
    }

}

