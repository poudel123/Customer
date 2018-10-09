package com.capgemini.Customerapp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.Customerapp.entity.Customer;
import com.capgemini.Customerapp.exception.AuthenticationFailedException;
import com.capgemini.Customerapp.exception.CustomerNotFoundException;
import com.capgemini.Customerapp.repository.CustomerRepository;
import com.capgemini.Customerapp.serviceImpl.CustomerServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {
	@InjectMocks
	CustomerServiceImpl customerService;

	
	@Mock
	private CustomerRepository customerRepository;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerService).build();
	}
	@Test
	public void testAddProductWhichReturnsProduct() {
		Customer customer=new Customer(1234,"priya", "12", "gp@gmail", "hyd");
		when(customerRepository.save(customer)).thenReturn(customer);
		assertEquals(customer, customerService.addCustomer(customer));
	}
	
	@Test
	public void testUpdate() {
		Customer customer=new Customer(1234,"priya", "123", "gp@gmail", "hyd");
		when(customerRepository.save(customer)).thenReturn(customer);
		assertEquals(customer,customerService.updateCustomer(customer));
	}
	
	@Test
	public void testFindProductById() throws CustomerNotFoundException {
		Customer customer=new Customer(1234,"priya", "123", "gp@gmail", "hyd");
		when(customerRepository.findById(1234)).thenReturn(Optional.of(customer));
		assertEquals(customer,customerService.findCustomerById(1234));
		
	}
	@Test
	public void TestAuthenticateCustomer() throws CustomerNotFoundException, AuthenticationFailedException {
		Customer customer=new Customer(1234,"priya", "123", "gp@gmail", "hyd");
		customerService.authenticate(customer);
		when(customerRepository.save(Mockito.isA(Customer.class))).thenReturn(customer);
		assertEquals(customer, customerService.authenticate(customer));
	}
	
	@Test
	public void testDeleteProduct() {
		Customer customer=new Customer(1234,"priya", "123", "gp@gmail", "hyd");
		customerService.deleteCustomer(customer);
		verify(customerRepository,times(1)).delete(customer);
	}
}
