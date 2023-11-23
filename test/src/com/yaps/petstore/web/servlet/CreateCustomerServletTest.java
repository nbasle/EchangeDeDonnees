package com.yaps.petstore.web.servlet;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.yaps.petstore.AbstractTestCase;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.FinderException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.server.domain.customer.Customer;
import junit.framework.TestSuite;

/**
 * This class tests the CreateCustomer servlet
 */
public class CreateCustomerServletTest extends AbstractTestCase {

    private WebConversation webConversation = new WebConversation();
    private static final String URL_PETSTORE = "http://localhost:8080/petstore";

    public CreateCustomerServletTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(CreateCustomerServletTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * This method ensures that creating an object works. It first finds the object,
     * makes sure it doesn't exist, creates it using the web page and checks it then exists.
     */
    public void testServletCreateCustomer() throws Exception {
        final int id = getUniqueId();
        Customer customer = null;

        // Ensures that the object doesn't exist
        try {
            customer = findCustomer(id);
            fail("Object has not been created yet it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }

        // Creates an object
        createCustomer(id);

        // Ensures that the object exists
        try {
            customer = findCustomer(id);
        } catch (ObjectNotFoundException e) {
            fail("Object has been created it should be found");
        }

        // Checks that it's the right object
        checkCustomer(customer, id);

        // Creates an object with the same identifier. An exception has to be thrown
        try {
            createCustomer(id);
            fail("An object with the same id has already been created");
        } catch (Exception e) {
        }

        // Cleans the test environment
        customer.remove();

        try {
            findCustomer(id);
            fail("Object has been deleted it shouldn't be found");
        } catch (ObjectNotFoundException e) {
        }
    }

    /**
     * This test tries to create an object with a invalid values.
     */
    public void testServletCreateCustomerWithInvalidValues() throws Exception {

        // Creates an object with empty values
        try {
            createInvalidCustomer();
            fail("Object with empty values should not be created");
        } catch (Exception e) {
        }
    }

    //==================================
    //=         Private Methods        =
    //==================================
    private Customer findCustomer(final int id) throws FinderException, CheckException {
        final Customer customer = new Customer();
        customer.findByPrimaryKey("custo" + id);
        return customer;
    }

    private void createCustomer(final int id) throws Exception {
        // Gets the Web Page
        WebResponse customerPage = webConversation.getResponse(URL_PETSTORE + "/createcustomer.html");
        WebForm createCustomerForm = customerPage.getFormWithName("customerForm");
        // Sets parameter to the web page
        createCustomerForm.setParameter("id", "custo" + id);
        createCustomerForm.setParameter("firstname", "firstname" + id);
        createCustomerForm.setParameter("lastname", "lastname" + id);
        createCustomerForm.setParameter("email", "email" + id);
        createCustomerForm.setParameter("telephone", "phone" + id);
        createCustomerForm.setParameter("street1", "street1" + id);
        createCustomerForm.setParameter("street2", "street2" + id);
        createCustomerForm.setParameter("city", "city" + id);
        createCustomerForm.setParameter("state", "state" + id);
        createCustomerForm.setParameter("zipcode", "zip" + id);
        createCustomerForm.setParameter("country", "cnty" + id);
        createCustomerForm.setParameter("creditCardExpiryDate", "10/08");
        createCustomerForm.setParameter("creditCardNumber", "4564 1231 4564 1222");
        createCustomerForm.setParameter("creditCardType", "Visa");

        // Submits the form
        createCustomerForm.submit();
        // If after clicking the submit button an error occurs,
        // the page title is 'YAPS Error'
        WebResponse currentPage = webConversation.getCurrentPage();
        if ("YAPS Error".equals(currentPage.getTitle()))
            throw new Exception("An error has occured");
    }

    private void createInvalidCustomer() throws Exception {
        // Gets the Web Page
        WebResponse customerPage = webConversation.getResponse(URL_PETSTORE + "/createcustomer.html");
        WebForm createCustomerForm = customerPage.getFormWithName("customerForm");
        // Sets empty parameters to the web page
        createCustomerForm.setParameter("id", "");
        createCustomerForm.setParameter("firstname", "");
        createCustomerForm.setParameter("lastname", "");
        // Submits the form
        createCustomerForm.submit();
        // If after clicking the submit button an error occurs,
        // the page title is 'YAPS Error'
        WebResponse currentPage = webConversation.getCurrentPage();
        if ("YAPS Error".equals(currentPage.getTitle()))
            throw new Exception("An error has occured");
    }

    private void checkCustomer(final Customer customer, final int id) {
        assertEquals("firstname", "firstname" + id, customer.getFirstname());
        assertEquals("lastname", "lastname" + id, customer.getLastname());
        assertEquals("city", "city" + id, customer.getCity());
        assertEquals("country", "cnty" + id, customer.getCountry());
        assertEquals("state", "state" + id, customer.getState());
        assertEquals("street1", "street1" + id, customer.getStreet1());
        assertEquals("street2", "street2" + id, customer.getStreet2());
        assertEquals("telephone", "phone" + id, customer.getTelephone());
        assertEquals("email", "email" + id, customer.getEmail());
        assertEquals("zipcode", "zip" + id, customer.getZipcode());
        assertEquals("CreditCardExpiryDate", "10/08", customer.getCreditCardExpiryDate());
        assertEquals("CreditCardNumber", "4564 1231 4564 1222", customer.getCreditCardNumber());
        assertEquals("CreditCardType", "Visa", customer.getCreditCardType());
    }
}
