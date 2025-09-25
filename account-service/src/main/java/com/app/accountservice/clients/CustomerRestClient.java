package com.app.accountservice.clients;

import com.app.accountservice.model.Customer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name="CUSTOMER-SERVICE")
public interface CustomerRestClient {

    @GetMapping("/api/customers/{customerId}")
    @CircuitBreaker(name= "customerService", fallbackMethod = "getDefaultCustomer()")
    Customer getCustomerById(@PathVariable Long customerId);

    @GetMapping("/api/customers")
    @CircuitBreaker(name= "customerService", fallbackMethod = "getAllCustomers")
    List<Customer> allCustomers();

    default Customer getDefaultCustomer(Long id, Exception exception) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName("Not Available");
        customer.setLastName("Not Available");
        customer.setEmail("Not Available");
        return customer;
    }

    default List<Customer> getAllCustomers(Exception exception) {
        return List.of();
    }
}
