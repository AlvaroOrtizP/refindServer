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
public class ExcepcionRefind extends Exception implements Serializable{

    private String mensajeUsuario;
    private String mensajeAdmin;
    private Usuario usuario;
    private String sentencia;
    public ExcepcionRefind() {
    }

    public ExcepcionRefind(String mensajeUsuario, String mensajeAdmin, Usuario usuario, String sentencia) {
        this.mensajeUsuario = mensajeUsuario;
        this.mensajeAdmin = mensajeAdmin;
        this.usuario = usuario;
        this.sentencia = sentencia;
    }

    public String getSentencia() {
        return sentencia;
    }

    public void setSentencia(String sentencia) {
        this.sentencia = sentencia;
    }

    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }

    public String getMensajeAdmin() {
        return mensajeAdmin;
    }

    public void setMensajeAdmin(String mensajeAdmin) {
        this.mensajeAdmin = mensajeAdmin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "ExcepcionRefind{" + "mensajeUsuario=" + mensajeUsuario + ", mensajeAdmin=" + mensajeAdmin + ", usuario=" + usuario + ", sentencia=" + sentencia + '}';
    }

}
