package com.app.accountservice.service;

import com.app.accountservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="customer-service", url = "http://localhost:8081")
public interface CustomerServiceClient {
    @GetMapping("/api/customers/{customerId}")
    Customer getCustomerById(Long customerId);
}
