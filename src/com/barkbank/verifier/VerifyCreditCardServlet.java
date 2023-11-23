/*
 * Created on 11 déc. 2005
 * Veronique
 */
package com.barkbank.verifier;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;


import java.net.URLDecoder;
import com.yaps.petstore.common.logging.Trace;

/**
 * @author Veronique
 *
 */
public class VerifyCreditCardServlet extends HttpServlet {
//	 ======================================
    // =             Attributes             =
    // ======================================

    // Used for logging
    private final transient String _cname = this.getClass().getName();
    Document creditCardToVerifyXML = null;

    // ======================================
    // =         Entry point method         =
    // ======================================
    protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String mname = "service";
        Trace.entering(_cname, mname);
        String VALID_CREDIT_CARD = "Valid";
        String INVALID_DATE = "Invalid date";
        String INVALID_NUMBER = "Invalid number";
        String INVALID_CREDIT_CARD = "Validation Exception";
        String statusToadd;
        
       
        String creditCardParameter = request.getParameter("param");
        if (creditCardParameter!= null) {
        String creditCardDecoded = URLDecoder.decode(creditCardParameter, "UTF-8");
//      Creates an XML document from the result of the servlet
        try {
        creditCardToVerifyXML = DocumentHelper.parseText(creditCardDecoded);
        Node nodeNumber = creditCardToVerifyXML.selectSingleNode("CreditCard/CardNumber");
        Node nodeType = creditCardToVerifyXML.selectSingleNode("CreditCard/CardType");
        Node nodeExpiryDate = creditCardToVerifyXML.selectSingleNode("CreditCard/ExpiryDate");
        
        if (nodeNumber!=null &&nodeType!=null &&nodeExpiryDate!=null){
        String nodeNumberValue =nodeNumber.getText();
        String nodeTypeValue = nodeType.getText();
        String nodeExpiryDateMonth =nodeExpiryDate.valueOf("@Month");
        String nodeExpiryDateYear =	nodeExpiryDate.valueOf("@Year");
        statusToadd =VerificationAlgorithm.verify(nodeNumberValue, nodeTypeValue, nodeExpiryDateYear, nodeExpiryDateMonth);
        } else {
        	statusToadd=INVALID_CREDIT_CARD;	
        }
        
        	Element nodeCreditCard = creditCardToVerifyXML.getRootElement();
    		nodeCreditCard.addAttribute("Status",statusToadd);
    		response.setContentType("text/html;charset=UTF-8");
    		
            final PrintWriter out = response.getWriter();
            out.print(creditCardToVerifyXML.asXML());
            out.close();     
				
        } catch(DocumentException e) {
        	System.out.print("A document Exception !!!");        	
        } catch (Error e ){
        	System.out.print("An error occurs!!!");        	
        }
        
        }
         
    }

    
}
