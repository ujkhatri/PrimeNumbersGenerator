package com.ujjwalkhatri.primenumbers;

import com.ujjwalkhatri.primenumbers.model.PrimesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimesResponseTest {

    private PrimesResponse response;
    private final Integer inputNumber = 10;
    private final List<Integer> primesList = Arrays.asList(2, 3, 5, 7);

    @BeforeEach
    public void setUp() {
        response = new PrimesResponse(inputNumber, primesList);
    }

    @Test
    public void constructorAndDataInitialization() {
        assertEquals(inputNumber, response.getInitial());
        assertEquals(primesList, response.getPrimes());
    }
}

