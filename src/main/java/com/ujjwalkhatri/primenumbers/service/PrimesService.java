package com.ujjwalkhatri.primenumbers.service;

import com.ujjwalkhatri.primenumbers.enums.Algorithm;
import com.ujjwalkhatri.primenumbers.model.PrimesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PrimesService {

    public static final int SEGMENT_SIZE = 1_00_000;

    @Autowired
    private PrimesGeneratorService primesGeneratorService;

    public PrimesResponse getPrimeNumbers(int inputNumber, Algorithm algorithm) {
        List<Integer> allPrimes = new ArrayList<>();
        long startMillis = System.currentTimeMillis();

        int intervals = (int) Math.ceil((double)inputNumber / SEGMENT_SIZE);
        for(int i = 0; i < intervals; i++) {
            int lowerBound = i * SEGMENT_SIZE;
            if(lowerBound < 2) lowerBound = 2;
            if (i != 0) {
                lowerBound += 1;
            }
            int upperBound = (i + 1) * SEGMENT_SIZE;
            upperBound = Math.min(upperBound, inputNumber);

            List<Integer> primeList = primesGeneratorService.fetchOrComputePrimes(lowerBound, upperBound, algorithm);
            allPrimes.addAll(primeList);
        }

        allPrimes = allPrimes.stream().filter(p -> p <= inputNumber).collect(Collectors.toList());
        log.info("Size of prime numbers list: {} and time taken: {}", allPrimes.size(), (System.currentTimeMillis() - startMillis));
        return new PrimesResponse(inputNumber, allPrimes);
    }
}
