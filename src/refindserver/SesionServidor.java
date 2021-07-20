/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package refindserver;

import Controlador.MVario;
import Controlador.RefindCAD;
import POJOS.Anuncio;
import POJOS.Categoria;
import POJOS.Comentario;
import POJOS.ExcepcionRefind;
import POJOS.Indicador;
import POJOS.Usuario;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alvaro
 */
public class SesionServidor extends Thread {

    RefindCAD refindCAD = null;
    Socket clienteConectado;
    String respuesta = "OK";
    ArrayList<Anuncio> listaAnuncios = new ArrayList<>();
    ArrayList<Categoria> listaCategorias = new ArrayList<>();
    ArrayList<Comentario> listaComentarios = new ArrayList<>();
    MVario mVario = null;

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
        switch (obtenerString()) {
            /**
             * USUARIO
             */
            case Indicador.INSERTAR_USUARIO:
                System.out.println("Servidor.Consola - Orden " + Indicador.INSERTAR_USUARIO);
                usuario = getUsuario(usuario);
                try {
                    refindCAD = new RefindCAD();
                    refindCAD.insertarUsuario(usuario);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                enviarString(respuesta);
                break;
            /**
             * Se usa un objeto Usuario del cual se obtendra el usuarioFirebase
             * y se modifican los datos por los que vengan en el usuario
             */
            case Indicador.ACTUALIZA_USUARIO:
                System.out.println("Servidor.Consola - Orden " + Indicador.ACTUALIZA_USUARIO);
                usuario = getUsuario(usuario);
                try {
                    refindCAD = new RefindCAD();
                    refindCAD.actualizarUsuario(usuario);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                enviarString(respuesta);
                break;
            case Indicador.BUSCAR_USUARIO:
                System.out.println("Servidor.Consola - Orden " + Indicador.BUSCAR_USUARIO);
                //Obtengo objeto que me envia el cliente
                usuario = getUsuario(usuario);
                try {
                    refindCAD = new RefindCAD();
                    usuario = refindCAD.obtenerUsuario(usuario);
                    System.out.println("Servidor.Consola - Se envian datos del usuario " + usuario.getUsuarioFirebase());
                    sendUsuario(usuario);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar objeto usuario devuelto
                enviarString(respuesta);
                break;
            /**
             * ANUNCIO
             */
            case Indicador.OBTENER_ANUNCIO:
                System.out.println("Servidor.Consola - Orden " + Indicador.OBTENER_ANUNCIO);
                //Obtengo objeto que me envia el cliente (anuncio con el anuncio id)
                anuncio = getAnuncio(anuncio);
                try {
                    refindCAD = new RefindCAD();
                    anuncio = refindCAD.obtenerAnuncio(anuncio);
                    sendAnuncio(anuncio);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarString(respuesta);
                break;
            case Indicador.OBTENER_ANUNCIOS:
                System.out.println("Servidor.Consola - Orden " + Indicador.OBTENER_ANUNCIOS);
                try {
                    String cadena = "";
                    refindCAD = new RefindCAD();
                    Categoria categoria = new Categoria();
                    categoria = getCategoria(categoria);
                    listaAnuncios = refindCAD.obtenerAnuncios(categoria);
                    for (int cantidad = 0; cantidad < listaAnuncios.size(); cantidad++) {
                        cadena = cadena + listaAnuncios.get(cantidad).toString();
                    }
                    enviarString(cadena);
                    System.out.println("Se enviaron los anuncios de la categoria " + categoria.getCategoriaId());
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar objeto usuario devuelto
                enviarString(respuesta);
                break;
            /**
             * CATEGORIA
             */
            case Indicador.OBTENER_CATEGORIAS:
                System.out.println("Servidor.Consola - Orden " + Indicador.OBTENER_CATEGORIAS);
                try {
                    String cadena = "";
                    refindCAD = new RefindCAD();
                    listaCategorias = refindCAD.obtenerCategorias();
                    for (int cantidad = 0; cantidad < listaCategorias.size(); cantidad++) {
                        cadena = cadena + listaCategorias.get(cantidad).toString();
                    }
                    enviarString(cadena);
                    System.out.println("Se enviaron las categorias");
                } catch (ExcepcionRefind eR) {
                    //respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                //enviarRespuesta(respuesta);
                break;
            /**
             * FAVORITO
             */
            case Indicador.INSERTAR_FAVORITO:
                System.out.println("Servidor.Consola - Orden " + Indicador.INSERTAR_FAVORITO);
                try {
                    refindCAD = new RefindCAD();
                    usuario = getUsuario(usuario);
                    anuncio = getAnuncio(anuncio);
                    refindCAD.insertarFavorito(usuario, anuncio);
                    respuesta = "TRUE";
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarString(respuesta);
                break;
            case Indicador.SABER_FAVORITO:
                System.out.println("Servidor.Consola - Orden " + Indicador.SABER_FAVORITO);
                try {
                    refindCAD = new RefindCAD();
                    usuario = getUsuario(usuario);
                    anuncio = getAnuncio(anuncio);
                    if (refindCAD.comprobarFavorito(usuario, anuncio)) {
                        enviarString("TRUE");
                    } else {
                        enviarString("");
                    }
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviarRespuesta(respuesta);//En caso afirmativo se enviara el mensaje no modificado
                break;
            case Indicador.OBTENER_FAVORITOS:
                System.out.println("Servidor.Consola - Orden " + Indicador.OBTENER_FAVORITOS);
                try {
                    String cadena = "";
                    refindCAD = new RefindCAD();
                    usuario = getUsuario(usuario);
                    listaAnuncios = refindCAD.obtenerFavoritos(usuario);
                    for (int cantidad = 0; cantidad < listaAnuncios.size(); cantidad++) {
                        cadena = cadena + listaAnuncios.get(cantidad).toString();
                    }
                    enviarString(cadena);
                    System.out.println("Se enviaron " + listaAnuncios.size() + " anuncios de favoritos del usuario " + usuario.getUsuarioFirebase());
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarString(respuesta);
                break;
            case Indicador.ELIMINAR_FAVORITO:
                System.out.println("Servidor.Consola - Orden " + Indicador.ELIMINAR_FAVORITO);
                try {
                    refindCAD = new RefindCAD();
                    usuario = getUsuario(usuario);
                    anuncio = getAnuncio(anuncio);
                    refindCAD.eliminarFavorito(usuario, anuncio);
                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarString(respuesta);
                break;
            /**
             * COMENTARIO
             */
            case Indicador.OBTENER_COMENTARIOS:

                System.out.println("Servidor.Consola - Orden " + Indicador.OBTENER_COMENTARIOS);
                try {
                    String cadena = "";
                    refindCAD = new RefindCAD();
                    anuncio = getAnuncio(anuncio);
                    System.out.println("Servidor.Consola - Comentarios del anuncio " + anuncio.getAnuncioId());
                    listaComentarios = refindCAD.obtenerComentarios(anuncio);
                    System.out.println("la lisata de cometarios tiene " + listaComentarios.size());
                    for (int cantidad = 0; cantidad < listaComentarios.size(); cantidad++) {
                        cadena = cadena + listaComentarios.get(cantidad).toString();
                    }
                    enviarString(cadena);
                    System.out.println("Se enviaron los comentarios");
                } catch (ExcepcionRefind eR) {
                    //respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                break;

            case Indicador.INSERTAR_COMENTARIO:
                System.out.println("Servidor.Consola - Orden " + Indicador.INSERTAR_COMENTARIO);
                try {
                    refindCAD = new RefindCAD();
                    comentario = getComentario(comentario);
                    refindCAD.insertarComentario(comentario);

                } catch (ExcepcionRefind eR) {
                    respuesta = eR.getMensajeUsuario();
                    System.err.println(eR.getMensajeAdmin());
                }
                //enviar mensaje final
                enviarString(respuesta);
                break;
            case Indicador.OBTENER_IMAGEN:
                System.out.println("Servidor entra en el case");
                enviarImagen();
                System.out.println("Servidor sale en el case");
                break;
        }
        System.out.println("Servidor.Consola - Cliente saliendo....");
    }

    /**
     * Revisar
     */
    private void enviarImagen() {
        byte[] b = new byte[1024];
        System.out.println("Servidor entra en el metodo");

        File f = new File("C:\\Users\\Alvaro\\Documents\\NetBeansProjects\\refindServer\\src\\AlmacenFotos\\" + obtenerString());
        System.out.println("Se recibe orden");
        try {
            OutputStream dout = new DataOutputStream(new BufferedOutputStream(clienteConectado.getOutputStream())); // Archivo leído en secuencia

            InputStream ins = new FileInputStream(f);
            int n = ins.read(b);
            while (n != -1) {
                dout.write(b);
                dout.flush();
                n = ins.read(b);
            }
            ins.close();
            dout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * USUARIO
     *
     */
    /**
     *
     * @param usuario
     * @return
     */
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

    /**
     *
     * ANUNCIO
     *
     */
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

    /**
     *
     * COMNETARIO
     *
     */
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
     *
     * VARIOS
     *
     */
    /**
     * Metodo que recibe por un socket un String y reorna su valor
     *
     * @return
     */
    private String obtenerString() {
        String orden = "";
        InputStream is;
        DataInputStream obtengoOrden;

        try {
            is = clienteConectado.getInputStream();

            obtengoOrden = new DataInputStream(is);

            orden = obtengoOrden.readUTF();

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
    private void enviarString(String respuesta) {
        DataOutputStream envioRespuesta;
        //El servidor envia una cadena de texto con la respuesta
        try {
            envioRespuesta = new DataOutputStream(clienteConectado.getOutputStream());
            envioRespuesta.writeUTF(respuesta);
            envioRespuesta.close();
        } catch (IOException i) {
            System.out.println(i.getMessage());
        }
    }

}
/**
 * En obtener usuario igual devuelvo un hasmap con usuario y respuesta En el
 * send igual se puede mejorar y dejar solo uno El enviar respuesta igual con
 * solo hacer uno valdria
 */
