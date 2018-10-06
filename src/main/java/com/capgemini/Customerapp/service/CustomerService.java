package com.capgemini.Customerapp.service;

import java.util.List;

import com.capgemini.Customerapp.entity.Customer;
import com.capgemini.Customerapp.exception.AuthenticationFailedException;
import com.capgemini.Customerapp.exception.CustomerNotFoundException;

public interface CustomerService {
	public Customer addCustomer(Customer customer);
	
	public Customer authenticate(Customer customer) throws AuthenticationFailedException ;

	public Customer updateCustomer(Customer customer);

	public Customer findCustomerById(int customerId) throws CustomerNotFoundException;

	public void deleteCustomer(Customer customer);
	
	public List<Customer> getAllCustomers();
}
