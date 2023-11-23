/*
 * Created on 10 déc. 2005
 * Veronique
 */
package com.yaps.petstore.server.service.customer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.CreateException;
import com.yaps.petstore.common.exception.FinderException;
import com.yaps.petstore.common.exception.RemoveException;
import com.yaps.petstore.common.exception.UpdateException;
import com.yaps.petstore.common.logging.Trace;
import com.yaps.petstore.server.domain.CreditCard;
import com.yaps.petstore.server.domain.customer.Customer;
import com.yaps.petstore.server.service.AbstractRemoteService;
import com.yaps.petstore.server.service.creditcard.CreditCardService;


/**
 * @author Veronique
 * Veronique
 */
public final class CustomerService extends AbstractRemoteService implements CustomerServiceRemote {

	final private CreditCardService creditCardService = new CreditCardService();
	/**************
	 * Constructor
	 **************/
	public CustomerService() throws RemoteException {
		
	}
	//////////Private method//////////
	/***********************
	 * transformCustomer2DTO
	 * @param customer
	 * @return customerDTO
	 ************************/
	private CustomerDTO transformCustomer2DTO(final Customer customer) {
		final CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCity(customer.getCity());
		customerDTO.setCountry(customer.getCountry());
		customerDTO.setCreditCardExpiryDate(customer.getCreditCardExpiryDate());
		customerDTO.setCreditCardNumber(customer.getCreditCardNumber());
		customerDTO.setCreditCardType(customer.getCreditCardType());
		customerDTO.setEmail(customer.getEmail());
		customerDTO.setFirstname(customer.getFirstname());
		customerDTO.setId(customer.getId());
		customerDTO.setLastname(customer.getLastname());
		customerDTO.setState(customer.getState());
		customerDTO.setStreet1(customer.getStreet1());
		customerDTO.setStreet2(customer.getStreet2());
		customerDTO.setTelephone(customer.getTelephone());
		customerDTO.setZipcode(customer.getZipcode());
		return customerDTO;
	}
	/*************************
	 * transformCustomers2DTO
	 * @param customers
	 * @return Collection
	 *************************/
	private Collection transformCustomers2DTOs(final Collection customers) {
	 final Collection customersDTO = new ArrayList();
	 for (Iterator iterator = customers.iterator(); iterator.hasNext();) {
	 	final Customer customer =(Customer)iterator.next();
	 	customersDTO.add(transformCustomer2DTO(customer));
	 }
	 return customersDTO;
	}
	/*********************** 
	 * CreateCustomer
	 * @param customerDTO
	 * @return CustomerDTO
	 ************************/
	public CustomerDTO createCustomer(final CustomerDTO customerDTO) throws CreateException, CheckException {
		final String mname ="createCustomer";
		Trace.entering(getCname(), mname, customerDTO);
		
		if(customerDTO == null)
			throw new CreateException("Customer object is null");
		
		//Check validity if an error occurs a checkException is thrown
		final CreditCard creditCard = new CreditCard();
		String creditCardExpiryDate = customerDTO.getCreditCardExpiryDate();
		String creditCardNumber = customerDTO.getCreditCardNumber();
		String creditCardType = customerDTO.getCreditCardType();
		if(!"".equals(creditCardExpiryDate) && !(creditCardExpiryDate==null) &&
		   !"".equals(creditCardNumber) && !(creditCardNumber == null) &&
		   !"".equals(creditCardType) && !(creditCardType == null)){
			creditCard.setCreditCardExpiryDate(creditCardExpiryDate);
			creditCard.setCreditCardNumber(creditCardNumber);
			creditCard.setCreditCardType(creditCardType);
		creditCardService.verifyCreditCard(creditCard);
		}
		
		// Transforms DTO into domain object
		final Customer customer = new Customer(customerDTO.getId(), customerDTO.getFirstname(),customerDTO.getLastname());
		customer.setCity(customerDTO.getCity());
		customer.setCountry(customerDTO.getCountry());
		customer.setCreditCardNumber(customerDTO.getCreditCardNumber());
		customer.setCreditCardExpiryDate(customerDTO.getCreditCardExpiryDate());
		customer.setCreditCardType(customerDTO.getCreditCardType());
		customer.setEmail(customerDTO.getEmail());
		customer.setState(customerDTO.getState());
		customer.setStreet1(customerDTO.getStreet1());
		customer.setStreet2(customerDTO.getStreet2());
		customer.setTelephone(customerDTO.getTelephone());
		customer.setZipcode(customerDTO.getZipcode());
		// Creates the object
		customer.create();
		
		// Transforms domain object into DTO
		final CustomerDTO result = transformCustomer2DTO(customer);
		
		Trace.exiting(getCname(), mname, result);
		return result;
	}

	

