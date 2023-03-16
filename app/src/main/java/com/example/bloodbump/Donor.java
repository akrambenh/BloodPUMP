package com.example.bloodbump;

public class Donor extends User {

	private String donor_ID;
	String first_name;
	String last_name;
	private String phone;
	//private  DOB;

	/**
	 * 
	 * @param first_name
	 * @param last_name
	 * @param username
	 * @param email
	 * @param password
	 */
	public void Donor(String first_name, String last_name, String username, String email, String password, String phone){
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}
	public void registerDonor(int first_name, int last_name, int username, int email, int password) {
		// TODO - implement Donor.registerDonor
		throw new UnsupportedOperationException();
	}

	public void addPhone() {
		// TODO - implement Donor.addPhone
		throw new UnsupportedOperationException();
	}

	public void updateLocation() {
		// TODO - implement Donor.updateLocation
		throw new UnsupportedOperationException();
	}

}