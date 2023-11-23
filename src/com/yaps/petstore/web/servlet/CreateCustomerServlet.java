package com.yaps.petstore.web.servlet;

import com.yaps.petstore.common.delegate.CustomerDelegate;
import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.DuplicateKeyException;
import com.yaps.petstore.common.logging.Trace;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This servlet creates a customer.
 */
public final class CreateCustomerServlet extends HttpServlet {

    // ======================================
    // =             Attributes             =
    // ======================================

    // Used for logging
    private final transient String _cname = this.getClass().getName();

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(_cname, mname);

        final CustomerDTO customerDTO = new CustomerDTO();

        // Personal information
        customerDTO.setId(request.getParameter("id"));
        customerDTO.setFirstname(request.getParameter("firstname"));
        customerDTO.setLastname(request.getParameter("lastname"));
        customerDTO.setEmail(request.getParameter("email"));
        customerDTO.setTelephone(request.getParameter("telephone"));
        // Personal information
        customerDTO.setStreet1(request.getParameter("street1"));
        customerDTO.setStreet2(request.getParameter("street2"));
        customerDTO.setCity(request.getParameter("city"));
        customerDTO.setState(request.getParameter("state"));
        customerDTO.setZipcode(request.getParameter("zipcode"));
        customerDTO.setCountry(request.getParameter("country"));
        // Credit Card Information
        customerDTO.setCreditCardType(request.getParameter("creditCardType"));
        customerDTO.setCreditCardNumber(request.getParameter("creditCardNumber"));
        customerDTO.setCreditCardExpiryDate(request.getParameter("creditCardExpiryDate"));

        try {
            // Creates the customer
            CustomerDelegate.createCustomer(customerDTO);
            response.setContentType("text/html");
            final PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>YAPS Customer Created</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1><center>Customer Created !!</center></h1>");
            out.println("</body>");
            out.println("</html>");
            out.close();

        } catch (DuplicateKeyException e) {
            getServletContext().getRequestDispatcher("/error?exception=Customer Id already exists").forward(request, response);
        } catch (CheckException e) {
            getServletContext().getRequestDispatcher("/error?exception=" + e.getMessage()).forward(request, response);
        } catch (Exception e) {
            Trace.throwing(_cname, mname, e);
            getServletContext().getRequestDispatcher("/error?exception=Cannot create the customer").forward(request, response);
        }
    }
}