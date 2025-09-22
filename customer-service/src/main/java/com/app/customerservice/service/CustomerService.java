package com.app.customerservice.service;

import com.app.customerservice.entities.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> customerList();

    Customer customerById(Long id);

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);
}
