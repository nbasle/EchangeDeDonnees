/*
 * Created on 10 déc. 2005
 * Veronique
 */
package com.yaps.petstore.server.service.creditcard;

import org.dom4j.Document;
import org.dom4j.Node;

import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.server.domain.CreditCard;

/**
 * @author Veronique
 *
 */
public class CreditCardService {

	/**
	 * @param creditCard
	 */
	public void verifyCreditCard(CreditCard creditCard) throws CheckException{
		Document outputDoc = HTTPSender.send(creditCard.toXML());
		Node node = outputDoc.selectSingleNode("CreditCard");
		String validityValue =node.valueOf("@Status");
		if(!validityValue.equals("Valid")) {
			throw new CheckException("The Credit card is invalid for folowing reason:"+validityValue);
		}		
	}
}
