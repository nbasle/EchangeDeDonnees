/*
 * Created on 10 déc. 2005
 * Veronique 
 */
package com.yaps.petstore.server.domain;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * This class encapsulates all the data for a credit card.
 *
 * @see com.yaps.petstore.server.domain.customer.Customer
 * @see com.yaps.petstore.server.domain.order.Order
 */
public final class CreditCard extends DomainObject {

    // ======================================
    // =             Attributes             =
    // ======================================
    private String _creditCardNumber;
    private String _creditCardType;
    private String _creditCardExpiryDate;

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    public String getCreditCardNumber() {
        return _creditCardNumber;
    }

    public void setCreditCardNumber(final String creditCardNumber) {
        _creditCardNumber = creditCardNumber;
    }

    public String getCreditCardType() {
        return _creditCardType;
    }

    public void setCreditCardType(final String creditCardType) {
        _creditCardType = creditCardType;
    }

    public String getCreditCardExpiryDate() {
        return _creditCardExpiryDate;
    }

    public void setCreditCardExpiryDate(final String creditCardExpiryDate) {
        _creditCardExpiryDate = creditCardExpiryDate;
    }
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Credit Card{");
        buf.append(",creditCardNumber=").append(getCreditCardNumber());
        buf.append(",creditCardType=").append(getCreditCardType());
        buf.append(",creditCardExpiryDate=").append(getCreditCardExpiryDate());
        buf.append('}');
        return buf.toString();
    }
    public Document toXML() {
    	Document document = DocumentHelper.createDocument();
    	Element root = document.addElement("CreditCard");
    	root.addElement("CardNumber").addText(getCreditCardNumber());
    	root.addElement("CardType").addText(getCreditCardType());
    	String date =getCreditCardExpiryDate();
    	root.addElement("ExpiryDate").addAttribute("Month",date.substring(0,date.indexOf('/'))).
		addAttribute("Year", date.substring(date.indexOf('/')+1));
		
    	return document;
    }
}
