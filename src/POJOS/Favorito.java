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
public class Favorito implements Serializable{

    private Usuario usuario;
    private Anuncio anuncio;
    private String error;
    public Favorito() {
    }

    public Favorito(Usuario usuario, Anuncio anuncio) {
        this.usuario = usuario;
        this.anuncio = anuncio;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    @Override
    public String toString() {
        return "Favorito{" + "usuario=" + usuario + ", anuncio=" + anuncio + '}';
    }

}
