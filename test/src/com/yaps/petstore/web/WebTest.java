package com.yaps.petstore.web;

import com.meterware.httpunit.WebConversation;
import com.yaps.petstore.AbstractTestCase;
import junit.framework.TestSuite;

/**
 * This class tests the HTML Pages and servlets
 */
public class WebTest extends AbstractTestCase {

    private WebConversation webConversation = new WebConversation();
    private static final String URL_PETSTORE = "http://localhost:8080/petstore";
    private static final String URL_BARKBANK = "http://localhost:8080/barkbank";

    public WebTest(final String s) {
        super(s);
    }

    public static TestSuite suite() {
        return new TestSuite(WebTest.class);
    }

    //==================================
    //=            Test cases          =
    //==================================
    /**
     * Checks that all HTML and pages are deployed
     */
    public void testWebCheckPages() {
        try {
            webConversation.getResponse(URL_PETSTORE + "/dummy.html");
            fail("A dummy HTML page has been found on petstore");
        } catch (Exception e) {
        }

        try {
            webConversation.getResponse(URL_PETSTORE);
        } catch (Exception e) {
            fail("Root " + URL_PETSTORE + " hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/createcustomer.html");
        } catch (Exception e) {
            fail("createcustomer.html hasn't been found");
        }

        try {
            webConversation.getResponse(URL_BARKBANK + "/dummy.html");
            fail("A dummy HTML page has been found on barkbank");
        } catch (Exception e) {
        }

        try {
            webConversation.getResponse(URL_BARKBANK);
        } catch (Exception e) {
            fail("Root " + URL_BARKBANK + " hasn't been found");
        }
    }

    /**
     * Checks that all servlets are deployed
     */
    public void testWebCheckServlets() {
        try {
            webConversation.getResponse(URL_PETSTORE + "/dummy");
            fail("A dummy servlet has been found on petstore");
        } catch (Exception e) {
        }

        try {
            webConversation.getResponse(URL_BARKBANK + "/dummy");
            fail("A dummy servlet has been found on barkbank");
        } catch (Exception e) {
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/error");
        } catch (Exception e) {
            fail("The ErrorServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_PETSTORE + "/createcustomer");
        } catch (Exception e) {
            fail("The CreateCustomerServlet hasn't been found");
        }

        try {
            webConversation.getResponse(URL_BARKBANK + "/creditcard");
        } catch (Exception e) {
            fail("The CreditCardServlet hasn't been found");
        }
    }
}
