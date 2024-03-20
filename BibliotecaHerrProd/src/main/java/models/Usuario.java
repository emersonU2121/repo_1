/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author A5155456HP
 */
public class Usuario {
  
    private int id;
    private String userName;
    private String contra;
    private int rol;

    public Usuario() {
    }

    public Usuario(int id, String userName, String contra, int rol) {
        this.id = id;
        this.userName = userName;
        this.contra = contra;
        this.rol = rol;
    }

    public Usuario(String userName, String contra) {
        this.userName = userName;
        this.contra = contra;
    }

    public Usuario(String userName, String contra, int rol) {
        this.userName = userName;
        this.contra = contra;
        this.rol = rol;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }  
}
