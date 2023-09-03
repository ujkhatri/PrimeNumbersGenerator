package com.ujjwalkhatri.primenumbers.generators;

import java.util.List;

public interface PrimesGenerator {
    /**
     * Gets prime numbers.
     *
     * @param inputNumber the input number
     * @return the prime numbers
     */
    List<Integer> getPrimeNumbers(int lowerNumber, int inputNumber);
}
