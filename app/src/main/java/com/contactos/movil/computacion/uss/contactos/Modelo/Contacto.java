package com.contactos.movil.computacion.uss.contactos.Modelo;

/**
 * Created by Alehexis on 26/11/2015.
 */
public class Contacto {
    public  int id;
    public String nombre;
    public String telefono;
    public String email;

    public Contacto() {

    }

    public Contacto(String email, String telefono, String nombre, int id) {
        this.email = email;
        this.telefono = telefono;
        this.nombre = nombre;
        this.id = id;
    }

    public Contacto(String nombre, String telefono, String email) {
        super();
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
