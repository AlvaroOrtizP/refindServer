/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import POJOS.Comentario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import refindserver.SesionServidor;

/**
 *
 * @author Alvaro
 */
public class MComentario {

    String respuesta = "";

    public String sendComentarios(ArrayList<Comentario> listaComentario, Socket clienteConectado) {
        try {
            ObjectOutputStream envioObjecto = new ObjectOutputStream(clienteConectado.getOutputStream());
            envioObjecto.writeObject(listaComentario);
        } catch (IOException io) {
            respuesta = "Error al enviar objeto listaAnuncio - cambiar mensaje";
            System.out.println(io.getMessage());
        }
        return respuesta;
    }

    public Comentario getComentario(Comentario comentario, Socket clienteConectado) {
        try {
            ObjectInputStream recepcionObjeto = new ObjectInputStream(clienteConectado.getInputStream());
            comentario = (Comentario) recepcionObjeto.readObject();
        } catch (IOException ex) {
            Logger.getLogger(SesionServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SesionServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comentario;
    }
}