	/*********************** 
	 * findCustomer
	 * @param customerId
	 * @return CustomerDTO
	 ***********************/
	public CustomerDTO findCustomer(final String customerId) throws FinderException, CheckException {
		final String mname ="findCustomer";
		Trace.entering(getCname(), mname, customerId);
		
		final Customer customer = new Customer();
		
		// Finds the object
		customer.findByPrimaryKey(customerId);
		
		// Transforms domain object into DTO
		final CustomerDTO customerDTO = transformCustomer2DTO(customer);
		
		Trace.exiting(getCname(), mname, customerDTO);
		return customerDTO;
	}

	/********************** 
	 * deleteCustomer
	 * @param  customerId
	 ***********************/
	public void deleteCustomer(final String customerId) throws RemoveException, CheckException {
		final String mname ="deleteCustomer";
		Trace.entering(getCname(), mname, customerId);
		
		final Customer customer = new Customer();
		
		// Checks if the object exists
		try {
			customer.findByPrimaryKey(customerId);
		} catch(FinderException e) {
			throw new RemoveException("Customer must exist to be deleted");
		}
		
		// Deletes the object
		customer.remove();
		
	}

	/********************** 
	 * updateCustomer
	 * @param customerDTO
	 **********************/
	public void updateCustomer(final CustomerDTO customerDTO) throws UpdateException, CheckException {
		final String mname ="updateCustomer";
		Trace.entering(getCname(), mname, customerDTO);
		
		if(customerDTO == null)
			throw new UpdateException("Customer object is null");
		
//		Check validity if an error occurs a checkException is thrown
		final CreditCard creditCard = new CreditCard();
		String creditCardExpiryDate = customerDTO.getCreditCardExpiryDate();
		String creditCardNumber = customerDTO.getCreditCardNumber();
		String creditCardType = customerDTO.getCreditCardType();
		if(!"".equals(creditCardExpiryDate) && !(creditCardExpiryDate==null) &&
		   !"".equals(creditCardNumber) && !(creditCardNumber == null) &&
		   !"".equals(creditCardType) && !(creditCardType == null)){
			creditCard.setCreditCardExpiryDate(creditCardExpiryDate);
			creditCard.setCreditCardNumber(creditCardNumber);
			creditCard.setCreditCardType(creditCardType);
		creditCardService.verifyCreditCard(creditCard);
		}
		
		Customer customer = new Customer();
		
		// Checks if the object exists
		try {
			customer.findByPrimaryKey(customerDTO.getId());
		} catch (FinderException e) {
			throw new UpdateException("Customer must exist to be updated");
		}
		
		// Transforms DTO into domain object		
		customer.setCity(customerDTO.getCity());
		customer.setCountry(customerDTO.getCountry());
		customer.setCreditCardExpiryDate(customerDTO.getCreditCardExpiryDate());
		customer.setCreditCardNumber(customerDTO.getCreditCardNumber());
		customer.setCreditCardType(customerDTO.getCreditCardType());
		customer.setEmail(customerDTO.getEmail());
		customer.setFirstname(customerDTO.getFirstname());
		customer.setId(customerDTO.getId());
		customer.setLastname(customerDTO.getLastname());
		customer.setState(customerDTO.getState());
		customer.setStreet1(customerDTO.getStreet1());
		customer.setStreet2(customerDTO.getStreet2());
		customer.setTelephone(customerDTO.getTelephone());
		customer.setZipcode(customerDTO.getZipcode());
		
		// Updates the object
		customer.update();
		
	}

	/********************** 
	 * findCustomers
	 * @return Collection
	 **********************/
	public Collection findCustomers() throws FinderException {
		final String mname ="findCustomers";
		Trace.entering(getCname(), mname);
		
		// Finds all the objects
		final Collection customers = new Customer().findAll();
		
		// Transforms domain objects into DTOs
		final Collection customersDTO = transformCustomers2DTOs(customers);
		return customersDTO;
	}

}
