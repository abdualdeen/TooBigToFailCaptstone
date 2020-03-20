package com.tbf;

import java.util.ArrayList;

/**
 * The Persons class deals with personCode, broker status, first and last name, address and email address.
 * 
 *
 */
public class Person {
	// FOR TESTING, REMOVE THE MAIN ARGS LATER
//	public static void main(String args[]) {
//		List<Person> persons = retrieveAllPerson();
//	}
	
	private String personCode;
	private String brokerStatus;
	private Name name;
	private Address address;
	private ArrayList<String> emailAddress;
	
	
	/**
	 * This constructor is used when person has all the needed information.
	 * @param personCode
	 * @param brokerStatus
	 * @param name
	 * @param address
	 * @param emailAddress
	 */
	public Person(String personCode, String brokerStatus, Name name, Address address, ArrayList<String> emailAddress) {
		super();
		this.personCode = personCode;
		this.brokerStatus = brokerStatus;
		this.name = name;
		this.address = address;
		this.setEmailAddress(emailAddress);
	}

	/**
	 * This constructor used when the person set does not contain any email address(s).
	 * @param personCode
	 * @param brokerStatus
	 * @param name
	 * @param address
	 */
	public Person(String personCode, String brokerStatus, Name name, Address address) {
		super();
		this.personCode = personCode;
		this.brokerStatus = brokerStatus;
		this.name = name;
		this.address = address;
	}

	
	/**
	 * Constructor used to initialize variable before while loop that iterates through the file.
	 */
	public Person() {
	}

	
	/**
	 * Here lies the setters and getters for each variable.
	 * @return
	 */
	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getBrokerStatus() {
		return brokerStatus;
	}

	public void setBrokerStatus(String brokerStatus) {
		this.brokerStatus = brokerStatus;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}


	public ArrayList<String> getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(ArrayList<String> emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Address getAddress() {
		return address;
	}


}
