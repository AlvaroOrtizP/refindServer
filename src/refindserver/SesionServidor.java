/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package refindserver;

import Controlador.RefindCAD;
import POJOS.Anuncio;
import POJOS.Categoria;
import POJOS.ExcepcionRefind;
import POJOS.Indicador;
import POJOS.Usuario;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alvaro
 */
public class SesionServidor extends Thread {

    Socket clienteConectado;
    String respuesta = "OK";
    ArrayList<Anuncio> listaAnuncios = new ArrayList<>();
    ArrayList<Categoria> listaCategorias = new ArrayList<>();

    public SesionServidor(Socket clienteConectado) {
        this.clienteConectado = clienteConectado;
    }

    /**
     * Método que implementa el comportamiento del hilo
     */
    @Override
    public void run() {
        Usuario usuario = null;
        Anuncio anuncio = null;
        switch (obtenerOrden()) {
            /**
             * USUARIO
             */
            case Indicador.INSERTAR_USUARIO:
                System.out.println("Servidor.Consola - Orden " + Indicador.INSERTAR_USUARIO);
                usuario = getUsuario(usuario, clienteConectado);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    refindCAD.insertarUsuario(usuario);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                enviarRespuesta(respuesta);
                break;
            /**
             * Se usa un objeto Usuario del cual se obtendra el usuarioFirebase
             * y se modifican los datos por los que vengan en el usuario
             */
            case Indicador.ACTUALIZA_USUARIO:
                System.out.println("Servidor.Consola - Orden " + Indicador.ACTUALIZA_USUARIO);
                usuario = getUsuario(usuario, clienteConectado);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    refindCAD.actualizarUsuario(usuario);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                enviarRespuesta(respuesta);
                break;
            case Indicador.BUSCAR_USUARIO:
                System.out.println("Servidor.Consola - Orden " + Indicador.BUSCAR_USUARIO);
                //Obtengo objeto que me envia el cliente
                usuario = getUsuario(usuario, clienteConectado);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    usuario = refindCAD.obtenerUsuario(usuario);
                    sendUsuario(usuario, clienteConectado);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar objeto usuario devuelto
                enviarRespuesta(respuesta);
                break;
            /**
             * ANUNCIO
             */
            case Indicador.OBTENER_ANUNCIO:
                System.out.println("Servidor.Consola - Orden " + Indicador.OBTENER_ANUNCIO);
                //Obtengo objeto que me envia el cliente (anuncio con el anuncio id)
                anuncio = getAnuncio(anuncio, clienteConectado);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    /**
                     * Usando el id del anuncio obtengo todos sus datos (Anuncio
                     * Categoria Usuario)
                     */
                    anuncio = refindCAD.obtenerAnuncio(anuncio);
                    sendAnuncio(anuncio, clienteConectado);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarRespuesta(respuesta);
                break;
            case Indicador.OBTENER_ANUNCIOS:
                System.out.println("Servidor.Consola - Orden " + Indicador.OBTENER_ANUNCIOS);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    listaAnuncios = refindCAD.obtenerAnuncios();
                    sendAnuncios(listaAnuncios, clienteConectado);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar objeto usuario devuelto
                enviarRespuesta(respuesta);
                break;
            /**
             * CATEGORIA
             */
            case Indicador.OBTENER_CATEGORIAS:
                System.out.println("Servidor.Consola - Orden " + Indicador.OBTENER_CATEGORIAS);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    /**
                     * Usando el id del anuncio obtengo todos sus datos (Anuncio
                     * Categoria Usuario)
                     */
                    //listaCategorias = refindCAD.obtenerCategorias();
                    sendCategorias(listaCategorias, clienteConectado);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarRespuesta(respuesta);
                break;
            /**
             * FAVORITO
             */
            case Indicador.INSERTAR_FAVORITO:
                System.out.println("Servidor.Consola - Orden " + Indicador.INSERTAR_FAVORITO);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    /**
                     * Usando el id del anuncio obtengo todos sus datos (Anuncio
                     * Categoria Usuario)
                     */
                    //listaCategorias = refindCAD.obtenerCategorias();
                    sendCategorias(listaCategorias, clienteConectado);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarRespuesta(respuesta);
                break;
            case Indicador.SABER_FAVORITO:
                System.out.println("Servidor.Consola - Orden " + Indicador.SABER_FAVORITO);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    /**
                     * Usando el id del anuncio obtengo todos sus datos (Anuncio
                     * Categoria Usuario)
                     */
                    //listaCategorias = refindCAD.obtenerCategorias();
                    sendCategorias(listaCategorias, clienteConectado);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarRespuesta(respuesta);
                break;
            case Indicador.OBTENER_FAVORITOS:
                System.out.println("Servidor.Consola - Orden " + Indicador.OBTENER_FAVORITOS);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    /**
                     * Usando el id del anuncio obtengo todos sus datos (Anuncio
                     * Categoria Usuario)
                     */
                    //listaCategorias = refindCAD.obtenerCategorias();
                    sendCategorias(listaCategorias, clienteConectado);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarRespuesta(respuesta);
                break;
            case Indicador.ELIMINAR_FAVORITO:
                System.out.println("Servidor.Consola - Orden " + Indicador.ELIMINAR_FAVORITO);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    /**
                     * Usando el id del anuncio obtengo todos sus datos (Anuncio
                     * Categoria Usuario)
                     */
                    //listaCategorias = refindCAD.obtenerCategorias();
                    sendCategorias(listaCategorias, clienteConectado);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarRespuesta(respuesta);
                break;
            /**
             * COMENTARIO
             */
            case Indicador.OBTENER_COMENTARIOS:
                System.out.println("Servidor.Consola - Orden " + Indicador.OBTENER_COMENTARIOS);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    /**
                     * Usando el id del anuncio obtengo todos sus datos (Anuncio
                     * Categoria Usuario)
                     */
                    //listaCategorias = refindCAD.obtenerCategorias();
                    sendCategorias(listaCategorias, clienteConectado);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarRespuesta(respuesta);
                break;

            case Indicador.INSERTAR_COMENTARIO:
                System.out.println("Servidor.Consola - Orden " + Indicador.INSERTAR_COMENTARIO);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    /**
                     * Usando el id del anuncio obtengo todos sus datos (Anuncio
                     * Categoria Usuario)
                     */
                    //listaCategorias = refindCAD.obtenerCategorias();
                    sendCategorias(listaCategorias, clienteConectado);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarRespuesta(respuesta);
                break;
        }
        System.out.println("Servidor.Consola - Cliente saliendo....");
    }

