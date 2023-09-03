package com.ujjwalkhatri.primenumbers.generators;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
@Qualifier("default")
public class PrimesGeneratorDefault implements PrimesGenerator {

    @Override
    public List<Integer> getPrimeNumbers(int lowerNumber, int inputNumber) {
        return IntStream.rangeClosed(lowerNumber, inputNumber)
                .parallel()
                .filter(this::isPrime)
                .boxed()
                .toList();
    }
    private boolean isPrime(final int number) {
        if (number % 2 == 0 || number < 2) {
            return number == 2;
        }
        int limit = (int) (Math.sqrt(number));
        for (int i = 3; i <= limit; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
