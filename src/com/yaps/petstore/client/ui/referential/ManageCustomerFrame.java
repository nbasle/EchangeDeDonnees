package com.yaps.petstore.client.ui.referential;

import com.yaps.petstore.client.ui.AbstractFrame;
import com.yaps.petstore.client.ui.referential.bar.*;
import com.yaps.petstore.common.delegate.CustomerDelegate;
import com.yaps.petstore.common.dto.CustomerDTO;
import com.yaps.petstore.common.exception.CheckException;
import com.yaps.petstore.common.exception.DuplicateKeyException;
import com.yaps.petstore.common.exception.ObjectNotFoundException;
import com.yaps.petstore.common.logging.Trace;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the graphical user interface that enables an employee
 * to create, find, delete and update a Customer.
 */
public final class ManageCustomerFrame extends AbstractFrame implements BarEventListener {

    // Variables declaration
    private final JLabel labelTitle = new JLabel();
    private final JPanel panelCenter = new JPanel();
    private final JTextField textId = new JTextField();
    private final JTextField textFirstname = new JTextField();
    private final JTextField textLastname = new JTextField();
    private final JTextField textTelephone = new JTextField();
    private final JTextField textEmail = new JTextField();
    private final JTextField textStreet1 = new JTextField();
    private final JTextField textStreet2 = new JTextField();
    private final JTextField textCity = new JTextField();
    private final JTextField textState = new JTextField();
    private final JTextField textZipcode = new JTextField();
    private final JTextField textCountry = new JTextField();
    private final JTextField textCreditCardNumber = new JTextField();
    private final JTextField textCreditCardType = new JTextField();
    private final JTextField textCreditCardExpiryDate = new JTextField();

    private final ManageBar manageBar = new ManageBar();

    /**
     * Creates new form
     */
    public ManageCustomerFrame() {
        initComponents();
        pack();
        manageBar.setManageListener(this);
    }

    // This method is called from within the constructor to display all the graphical components
    private void initComponents() {
        // Panel North
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setFont(new Font("Dialog", 1, 18));
        labelTitle.setText("Customer");

        getContentPane().add(labelTitle, BorderLayout.NORTH);

        // Panel Center
        panelCenter.setLayout(new GridLayout(14, 2));

        panelCenter.add(new JLabel("Identifier"));
        panelCenter.add(textId);

        panelCenter.add(new JLabel("First Name"));
        panelCenter.add(textFirstname);

        panelCenter.add(new JLabel("Last Name"));
        panelCenter.add(textLastname);

        panelCenter.add(new JLabel("Telephone"));
        panelCenter.add(textTelephone);

        panelCenter.add(new JLabel("Email"));
        panelCenter.add(textEmail);

        panelCenter.add(new JLabel("Street 1"));
        panelCenter.add(textStreet1);

        panelCenter.add(new JLabel("Street 2"));
        panelCenter.add(textStreet2);

        panelCenter.add(new JLabel("City"));
        panelCenter.add(textCity);

        panelCenter.add(new JLabel("State"));
        panelCenter.add(textState);

        panelCenter.add(new JLabel("Zipcode"));
        panelCenter.add(textZipcode);

        panelCenter.add(new JLabel("Country"));
        panelCenter.add(textCountry);

        panelCenter.add(new JLabel("Credit Card Number"));
        panelCenter.add(textCreditCardNumber);

        panelCenter.add(new JLabel("Credit Card Type"));
        panelCenter.add(textCreditCardType);

        panelCenter.add(new JLabel("Credit Card Expiry Date (MM/YY)"));
        panelCenter.add(textCreditCardExpiryDate);

        getContentPane().add(panelCenter, BorderLayout.CENTER);

        // Panel South
        getContentPane().add(manageBar, BorderLayout.SOUTH);
    }

