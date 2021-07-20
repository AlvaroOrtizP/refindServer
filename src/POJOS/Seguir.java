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
public class Seguir implements Serializable{

    private Usuario usuario;
    private Usuario seguidor;
    private String error;
    public Seguir() {
    }

    public Seguir(Usuario usuario, Usuario seguidor) {
        this.usuario = usuario;
        this.seguidor = seguidor;
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

   
    public Usuario getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(Usuario seguidor) {
        this.seguidor = seguidor;
    }

    @Override
    public String toString() {
        return "Seguir{" + "usuario=" + usuario + ", seguidor=" + seguidor + '}';
    }

}
