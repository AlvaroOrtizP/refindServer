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
public class Anuncio implements Serializable {

    private Integer anuncioId;
    private String titulo;
    private String descripcion;
    private Categoria categoria;
    private Usuario usuario;
    private String telefono;
    private String foto;
    private String error;

    public Anuncio() {
    }

    public Anuncio(Integer anuncioId, String titulo, String descripcion, Categoria categoria, Usuario usuario, String telefono, String foto) {
        this.anuncioId = anuncioId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.usuario = usuario;
        this.telefono = telefono;
        this.foto = foto;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getAnuncioId() {
        return anuncioId;
    }

    public void setAnuncioId(Integer anuncioId) {
        this.anuncioId = anuncioId;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuarioFirebase) {
        this.usuario = usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
         return "-/" + anuncioId + "/" + titulo + "/" + descripcion + "/" + categoria + "/" + usuario + "/" + telefono + "/" + foto + "/";
    }

}
