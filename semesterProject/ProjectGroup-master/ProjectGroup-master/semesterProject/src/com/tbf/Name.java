package com.tbf;
/**
 * This class is used to manage the name from the Persons file. It stores the first and last name.
 * 
 *
 */
public class Name {
	private String firstName;
	private String lastName;
	
	
	public Name(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
