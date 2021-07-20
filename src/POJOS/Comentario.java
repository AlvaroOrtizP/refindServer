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
public class Comentario implements Serializable{

    private Integer comentarioId;
    private Usuario usuario;
    private Anuncio anuncio;
    private String texto;
    private String error;
    public Comentario() {
    }

    public Comentario(Integer comentarioId, Usuario usuario, Anuncio anuncio, String texto) {
        this.comentarioId = comentarioId;
        this.usuario = usuario;
        this.anuncio = anuncio;
        this.texto = texto;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getComentarioId() {
        return comentarioId;
    }

    public void setComentarioId(Integer comentarioId) {
        this.comentarioId = comentarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
       return "-/" + comentarioId + "/" + usuario.getNombre() + "/" + anuncio.getAnuncioId() + "/" + texto + "/";
    }

}
