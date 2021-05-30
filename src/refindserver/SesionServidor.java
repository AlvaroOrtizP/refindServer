/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package refindserver;

import Controlador.RefindCAD;
import POJOS.Anuncio;
import POJOS.Categoria;
import POJOS.Comentario;
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
    ArrayList<Comentario> listaComentarios = new ArrayList<>();

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
        Comentario comentario = null;
        switch (getOrden()) {
            /**
             * USUARIO
             */
            case Indicador.INSERTAR_USUARIO:
                System.out.println("Servidor.Consola - Orden " + Indicador.INSERTAR_USUARIO);
                usuario = getUsuario(usuario);
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
                usuario = getUsuario(usuario);
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
                usuario = getUsuario(usuario);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    usuario = refindCAD.obtenerUsuario(usuario);
                    sendUsuario(usuario);
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
                anuncio = getAnuncio(anuncio);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    anuncio = refindCAD.obtenerAnuncio(anuncio);
                    sendAnuncio(anuncio);
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
                    Categoria categoria = new Categoria();
                    categoria = getCategoria(categoria);
                    listaAnuncios = refindCAD.obtenerAnuncios(categoria);
                    sendAnuncios(listaAnuncios);
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
                    listaCategorias = refindCAD.obtenerCategorias();
                    sendCategorias(listaCategorias);
                } catch (ExcepcionRefind eR) {
                    //respuesta = eR.getMensajeUsuario();
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
                    usuario = getUsuario(usuario);
                    anuncio = getAnuncio(anuncio);
                    refindCAD.insertarFavorito(usuario, anuncio);

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
                    usuario = getUsuario(usuario);
                    anuncio = getAnuncio(anuncio);
                    refindCAD.comprobarFavorito(usuario, anuncio);

                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                enviarRespuesta(respuesta);//En caso afirmativo se enviara el mensaje no modificado
                break;
            case Indicador.OBTENER_FAVORITOS:
                System.out.println("Servidor.Consola - Orden " + Indicador.OBTENER_FAVORITOS);
                try {
                    RefindCAD refindCAD = new RefindCAD();
                    usuario = getUsuario(usuario);
                    listaAnuncios = refindCAD.obtenerFavoritos(usuario);
                    sendAnuncios(listaAnuncios);
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
                    usuario = getUsuario(usuario);
                    anuncio = getAnuncio(anuncio);
                    refindCAD.eliminarFavorito(usuario, anuncio);
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
                    anuncio = getAnuncio(anuncio);
                    listaComentarios = refindCAD.obtenerComentarios(anuncio);
                    sendCategorias(listaCategorias);
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
                    comentario = getComentario(comentario);
                    refindCAD.insertarComentario(comentario);

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

    private Usuario getUsuario(Usuario usuario) {//Quite el socket cliente
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

    private Anuncio getAnuncio(Anuncio anuncio) {
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

    private Comentario getComentario(Comentario comentario) {
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

    /**
     * Metodo que recibe por un socket un String y reorna su valor
     *
     * @return
     */
    private String getOrden() {
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

    private Categoria getCategoria(Categoria categoria) {
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

    private String sendUsuario(Usuario usuario) {
        try {
            ObjectOutputStream envioObjecto = new ObjectOutputStream(clienteConectado.getOutputStream());
            envioObjecto.writeObject(usuario);
        } catch (IOException io) {
            respuesta = "Error al enviar objeto usuario - cambiar mensaje";
            System.out.println(io.getMessage());
        }
        return respuesta;
    }

    private String sendAnuncio(Anuncio anuncio) {
        try {
            ObjectOutputStream envioObjecto = new ObjectOutputStream(clienteConectado.getOutputStream());
            envioObjecto.writeObject(anuncio);
        } catch (IOException io) {
            respuesta = "Error al enviar objeto anuncio - cambiar mensaje";
            System.out.println(io.getMessage());
        }
        return respuesta;
    }

    private String sendAnuncios(ArrayList<Anuncio> listaAnuncios) {
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
    }

}
/**
 * En obtener usuario igual devuelvo un hasmap con usuario y respuesta En el
 * send igual se puede mejorar y dejar solo uno El enviar respuesta igual con
 * solo hacer uno valdria
 */
