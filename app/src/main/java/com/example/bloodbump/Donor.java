package com.example.bloodbump;

import android.widget.EditText;

public class Donor extends User {

	public Donor(String name, String lastname, String username, String email, String password, String phone) {
		super();
		this.first_name = name;
		this.last_name = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}

	String getDonor_ID() {
		return donor_ID;
	}

	public void setDonor_ID(String donor_ID) {
		this.donor_ID = donor_ID;
	}

	String donor_ID;

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	String first_name;

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	String last_name;
	String phone;


	//private  DOB;

	/**
	 * 
	 * @param first_name
	 * @param last_name
	 * @param email
	 * @param password
	 */


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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}