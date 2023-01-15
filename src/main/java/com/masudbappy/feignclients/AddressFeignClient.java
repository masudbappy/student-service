package com.masudbappy.feignclients;

import com.masudbappy.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(url = "${address.service.url}", value = "address-feign-client")
@FeignClient( value = "address-service", path = "/api/address")
public interface AddressFeignClient {

    @GetMapping("/getById/{id}")
    public AddressResponse getById(@PathVariable("id") long id);

}
