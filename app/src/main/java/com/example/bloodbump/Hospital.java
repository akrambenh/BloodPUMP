package com.example.bloodbump;

public class Hospital {
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    private String UID;
    private String Name;
    private String Supervisor_pseudo;
    private String Phone;
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
