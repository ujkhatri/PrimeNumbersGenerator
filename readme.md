# Prime Numbers Generator

The Prime Numbers Generator is a Spring Boot project designed to generate prime numbers up to a specified input number.
## Overview

Given an input number, the service returns a list of prime numbers up to and including that number.

`curl --location 'http://localhost:8080/api/v1/primes/130000?algorithm=sieve' --header 'Accept: application/json'`

### Sample Input: `10`

### Sample Output (JSON):
```json
{
  "Initial": 10,
  "Primes": [2,3,5,7]
}
```

### Sample Output (XML):

```XML
<PrimesResponse>
    <Initial>10</Initial>
    <Primes>
        <Prime>2</Prime>
        <Prime>3</Prime>
        <Prime>5</Prime>
        <Prime>7</Prime>
    </Primes>
</PrimesResponse>
```

## API Details

**Base URL:** http://localhost:8080/api/v1

**Endpoint:** /primes/{inputNumber}?algorithm={algorithmType}

**Examples:**

* http://localhost:8080/api/v1/primes/10
 
* http://localhost:8080/api/v1/primes/10?algorithm=default

* http://localhost:8080/api/v1/primes/10?algorithm=sieve

### Parameters:
#### inputNumber: 
The number up to which the prime numbers will be calculated.
#### algorithm (optional): 
The algorithm to use for prime number calculation. If not specified, the default algorithm is used.

### Headers (Required):

Based on the desired output, the request should have one of the following header:

* application/json
* application/xml

### Supported Algorithms:
Currently, the service supports the following algorithms for prime number generation:

* Default
* Sieve
 
### Response Format:
The API can return results in either JSON or XML format. The response format is determined by the 'Accept' header in the request.

## Caching

Caching can be toggled on or off through the application.properties file:

`cache.primes.enabled=true`

By default, caching is enabled.

## Development

### Pre-Push Validation

Before committing and pushing code changes, developers should execute the pre-push validation script to ensure there are no breaking changes:

`./pre-push.sh`

if you get 'permission denied' error, change the permission with the following:

`chmod +x pre-push.sh`

