package com.ujjwalkhatri.primenumbers.controller;

import com.ujjwalkhatri.primenumbers.enums.Algorithm;
import com.ujjwalkhatri.primenumbers.exceptions.InvalidInputException;
import com.ujjwalkhatri.primenumbers.model.PrimesResponse;
import com.ujjwalkhatri.primenumbers.service.PrimesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class PrimesController {
    @Autowired
    private PrimesService primesService;

    @Autowired
    private CacheManager cacheManager;

    @GetMapping(value = "/primes/{inputNumber}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody ResponseEntity<PrimesResponse> getPrimeNumbers(
            @PathVariable int inputNumber,
            @RequestParam(name = Algorithm.PARAM_NAME, required = false, defaultValue = Algorithm.DEFAULT_VALUE) Algorithm algorithm,
            @RequestHeader(name = "Accept", required = false, defaultValue = MediaType.APPLICATION_JSON_VALUE) MediaType header) {
        log.info("Generating prime numbers for {} and algorithm: {}", inputNumber, algorithm.toLowerCase());
        if (inputNumber < 0) {
            log.warn("Invalid input number: {}", inputNumber);
            throw new InvalidInputException("Input number cannot be negative");
        }
        PrimesResponse primesResponse = primesService.getPrimeNumbers(inputNumber, algorithm);
        return ResponseEntity.ok().contentType(header).body(primesResponse);
    }
}
