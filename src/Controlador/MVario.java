/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import POJOS.Anuncio;
import POJOS.Categoria;
import POJOS.Comentario;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import refindserver.SesionServidor;

/**
 *
 * @author Alvaro
 */
public class MVario {
        
    /*private void setCategoria(Categoria categoria) {
        try {
            ObjectOutputStream envioObjecto = new ObjectOutputStream(this.clienteConectado.getOutputStream());
            envioObjecto.writeObject(categoria);
        } catch (IOException ex) {
            Logger.getLogger(SesionServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    /*    private String sendAnuncios(ArrayList<Anuncio> listaAnuncios) {
        try {
            ObjectOutputStream envioObjecto = new ObjectOutputStream(clienteConectado.getOutputStream());
            envioObjecto.writeObject(listaAnuncios);
        } catch (IOException io) {
            respuesta = "Error al enviar objeto listaAnuncio - cambiar mensaje";
            System.out.println(io.getMessage());
        }
        return respuesta;
    }

    private String sendCategorias(ArrayList<Categoria> listaCategorias) {
        try {
            ObjectOutputStream envioObjecto = new ObjectOutputStream(clienteConectado.getOutputStream());
            envioObjecto.writeObject(listaCategorias);
        } catch (IOException io) {
            respuesta = "Error al enviar objeto listaAnuncio - cambiar mensaje";
            System.out.println(io.getMessage());
        }
        return respuesta;
    }

    private String sendComentarios(ArrayList<Comentario> listaComentario) {
        try {
            ObjectOutputStream envioObjecto = new ObjectOutputStream(clienteConectado.getOutputStream());
            envioObjecto.writeObject(listaComentario);
        } catch (IOException io) {
            respuesta = "Error al enviar objeto listaAnuncio - cambiar mensaje";
            System.out.println(io.getMessage());
        }
        return respuesta;
    }*/
}
