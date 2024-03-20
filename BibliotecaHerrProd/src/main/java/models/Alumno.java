/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package models;

/**
 *
 * @author A5155456HP
 */


import java.util.ArrayList;
import java.util.Date;

public class Alumno {
    private String carnet;
    private String nombre;
    private String apellido;
    private String direccion;
    private Date fechanacimiento;
    private Date fechaingreso;
    private String genero;
    private boolean estado;

    private ArrayList<PrestamoLibro> prestamoLibroArrayList;


    public Alumno() {
    }


    public Alumno(String carnet) {
        this.carnet = carnet;
    }

    public Alumno(String carnet, String nombre, String apellido, String direccion, Date fechanacimiento, Date fechaingreso, String genero, boolean estado) {

        this.carnet = carnet;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.fechanacimiento = fechanacimiento;
        this.fechaingreso = fechaingreso;
        this.genero = genero;
        this.estado = estado;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }


    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public ArrayList<PrestamoLibro> getPrestamoLibroArrayList() {
        return prestamoLibroArrayList;
    }

    public void setPrestamoLibroArrayList(ArrayList<PrestamoLibro> prestamoLibroArrayList) {
        this.prestamoLibroArrayList = prestamoLibroArrayList;
    }
 
}
