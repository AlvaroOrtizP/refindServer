/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package refindserver;

/**
 *
 * @author Alvaro
 */
public class IniciarServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread servidor = new Servidor();
        servidor.start();

    }
}
