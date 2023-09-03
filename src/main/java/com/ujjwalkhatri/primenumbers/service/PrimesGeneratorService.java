package com.ujjwalkhatri.primenumbers.service;

import com.ujjwalkhatri.primenumbers.enums.Algorithm;
import com.ujjwalkhatri.primenumbers.generators.PrimesGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

@Service
@Slf4j
public class PrimesGeneratorService {

    private final Map<String, PrimesGenerator> primeNumbersGenerators;

    @Value("${cache.primes.enabled}")
    private boolean isCachePrimesEnabled;

    public boolean isCachePrimesEnabled() {
        return isCachePrimesEnabled;
    }
    private int segmentSize = PrimesService.SEGMENT_SIZE;

    public int getSegmentSize() {
        return segmentSize;
    }

    public PrimesGeneratorService(List<PrimesGenerator> primesGeneratorList) {
        this.primeNumbersGenerators = primesGeneratorList.stream()
                .collect(toUnmodifiableMap(k -> k.getClass().getDeclaredAnnotation(Qualifier.class).value(), identity()));
    }

    @Cacheable(value = "primesCache", key = "#number", condition = "#number%#root.target.segmentSize==0 && #root.target.isCachePrimesEnabled")
    public List<Integer> fetchOrComputePrimes(int lower, int number, Algorithm algorithm) {
        PrimesGenerator primesGenerator = getPrimeNumbersGenerator(algorithm);
        List<Integer> primeList = primesGenerator.getPrimeNumbers(lower, number);
        return primeList;
    }

    private PrimesGenerator getPrimeNumbersGenerator(Algorithm algorithm) {
        return primeNumbersGenerators.get(algorithm.toLowerCase());
    }
}
