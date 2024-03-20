/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author A5155456HP
 */
public class CategoriaLibros {
 
    private String idCategoriaLibro;
    private String categoriaLibro;

    public CategoriaLibros() {
    }

    public CategoriaLibros(String idCategoriaLibro, String categoriaLibro) {
        this.idCategoriaLibro = idCategoriaLibro;
        this.categoriaLibro = categoriaLibro;
    }

    public String getIdCategoriaLibro() {
        return idCategoriaLibro;
    }

    public void setIdCategoriaLibro(String idCategoriaLibro) {
        this.idCategoriaLibro = idCategoriaLibro;
    }

    public String getCategoriaLibro() {
        return categoriaLibro;
    }

    public void setCategoriaLibro(String categoriaLibro) {
        this.categoriaLibro = categoriaLibro;
    }   
}
