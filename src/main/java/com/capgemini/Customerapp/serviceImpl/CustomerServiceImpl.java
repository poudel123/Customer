package com.capgemini.Customerapp.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.Customerapp.entity.Customer;
import com.capgemini.Customerapp.exception.AuthenticationFailedException;
import com.capgemini.Customerapp.exception.CustomerNotFoundException;
import com.capgemini.Customerapp.repository.CustomerRepository;
import com.capgemini.Customerapp.service.CustomerService;



@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Customer addCustomer(Customer customer) {
	
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
	
		return customerRepository.save(customer);
	}

	@Override
	public Customer findCustomerById(int customerId) {
		Optional<Customer> optionalCustomer= customerRepository.findById(customerId);
		if(optionalCustomer.isPresent())
			return optionalCustomer.get();
		throw new CustomerNotFoundException("Product does not exists");
	}


	@Override
	public Customer authenticate(Customer customer) throws AuthenticationFailedException, CustomerNotFoundException {
		Optional<Customer> customer1 = customerRepository.findById((int) customer.getCustomerId());
		if (!customer1.isPresent())
			throw new CustomerNotFoundException("Customer Not found");
		if (customer1.isPresent()) {
			if (customer1.get().getCustomerPassword().equals(customer.getCustomerPassword()))
				return customer1.get();
		}
		throw new AuthenticationFailedException("Login Failed Try again");
	
	}

	@Override
	public void deleteCustomer(Customer customer) throws CustomerNotFoundException {
		customerRepository.delete(customer);
		
	}
	@Override
	public List<Customer> getAllCustomers() {
		
		return null;
	}

}
