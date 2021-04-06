/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package refindserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Alvaro
 */
public class Servidor extends Thread {

    /**
     * Método que implementa el comportamiento del hilo
     */
    @Override
    public void run() {
        try {
            System.out.println("Servidor.Consola - Servidor encendido");
            int puertoServidor = 30500;
            ServerSocket socketServidor = new ServerSocket(puertoServidor);

            ArrayList<SesionServidor> sesiones = new ArrayList();

            Socket clienteConectado;
            SesionServidor sesion;
            while (true) {
                clienteConectado = socketServidor.accept();
                System.out.println("Servidor.Consola - Cliente conectado");
                sesion = new SesionServidor(clienteConectado);
                sesiones.add(sesion);
                sesion.start();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
