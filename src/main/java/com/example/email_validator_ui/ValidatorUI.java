/**
 * ValidatorUI.java
 */
package com.example.email_validator_ui;

/**
 * A basic UI for validating the format of email addresses.
 * @author ian-dawson
 */

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import com.example.email_validator.*;

@Theme("mytheme")
public class ValidatorUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField email = new TextField();
        email.setCaption("Enter an email:");
        
        final String[] ruleList = {
				"[^@]*@[^@]*",	//Exactly 1 "@"
				".*\\.+.*",		//At least one "."
				".+@.*",		//Local-part is present
				".*@.+\\..+"	//Domain is present
			};

        Button button = new Button("Validate");
        button.addClickListener( e -> {
        	layout.removeAllComponents();
            layout.addComponents(email, button);
        	final Integer checksPassed = Validator.validate(email.getValue(), ruleList);
        	if (checksPassed == ruleList.length) {
        		layout.addComponent(new Label("The email address: " + email.getValue() 
        			+ " passed all " + checksPassed + " validation checks! It's probably valid!"));
        	} else {
        		layout.addComponent(new Label("The email address: " + email.getValue() 
				+ " is invalid. Please check that:"));
        		String[] rule0 = {ruleList[0]};
        		String[] rule2 = {ruleList[2]};
        		String[] rule3 = {ruleList[3]};
        		if (Validator.validate(email.getValue(), rule0) != 1) {
        			layout.addComponent(new Label("The address contains only 1 \"@\" symbol"));
        		}
        		if (Validator.validate(email.getValue(), rule2) != 1) {
        			layout.addComponent(new Label("There is a local-part before the \"@\" symbol"));
        		}
        		if (Validator.validate(email.getValue(), rule3) != 1) {
        			layout.addComponent(new Label("There is a valid domain after the \"@\" symbol"));
        		}
        	}
        });
        
        layout.addComponents(email, button);        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "ValidatorUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = ValidatorUI.class, productionMode = false)
    public static class ValidatorUIServlet extends VaadinServlet {
    }
}
