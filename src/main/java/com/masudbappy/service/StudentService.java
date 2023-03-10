package com.masudbappy.service;

import com.masudbappy.entity.Student;
import com.masudbappy.feignclients.AddressFeignClient;
import com.masudbappy.repository.StudentRepository;
import com.masudbappy.request.CreateStudentRequest;
import com.masudbappy.response.AddressResponse;
import com.masudbappy.response.StudentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    WebClient webClient;

    @Autowired
    AddressFeignClient addressFeignClient;

    @Autowired
    CommonService commonService;

    public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {

        Student student = new Student();
        student.setFirstName(createStudentRequest.getFirstName());
        student.setLastName(createStudentRequest.getLastName());
        student.setEmail(createStudentRequest.getEmail());

        student.setAddressId(createStudentRequest.getAddressId());
        student = studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse(student);

//        studentResponse.setAddressResponse(getAddressById(student.getAddressId()));
//        studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));
        studentResponse.setAddressResponse(commonService.getAddressById(student.getAddressId()));

        return studentResponse;
    }

    public StudentResponse getById (long id) {
        logger.info("Inside Student getById");
        Student student = studentRepository.findById(id).get();
        StudentResponse studentResponse = new StudentResponse(student);

//        studentResponse.setAddressResponse(getAddressById(student.getAddressId()));
//        studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()));
        studentResponse.setAddressResponse(commonService.getAddressById(student.getAddressId()));

        return studentResponse;
    }

    /*public AddressResponse getAddressById (long addressId) {
        Mono<AddressResponse> addressResponse =
                webClient.get().uri("/getById/" + addressId)
                        .retrieve().bodyToMono(AddressResponse.class);

        return addressResponse.block();
    }*/

    // This method is modified for circuit breaker and here we will use feign client.
   /*@CircuitBreaker(name = "addressService", fallbackMethod = "fallBackGetAddressById")
    public AddressResponse getAddressById (long addressId) {
        AddressResponse addressResponse = addressFeignClient.getById(addressId);
        return addressResponse;
    }

    public AddressResponse fallBackGetAddressById (long addressId, Throwable throwable) {

        return new AddressResponse();
    }*/
}

