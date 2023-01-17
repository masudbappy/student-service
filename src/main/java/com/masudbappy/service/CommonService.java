package com.masudbappy.service;

import com.masudbappy.feignclients.AddressFeignClient;
import com.masudbappy.response.AddressResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    Logger logger = LoggerFactory.getLogger(CommonService.class);
    long count = 1;

    @Autowired
    AddressFeignClient addressFeignClient;
    // This method is modified for circuit breaker and here we will use feign client.
    @CircuitBreaker(name = "addressService", fallbackMethod = "fallBackGetAddressById")
    public AddressResponse getAddressById (long addressId) {
        logger.info("Count = " + count);
        count++;
        AddressResponse addressResponse = addressFeignClient.getById(addressId);
        return addressResponse;
    }

    public AddressResponse fallBackGetAddressById (long addressId, Throwable throwable) {
        logger.error("Error = " + throwable);
        return new AddressResponse();
    }
}
