package com.example.jstalin.apuestasonline.lessons;

/**
 * Created by JStalin on 11/01/2018.
 */

/**
 * Clase que contiene informacion que se almacena en el control de registro
 */
public class DataRegistry {

    private String name;
    private String email;
    private String birthdate;

    public DataRegistry(String name, String email, String birthdate) {
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
