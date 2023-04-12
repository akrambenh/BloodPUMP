package com.example.bloodbump;

public class Donations {

	public String getDonation_ID() {
		return donation_ID;
	}

	public void setDonation_ID(String donation_ID) {
		this.donation_ID = donation_ID;
	}

	private String donation_ID;

	public String getDonorFullname() {
		return DonorFullname;
	}

	public void setDonorFullname(String donorFullname) {
		DonorFullname = donorFullname;
	}

	private String DonorFullname;

	public String getBloodGroup() {
		return BloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		BloodGroup = bloodGroup;
	}

	private String BloodGroup;

	public Double getQuantity() {
		return Quantity;
	}

	public void setQuantity(Double quantity) {
		Quantity = quantity;
	}

	private Double Quantity;

	public String getDonationLocation() {
		return DonationLocation;
	}

	public void setDonationLocation(String donationLocation) {
		DonationLocation = donationLocation;
	}

	private String DonationLocation;

	public void addDonation() {
		// TODO - implement Donations.addDonation
		throw new UnsupportedOperationException();
	}

	public void getDonationDetails() {
		// TODO - implement Donations.getDonationDetails
		throw new UnsupportedOperationException();
	}

	public void viewDonationsList() {
		// TODO - implement Donations.viewDonationsList
		throw new UnsupportedOperationException();
	}

}