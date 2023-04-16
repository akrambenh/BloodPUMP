package com.example.bloodbump;

public class Hospital {
    public Hospital(String uid, String name, String email, String pseudo, String phone, String password) {
        super();
        this.Hospital_ID = uid;
        this.Name = name;
        this.Email = email;
        this.Supervisor_pseudo = pseudo;
        this.Phone = phone;
        this.Password = password;
    }

    public String getUID() {
        return Hospital_ID;
    }

    public void setUID(String UID) {
        this.Hospital_ID = UID;
    }

    private String Hospital_ID;
    private String Name;
    private String Supervisor_pseudo;
    private String Phone;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private String Email;

    public String getHospital_ID() {
        return Hospital_ID;
    }

    public void setHospital_ID(String hospital_ID) {
        Hospital_ID = hospital_ID;
    }

    private String Password;

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSupervisor_pseudo() {
        return Supervisor_pseudo;
    }

    public void setSupervisor_pseudo(String supervisor_pseudo) {
        Supervisor_pseudo = supervisor_pseudo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
