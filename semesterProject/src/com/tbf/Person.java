package com.tbf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * The Persons class deals with personCode, broker status, first and last name, address and email address.
 * 
 *
 */
public class Person {
	private String personCode;
	private String brokerStatus;
	private Name name;
	private Address address;
	private ArrayList<String> emailAddress;
	
	
	public static List<Person> retrieveAllPerson() {
		Connection conn = DBTool.connectToDB();
		String query = "Select p.personId, p.alphaCode, p.brokerStat, p.lastName, p.firstName, a.street, a.city, a.zip, s.abbreviation, c.name, e.emailAddress from Person p"
				+ " join Address a on p.addressId = a.addressId join State s on a.stateId = s.stateId join Country c on a.countryId = c.countryId join EmailAddress e"
				+ " on p.personId = e.personId;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Person> persons = new ArrayList<>();
		
			try {
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					String alphaCode = rs.getString("alphaCode");
					String brokerStat = rs.getString("brokerStat");
					String lastName = rs.getString("lastName");
					String firstName = rs.getString("firstName");
					String street = rs.getString("street");
					String city = rs.getString("city");
					String zip = rs.getString("zip");
					String abbreviation = rs.getString("abbreviation");
					String name = rs.getString("name");
					String emailAddress = rs.getString("emailAddress");
					ArrayList<String> email = new ArrayList<>();
					email.add(emailAddress);
					Name n = new Name(firstName, lastName);
					Address a = new Address(street, city, abbreviation, zip, name);
					Person p = new Person(alphaCode, brokerStat, n, a, email);
					persons.add(p);
				}
			} catch (SQLException sqle) {
				throw new RuntimeException(sqle);
			}
			
			DBTool.disconnectFromDB(conn, ps, rs);
			return persons;
	}
	
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
