package com.example.bloodbump;

import android.widget.EditText;

import java.time.LocalDate;

public class Donor {
	String password;
	String username;
	String email;
	String donor_ID;
	String first_name;
	String last_name;
	String phone;
	LocalDate DOB;
	String sex;
	String donorType;
	String bloodGroup;
	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}


	public String getDonorType() {
		return donorType;
	}

	public void setDonorType(String donorType) {
		this.donorType = donorType;
	}


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}



	public LocalDate getDOB() {
		return DOB;
	}

	public void setDOB(LocalDate DOB) {
		this.DOB = DOB;
	}


	public Donor(String name, String lastname, String username, String email, String password, String phone) {
		super();
		this.first_name = name;
		this.last_name = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}
	public Donor(String sex, LocalDate dob, String donortype, String bloodgroup){
		super();
		this.sex = sex;
		this.DOB = dob;
		this.donorType = donortype;
		this.bloodGroup = bloodgroup;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	String getDonor_ID() {
		return donor_ID;
	}

	public void setDonor_ID(String donor_ID) {
		this.donor_ID = donor_ID;
	}



	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}



	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}





	//private  DOB;


	public void login(int username, int password) {
		// TODO - implement User.login
		throw new UnsupportedOperationException();
	}
	public void logout() {
		// TODO - implement User.logout
		throw new UnsupportedOperationException();
	}
	public void resetPassword() {
		// TODO - implement User.resetPassword
		throw new UnsupportedOperationException();
	}
	public void registerDonor() {

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