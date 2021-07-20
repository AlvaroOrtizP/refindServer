/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJOS;

import java.io.Serializable;

/**
 *
 * @author alorp
 */
public class Categoria implements Serializable {

    private Integer categoriaId;
    private String titulo;
    private String descripcion;
    private String foto;
    private String error;

    public Categoria() {
    }

    public Categoria(Integer categoriaId, String titulo, String descripcion, String foto) {
        this.categoriaId = categoriaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "-/" + categoriaId + "/" + titulo + "/" + descripcion + "/" + foto + "/";
    }

}
