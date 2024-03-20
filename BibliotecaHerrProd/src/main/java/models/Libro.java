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

public class Libro {
    
    private String codigolibro;
    private String titulolibro;
    private int existencia;
    private float precio;
    private CategoriaLibros categoriaLibros;
    private Autor autor;
    private ArrayList<PrestamoLibro> prestamoLibroArrayList;


    public Libro() {
    }

    public Libro(String codigolibro) {
        this.codigolibro = codigolibro;
    }

    public Libro(String codigolibro, String titulolibro, int existencia, float precio) {
        this.codigolibro = codigolibro;
        this.titulolibro = titulolibro;
        this.existencia = existencia;
        this.precio = precio;
    }

    public Libro(String codigolibro, String titulolibro, int existencia,
                 float precio, CategoriaLibros categoriaLibros) {
        this.codigolibro = codigolibro;
        this.titulolibro = titulolibro;
        this.existencia = existencia;
        this.precio = precio;
        this.categoriaLibros = categoriaLibros;
    }

    public Libro(String codigolibro, String titulolibro, int existencia,
                 float precio, Autor autor) {
        this.codigolibro = codigolibro;
        this.titulolibro = titulolibro;
        this.existencia = existencia;
        this.precio = precio;
        this.autor = autor;
    }

    public Libro(String codigolibro, String titulolibro, int existencia,
                 float precio, ArrayList<PrestamoLibro> prestamoLibroArrayList) {
        this.codigolibro = codigolibro;
        this.titulolibro = titulolibro;
        this.existencia = existencia;
        this.precio = precio;
        this.prestamoLibroArrayList = prestamoLibroArrayList;
    }

    public Libro(String codigolibro, String titulolibro, int existencia,
                 float precio, CategoriaLibros categoriaLibros, Autor autor,
                 ArrayList<PrestamoLibro> prestamoLibroArrayList) {
        this.codigolibro = codigolibro;
        this.titulolibro = titulolibro;
        this.existencia = existencia;
        this.precio = precio;
        this.categoriaLibros = categoriaLibros;
        this.autor = autor;
        this.prestamoLibroArrayList = prestamoLibroArrayList;
    }

    public String getCodigolibro() {
        return codigolibro;
    }

    public void setCodigolibro(String codigolibro) {
        this.codigolibro = codigolibro;
    }

    public String getTitulolibro() {
        return titulolibro;
    }

    public void setTitulolibro(String titulolibro) {
        this.titulolibro = titulolibro;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public CategoriaLibros getCategoriaLibros() {
        return categoriaLibros;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setCategoriaLibros(CategoriaLibros categoriaLibros) {
        this.categoriaLibros = categoriaLibros;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public ArrayList<PrestamoLibro> getPrestamoLibroArrayList() {
        return prestamoLibroArrayList;
    }

    public void setPrestamoLibroArrayList(ArrayList<PrestamoLibro> prestamoLibroArrayList) {
        this.prestamoLibroArrayList = prestamoLibroArrayList;

    }
}