    public void create(final CreateEvent evt) {
        final String mname = "create";

        // Sets all the Customer data
        final CustomerDTO customerDTO = new CustomerDTO(textId.getText(), textFirstname.getText(), textLastname.getText());
        customerDTO.setTelephone(textTelephone.getText());
        customerDTO.setEmail(textEmail.getText());
        customerDTO.setStreet1(textStreet1.getText());
        customerDTO.setStreet2(textStreet2.getText());
        customerDTO.setCity(textCity.getText());
        customerDTO.setState(textState.getText());
        customerDTO.setZipcode(textZipcode.getText());
        customerDTO.setCountry(textCountry.getText());
        customerDTO.setCreditCardNumber(textCreditCardNumber.getText());
        customerDTO.setCreditCardType(textCreditCardType.getText());
        customerDTO.setCreditCardExpiryDate(textCreditCardExpiryDate.getText());

        try {
            // Creates the customer
            CustomerDelegate.createCustomer(customerDTO);

        } catch (DuplicateKeyException e) {
            JOptionPane.showMessageDialog(this, "This Id already exists", "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot create the customer", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void delete(final DeleteEvent evt) {
        final String mname = "delete";

        try {
            // Deletes the customer
            CustomerDelegate.deleteCustomer(textId.getText());
            reset(new ResetEvent(this));

        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot delete the customer", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void find(final FindEvent evt) {
        final String mname = "find";

        try {
            // Finds the customer
            final CustomerDTO customerDTO = CustomerDelegate.findCustomer(textId.getText());

            // Displays the data
            textId.setText(customerDTO.getId());
            textFirstname.setText(customerDTO.getFirstname());
            textLastname.setText(customerDTO.getLastname());
            textTelephone.setText(customerDTO.getTelephone());
            textEmail.setText(customerDTO.getEmail());
            textStreet1.setText(customerDTO.getStreet1());
            textStreet2.setText(customerDTO.getStreet2());
            textCity.setText(customerDTO.getCity());
            textState.setText(customerDTO.getState());
            textZipcode.setText(customerDTO.getZipcode());
            textCountry.setText(customerDTO.getCountry());
            textCreditCardNumber.setText(customerDTO.getCreditCardNumber());
            textCreditCardType.setText(customerDTO.getCreditCardType());
            textCreditCardExpiryDate.setText(customerDTO.getCreditCardExpiryDate());

        } catch (ObjectNotFoundException e) {
            JOptionPane.showMessageDialog(this, "This customer has not been found", "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot find the customer", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void update(final UpdateEvent evt) {
        final String mname = "update";

        // Sets all the Customer data
        final CustomerDTO customerDTO = new CustomerDTO(textId.getText(), textFirstname.getText(), textLastname.getText());
        customerDTO.setTelephone(textTelephone.getText());
        customerDTO.setEmail(textEmail.getText());
        customerDTO.setStreet1(textStreet1.getText());
        customerDTO.setStreet2(textStreet2.getText());
        customerDTO.setCity(textCity.getText());
        customerDTO.setState(textState.getText());
        customerDTO.setZipcode(textZipcode.getText());
        customerDTO.setCountry(textCountry.getText());
        customerDTO.setCreditCardNumber(textCreditCardNumber.getText());
        customerDTO.setCreditCardType(textCreditCardType.getText());
        customerDTO.setCreditCardExpiryDate(textCreditCardExpiryDate.getText());

        try {
            // Updates the customer
            CustomerDelegate.updateCustomer(customerDTO);

        } catch (CheckException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cannot update the customer", "Error", JOptionPane.ERROR_MESSAGE);
            Trace.throwing(getCname(), mname, e);
        }
    }

    public void reset(final ResetEvent evt) {
        textId.setText("");
        textFirstname.setText("");
        textLastname.setText("");
        textTelephone.setText("");
        textEmail.setText("");
        textStreet1.setText("");
        textStreet2.setText("");
        textCity.setText("");
        textState.setText("");
        textZipcode.setText("");
        textCountry.setText("");
        textCreditCardNumber.setText("");
        textCreditCardType.setText("");
        textCreditCardExpiryDate.setText("");
    }

    public void close(final CloseEvent evt) {
        dispose();
    }
}