    private Usuario getUsuario(Usuario usuario, Socket clienteConectado) {
        try {
            ObjectInputStream recepcionObjeto = new ObjectInputStream(clienteConectado.getInputStream());
            usuario = (Usuario) recepcionObjeto.readObject();
        } catch (IOException ex) {
            Logger.getLogger(SesionServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SesionServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

    private Anuncio getAnuncio(Anuncio anuncio, Socket clienteConectado) {
        try {
            ObjectInputStream recepcionObjeto = new ObjectInputStream(clienteConectado.getInputStream());
            anuncio = (Anuncio) recepcionObjeto.readObject();
        } catch (IOException ex) {
            Logger.getLogger(SesionServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SesionServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return anuncio;
    }

    /**
     * Metodo que recibe por un socket un String y reorna su valor
     *
     * @return
     */
    private String obtenerOrden() {
        String orden = "";
        InputStream is;
        DataInputStream obtengoOrden;

        try {
            is = clienteConectado.getInputStream();
            obtengoOrden = new DataInputStream(is);
            orden = obtengoOrden.readUTF();

            //obtengoOrden.close();
        } catch (IOException ex) {
            Logger.getLogger(SesionServidor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orden;
    }

    /**
     * Metodo que envia una respuesta (String) mediante sockets a un cliente
     *
     * @param respuesta
     * @return
     */
    private void enviarRespuesta(String respuesta) {
        DataOutputStream envioRespuesta;
        //El servidor envia una cadena de texto con la respuesta
        try {
            envioRespuesta = new DataOutputStream(clienteConectado.getOutputStream());
            envioRespuesta.writeUTF(respuesta);
            // envioRespuesta.close();
        } catch (IOException i) {
            System.out.println(i.getMessage());
        }
    }

    private String sendUsuario(Usuario usuario, Socket clienteConectado) {
        try {
            ObjectOutputStream envioObjecto = new ObjectOutputStream(clienteConectado.getOutputStream());
            envioObjecto.writeObject(usuario);
        } catch (IOException io) {
            respuesta = "Error al enviar objeto usuario - cambiar mensaje";
            System.out.println(io.getMessage());
        }
        return respuesta;
    }

    private String sendAnuncio(Anuncio anuncio, Socket clienteConectado) {
        try {
            ObjectOutputStream envioObjecto = new ObjectOutputStream(clienteConectado.getOutputStream());
            envioObjecto.writeObject(anuncio);
        } catch (IOException io) {
            respuesta = "Error al enviar objeto anuncio - cambiar mensaje";
            System.out.println(io.getMessage());
        }
        return respuesta;
    }

    private String sendAnuncios(ArrayList<Anuncio> listaAnuncios, Socket clienteConectado) {
        try {
            ObjectOutputStream envioObjecto = new ObjectOutputStream(clienteConectado.getOutputStream());
            envioObjecto.writeObject(listaAnuncios);
        } catch (IOException io) {
            respuesta = "Error al enviar objeto listaAnuncio - cambiar mensaje";
            System.out.println(io.getMessage());
        }
        return respuesta;
    }

    private String sendCategorias(ArrayList<Categoria> listaCategorias, Socket clienteConectado) {
        try {
            ObjectOutputStream envioObjecto = new ObjectOutputStream(clienteConectado.getOutputStream());
            envioObjecto.writeObject(listaCategorias);
        } catch (IOException io) {
            respuesta = "Error al enviar objeto listaAnuncio - cambiar mensaje";
            System.out.println(io.getMessage());
        }
        return respuesta;
    }

}
/**
 * En obtener usuario igual devuelvo un hasmap con usuario y respuesta En el
 * send igual se puede mejorar y dejar solo uno El enviar respuesta igual con
 * solo hacer uno valdria
 */
