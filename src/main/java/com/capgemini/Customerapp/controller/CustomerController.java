package com.capgemini.Customerapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.Customerapp.entity.Customer;
import com.capgemini.Customerapp.exception.AuthenticationFailedException;
import com.capgemini.Customerapp.exception.CustomerNotFoundException;
import com.capgemini.Customerapp.service.CustomerService;




@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		System.out.println("Hello");
		ResponseEntity<Customer> responseEntity = new ResponseEntity<Customer>(customerService.addCustomer(customer),
				HttpStatus.OK);
		return responseEntity;
}
	@PutMapping("/customer")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
		try {
			Customer customerFormDb = customerService.findCustomerById(customer.getCustomerId());
			if (customerFormDb != null)
				return new ResponseEntity<Customer>(customerService.updateCustomer(customer), HttpStatus.OK);

		} catch (CustomerNotFoundException exception) {
			
		}
		return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
	}

	
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<Customer> findCustomerById(@PathVariable int customerId) {
		try {
			Customer customerFormDb = customerService.findCustomerById(customerId);
			return new ResponseEntity<Customer>(customerService.findCustomerById(customerId), HttpStatus.OK);

		} catch (CustomerNotFoundException exception) {
		}
		return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/customers/{customerId}")
	public ResponseEntity<Customer> deleteCustomer(Customer customer){
		try {
			Customer customerFromDb = customerService.findCustomerById(customer.getCustomerId());
			if(customerFromDb != null) {
				customerService.deleteCustomer(customerFromDb);
				return new ResponseEntity<Customer>(HttpStatus.OK);
			}
			}catch(CustomerNotFoundException exception) {
				//logged the exception
			}		
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		
		}
	@PostMapping("/login")
	public ResponseEntity<Customer> authenticateCustomer(@RequestBody Customer customer) throws AuthenticationFailedException{
		
		Customer customer1 = customerService.authenticate(customer);
		if (customer1 != null)
			return new ResponseEntity<Customer>(customerService.authenticate(customer), HttpStatus.OK);

		return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
	
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List> listAllCustomers(){
		List<Customer> list = customerService.getAllCustomers();
		return new ResponseEntity<List>(list, HttpStatus.OK);
	}

		
}