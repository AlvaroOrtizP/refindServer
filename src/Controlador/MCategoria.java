/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import POJOS.Categoria;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import refindserver.SesionServidor;

/**
 *
 * @author Alvaro
 */
public class MCategoria {

    public static Categoria getCategoria(Categoria categoria, Socket clienteConectado) {
        try {
            ObjectInputStream recepcionObjeto = new ObjectInputStream(clienteConectado.getInputStream());
            categoria = (Categoria) recepcionObjeto.readObject();
        } catch (IOException ex) {
            Logger.getLogger(SesionServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SesionServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categoria;
    }
}
