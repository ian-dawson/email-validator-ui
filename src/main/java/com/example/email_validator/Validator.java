/**
 * Validator.java
 */
package com.example.email_validator;

/**
 * This class is for validating email addresses.
 * 
 * @author ian-dawson
 */

import java.util.regex.*;


public class Validator {
	
	/**
	 * Validates email according to a set of rules. 
	 * 
	 * @param email Email address to be validated.
	 * @param rules The rules to be checked against
	 * 
	 * @return the number of checks that passed.
	 */
    public static Integer validate(String email, String[] rules) {
    	Integer checksPassed = 0;  
    	for (Integer i = 0; i < rules.length; i++) {
    		if (Pattern.matches(rules[i], email)) {
    			checksPassed++;
    		}
    	}
    	return checksPassed;
    }
}
