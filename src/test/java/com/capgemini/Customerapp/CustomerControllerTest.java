package com.capgemini.Customerapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.Customerapp.controller.CustomerController;
import com.capgemini.Customerapp.entity.Customer;
import com.capgemini.Customerapp.service.CustomerService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTest {
	@Mock
	private CustomerService customerService;
	
	@InjectMocks
	private CustomerController customerController;
	
	
	
	
	private MockMvc mockMvc;
	
	@Before
	public void setUP()
	{
		MockitoAnnotations.initMocks(this);
	mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}
	
	@Test
	public void testAddCustomer() throws Exception {
		String context="{\r\n" + 
				"  \"customerId\": \"1234\",\r\n" + 
				"  \"customerName\": \"priya\",\r\n" + 
				"  \"customerPassword\": \"12\",\r\n" + 
				"  \"email\": \"gp@gmail\",\r\n" + 
				"  \"address\": \"hyd\"\r\n" + 
				"}";
		when(customerService.addCustomer(Mockito.isA(Customer.class))).thenReturn(new Customer(1234,"priya", "12", "gp@gmail", "hyd") );
		mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(context)
				.accept( MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andDo(print());
		
	}
	
	@Test
	public void testUpdateCustomer() throws Exception{
		String content = "{\r\n" + 
				"  \"customerId\": \"1234\",\r\n" + 
				"  \"customerName\": \"priya\",\r\n" + 
				"  \"customerPassword\": \"123\",\r\n" + 
				"  \"email\": \"gp@gmail\",\r\n" + 
				"  \"address\": \"hyd\"\r\n" + 
				"}";
		when(customerService.findCustomerById(Mockito.isA(Integer.class))).thenReturn(new Customer(1234,"priya", "123", "gp@gmail", "hyd"));
		when(customerService.updateCustomer(Mockito.isA(Customer.class))).thenReturn(new Customer(1234,"priya", "123", "gp@gmail", "hyd"));
		mockMvc.perform(put("/customer").contentType(MediaType.APPLICATION_JSON_UTF8).content(content).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		 .andExpect(status().isOk());

}
	@Test
	public void testFindProductbyId()throws Exception
	{
		when(customerService.findCustomerById(123)).thenReturn(new Customer(1234,"priya", "123", "gp@gmail", "hyd"));
		mockMvc.perform(get("/customers/123"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.customerId").exists())
        .andExpect(jsonPath("$.customerName").exists())
        .andExpect(jsonPath("$.customerId").value(1234))
        .andExpect(jsonPath("$.customerName").value("priya"))
        .andDo(print());		              
	}
}