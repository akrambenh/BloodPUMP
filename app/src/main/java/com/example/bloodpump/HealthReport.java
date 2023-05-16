package com.example.bloodpump;

public class HealthReport {

	private String donor_ID;
	private String bloodGroup;
	private boolean HIV;
	private boolean malaria;
	private int systolic;
	private int diastolic;

	public HealthReport(String donor_id, String bloodGroup, boolean hiv, boolean malaria, int systolic, int diastolic) {
		donor_ID = donor_id;
		this.bloodGroup = bloodGroup;
		HIV = hiv;
		this.malaria = malaria;
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public void viewReport() {
		// TODO - implement HealthReport.viewReport
		throw new UnsupportedOperationException();
	}

}