package com.example.bloodbump;

public class Appointment {

	private String appointment_id;
	private String donor_id;
	private String appointment_date;
	private String appointment_time;
	private String donation_type;
	private String medical_establishement;

	public Appointment(String appointment_id, String donor_id, String appointment_date, String appointment_time, String donation_type, String medical_establishement) {
		this.appointment_id = appointment_id;
		this.donor_id = donor_id;
		this.appointment_date = appointment_date;
		this.appointment_time = appointment_time;
		this.donation_type = donation_type;
		this.medical_establishement = medical_establishement;
	}


	public void viewAppointment() {
		// TODO - implement Appointment.viewAppointment
		throw new UnsupportedOperationException();
	}

	public void book() {
		// TODO - implement Appointment.book
		throw new UnsupportedOperationException();
	}

}