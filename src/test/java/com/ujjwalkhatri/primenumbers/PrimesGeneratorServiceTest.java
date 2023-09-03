package com.ujjwalkhatri.primenumbers;

import com.ujjwalkhatri.primenumbers.generators.PrimesGenerator;
import com.ujjwalkhatri.primenumbers.generators.PrimesGeneratorDefault;
import com.ujjwalkhatri.primenumbers.generators.PrimesGeneratorSieve;
import com.ujjwalkhatri.primenumbers.service.PrimesGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class PrimesGeneratorServiceTest {

    private PrimesGeneratorService primesGeneratorService;

    private final PrimesGenerator primeNumbersSieve = Mockito.spy(PrimesGeneratorSieve.class);
    private final PrimesGenerator primeNumbersDefault = Mockito.spy(PrimesGeneratorDefault.class);

    @BeforeEach
    public void setup() {
        when(primeNumbersSieve.getPrimeNumbers(anyInt(), anyInt())).thenReturn(Arrays.asList(2, 3, 5));
        when(primeNumbersDefault.getPrimeNumbers(anyInt(), anyInt())).thenReturn(Arrays.asList(2, 3, 5, 7));
        primesGeneratorService = new PrimesGeneratorService(Arrays.asList(primeNumbersSieve, primeNumbersDefault));
    }
}
