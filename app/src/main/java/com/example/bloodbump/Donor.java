package com.example.bloodbump;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Donor {
	String password, email, donor_ID, first_name, last_name, phone, DOB, gender, donorType;

	public Donor(String sex, String dob, String bloodgroup, String donorType) {
		this.gender = sex;
		this.DOB = dob;
		this.Bloodgroup = bloodgroup;
		this.donorType = donorType;
	}

	public String getBloodgroup() {
		return Bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		Bloodgroup = bloodgroup;
	}

	String Bloodgroup;

	public String getDonorType() {
		return donorType;
	}

	public void setDonorType(String donorType) {
		this.donorType = donorType;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String sex) {
		this.gender = sex;
	}


	public String getDOB() {
		return DOB;
	}

	public void setDOB(String DOB) {
		this.DOB = DOB;
	}

	public Donor(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public Donor(String name, String lastname, String email, String password, String phone) {
		super();
		this.first_name = name;
		this.last_name = lastname;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}

	public Donor() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public static boolean login(String email, String password) {
		FirebaseAuth userAuth = FirebaseAuth.getInstance();

		userAuth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
						}
					}
				});
		return true;
	}

	public void logout() {
		// TODO - implement User.logout
		throw new UnsupportedOperationException();
	}

	public void resetPassword() {
		// TODO - implement User.resetPassword
		throw new UnsupportedOperationException();
	}

	public void changeInfo() {
		// TODO - implement Donor.addPhone
		throw new UnsupportedOperationException();
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean registerDonor(@NonNull FirebaseAuth auth, Donor donor) {
		final Boolean[] status = new Boolean[1];
		FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
		DatabaseReference reference = userDatabase.getReference("User");
		String fullname = donor.first_name + " " + donor.last_name;
		auth.createUserWithEmailAndPassword(donor.email, donor.password)
				.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							status[0] = true;
							String ID = auth.getUid().toString();
							reference.child(ID).setValue(donor);
						}
					}

				});
		if (status[0]) {
			return true;
		} else
			return false;
	}

}
