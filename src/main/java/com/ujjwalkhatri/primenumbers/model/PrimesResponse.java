package com.ujjwalkhatri.primenumbers.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.List;

/**
 * The type Prime numbers response.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrimesResponse implements Serializable {

    private Integer initial;

    @JacksonXmlElementWrapper(localName = "Primes")
    @JacksonXmlProperty(localName = "Prime")
    private List<Integer> primes;

    public PrimesResponse(Integer inputNumber, List<Integer> primesList) {
        this.initial = inputNumber;
        this.primes = primesList;
    }

    @JsonProperty("Initial")
    public Integer getInitial() {
        return initial;
    }

    @JsonProperty("Primes")
    @JacksonXmlElementWrapper(localName = "Primes")
    @JacksonXmlProperty(localName = "Prime")
    public List<Integer> getPrimes() {
        return primes;
    }
}



