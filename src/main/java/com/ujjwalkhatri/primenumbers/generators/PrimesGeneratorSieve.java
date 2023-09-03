package com.ujjwalkhatri.primenumbers.generators;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Qualifier("sieve")
public class PrimesGeneratorSieve implements PrimesGenerator {

    public List<Integer> getPrimeNumbers(int a, int b) {
        List<Integer> primesList = calculateSieve((int) Math.sqrt(b) + 1);
        boolean[] validPrimes = new boolean[b - a + 1];
        IntStream.range(0, b - a + 1).forEach(i -> validPrimes[i] = true);

        primesList.forEach(p -> {
            int start = Math.max(p * p, (a + p - 1) / p * p);
            if (start <= b) {
                for (int j = start; j <= b; j += p) {
                    validPrimes[j - a] = false;
                }
            }
        });

        return IntStream.range(0, validPrimes.length).filter(i -> validPrimes[i]).mapToObj(i -> i + a).collect(Collectors.toList());
    }

    public static List<Integer> calculateSieve(int n) {
        boolean[] sieve = new boolean[n + 1];
        IntStream.range(0, n + 1).forEach(i -> sieve[i] = true);
        sieve[0] = sieve[1] = false;

        IntStream.rangeClosed(2, (int) Math.sqrt(n))
                .filter(i -> sieve[i])
                .forEach(i -> {
                    for (int j = i * i; j <= n; j += i) {
                        sieve[j] = false;
                    }
                });

        return IntStream.rangeClosed(2, n).filter(i -> sieve[i]).boxed().collect(Collectors.toList());
    }
}
