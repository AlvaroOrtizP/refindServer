
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import POJOS.Anuncio;
import POJOS.Categoria;
import POJOS.Comentario;
import POJOS.ExcepcionRefind;
import POJOS.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

/**
 *
 * @author Alvaro Ortiz Pedroso
 */
public class RefindCAD {

    private Connection conexion;

    public RefindCAD() throws ExcepcionRefind {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ExcepcionRefind e = new ExcepcionRefind();
            e.setMensajeUsuario("Error general del sistema. Consulte con su administrador");
            e.setMensajeAdmin("Error con el driver de mySql");
            throw e;
        }
    }

    private void conectar() throws ExcepcionRefind {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/refind?user=root&password=");
        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();
            e.setMensajeUsuario("Error general del sistema. Consulte con su administrador");
            e.setMensajeAdmin("Error en la conexion a la base de datos de refind");
            throw e;
        }
    }

    /**
     * ------------- METODOS USUARIO
     */
    /**
     *
     *
     *
     * Ademas el identificador de usuario no puede aparecer duplicado ya que es
     * gestionbado por fireBase
     *
     * @param usuario
     * @return
     * @throws ExcepcionRefind
     */
    public int insertarUsuario(Usuario usuario) throws ExcepcionRefind {
        int registrosAfectados = 0;
        String dml = "INSERT INTO usuario(usuario_firebase, nombre, apellido, biografia, email, foto, creador) VALUES (?,?,?,?,?,?,0)";
        conectar();
        try {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, usuario.getUsuarioFirebase());
            sentenciaPreparada.setString(2, usuario.getNombre());
            sentenciaPreparada.setString(3, usuario.getApellido());
            sentenciaPreparada.setString(5, usuario.getEmail());
            sentenciaPreparada.setString(4, usuario.getBiografia());
            sentenciaPreparada.setString(6, usuario.getFoto());//
            //sentenciaPreparada.setObject(7, usuario.getCreador(), Types.INTEGER);
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();

            String[] palabraClave = {"null"};
            String error = "";
            for (int i = 0; i < palabraClave.length; i++) {
                if (ex.getMessage().contains(palabraClave[i])) {
                    error = palabraClave[i];
                    break;
                }
            }
            switch (error) {
                case "null":
                    e.setMensajeUsuario("Es necesario rellenar todos los campos");
                    break;
                default:
                    e.setMensajeUsuario("Error general del sistema. Consulte con su administrador");
            }
            e.setMensajeAdmin(ex.getMessage());
            throw e;
        }
        return registrosAfectados;
    }

    public int actualizarUsuario(Usuario usuario) throws ExcepcionRefind {
        int registrosAfectados = 0;
        String dml = "UPDATE usuario SET nombre=?,apellido=?,email=?,biografia=? WHERE usuario_firebase=?";
        conectar();
        try {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, usuario.getNombre());
            sentenciaPreparada.setString(2, usuario.getApellido());
            sentenciaPreparada.setString(3, usuario.getEmail());
            sentenciaPreparada.setString(4, usuario.getBiografia());
            sentenciaPreparada.setString(5, usuario.getUsuarioFirebase());
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();

            String[] palabraClave = {"null"};
            String error = "";
            for (int i = 0; i < palabraClave.length; i++) {
                if (ex.getMessage().contains(palabraClave[i])) {
                    error = palabraClave[i];
                    break;
                }
            }
            switch (error) {
                case "null":
                    e.setMensajeUsuario("Es necesario rellenar todos los campos");
                    break;
                default:
                    e.setMensajeUsuario("Error general del sistema. Consulte con su administrador");
            }
            e.setMensajeAdmin(ex.getMessage());
            throw e;
        }
        return registrosAfectados;
    }

    public void actualizarFotoUsuario() {

    }//FALTA

    public int actualizarCreador(Usuario usuario) throws ExcepcionRefind {
        int registrosAfectados = 0;
        String dml = "UPDATE usuario SET creador=? WHERE usuario_firebase=?";
        conectar();
        try {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);

            sentenciaPreparada.setObject(1, usuario.getCreador(), Types.INTEGER);
            sentenciaPreparada.setString(2, usuario.getUsuarioFirebase());

            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();

            String[] palabraClave = {"null"};
            String error = "";
            for (int i = 0; i < palabraClave.length; i++) {
                if (ex.getMessage().contains(palabraClave[i])) {
                    error = palabraClave[i];
                    break;
                }
            }
            switch (error) {
                case "null":
                    e.setMensajeUsuario("Es necesario rellenar todos los campos");
                    break;
                default:
                    e.setMensajeUsuario("Error general del sistema. Consulte con su administrador");
            }
            e.setMensajeAdmin(ex.getMessage());
            throw e;
        }
        return registrosAfectados;
    }

    public Usuario obtenerUsuario(Usuario usuarioFirebase) throws ExcepcionRefind {
        conectar();
        Usuario usuario = new Usuario();
        String sql = "select * from usuario where usuario_firebase = '" + usuarioFirebase.getUsuarioFirebase() + "'";

        try {
            conectar();
            Statement sentencia = conexion.createStatement();
            ResultSet res = sentencia.executeQuery(sql);
            while (res.next()) {
                usuario.setUsuarioFirebase(usuarioFirebase.getUsuarioFirebase());
                usuario.setNombre(res.getString("nombre"));
                usuario.setApellido(res.getString("apellido"));
                usuario.setBiografia(res.getString("biografia"));
                usuario.setEmail(res.getString("email"));
                usuario.setCreador(res.getInt("creador"));
                usuario.setFoto(res.getString("foto"));
            }
            res.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();
            e.setMensajeAdmin(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
        }
        return usuario;
    }

    /**
     * ------------- METODOS FAVORITOS
     */
    public int insertarFavorito(Usuario usuario, Anuncio anuncio) throws ExcepcionRefind {
        int registrosAfectados = 0;
        String dml = "INSERT INTO favorito (usuario_firebase, anuncio_id) values (?,?)";
        conectar();
        try {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, usuario.getUsuarioFirebase());
            sentenciaPreparada.setObject(2, anuncio.getAnuncioId(), Types.INTEGER);
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();

            String[] palabraClave = {"null"};
            String error = "";
            for (int i = 0; i < palabraClave.length; i++) {
                if (ex.getMessage().contains(palabraClave[i])) {
                    error = palabraClave[i];
                    break;
                }
            }
            switch (error) {
                case "null":
                    e.setMensajeUsuario("Es necesario rellenar todos los campos");
                    break;
                default:
                    e.setMensajeUsuario("Error general del sistema. Consulte con su administrador");
            }
            e.setMensajeAdmin(ex.getMessage());
            throw e;
        }
        return registrosAfectados;
    }

    public int eliminarFavorito(Usuario usuario, Anuncio anuncio) throws ExcepcionRefind {
        int registrosAfectados = 0;
        String dml = "DELETE FROM favorito WHERE usuario_firebase=? and anuncio_id=?";
        conectar();
        try {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, usuario.getUsuarioFirebase());
            sentenciaPreparada.setObject(2, anuncio.getAnuncioId(), Types.INTEGER);
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();

            String[] palabraClave = {"null"};
            String error = "";
            for (int i = 0; i < palabraClave.length; i++) {
                if (ex.getMessage().contains(palabraClave[i])) {
                    error = palabraClave[i];
                    break;
                }
            }
            switch (error) {
                case "null":
                    e.setMensajeUsuario("Es necesario rellenar todos los campos");
                    break;
                default:
                    e.setMensajeUsuario("Error general del sistema. Consulte con su administrador");
            }
            e.setMensajeAdmin(ex.getMessage());
            throw e;
        }
        return registrosAfectados;
    }

    //Podria hacer esta comprobacion en obtener anuncio
    /**
     * Devuelve un boolean si el anuncio se encuentra en la lista de favoritos
     * del usuario
     *
     * @param usuario
     * @throws ExcepcionRefind
     */
    public boolean comprobarFavorito(Usuario usuario, Anuncio anuncio) throws ExcepcionRefind {
        conectar();
        String sql = "SELECT * FROM favorito WHERE usuario_firebase='" + usuario.getUsuarioFirebase() + "' and anuncio_id=" + anuncio.getAnuncioId();
        boolean comprobar = false;
        try {
            conectar();
            Statement sentencia = conexion.createStatement();
            ResultSet res = sentencia.executeQuery(sql);
            while (res.next()) {
                comprobar = true;
            }
            res.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();
            e.setMensajeAdmin(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        return comprobar;
    }

    public ArrayList<Anuncio> obtenerFavoritos(Usuario usuario) throws ExcepcionRefind {
        conectar();
        ArrayList<Anuncio> listaAnuncios = new ArrayList();
        String sql = "SELECT a.anuncio_id, a.titulo, a.descripcion, a.telefono, a.foto FROM anuncio a, favorito f, usuario u WHERE a.anuncio_id = f.anuncio_id and f.usuario_firebase = u.usuario_firebase and u.usuario_firebase ='"+usuario.getUsuarioFirebase()+"'";

        try {
            conectar();
            Statement sentencia = conexion.createStatement();
            ResultSet res = sentencia.executeQuery(sql);
            while (res.next()) {
                Anuncio anuncio = new Anuncio();
                anuncio.setAnuncioId(res.getInt("anuncio_id"));
                anuncio.setTitulo(res.getString("titulo"));
                anuncio.setDescripcion(res.getString("descripcion"));
                anuncio.setTelefono(res.getString("telefono"));
                anuncio.setFoto(res.getString("foto"));

                listaAnuncios.add(anuncio);
            }
            res.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();
            e.setMensajeAdmin(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }

        return listaAnuncios;
    }

    /**
     * ------------- METODOS COMENTARIOS
     */
    public int insertarComentario(Comentario comentario) throws ExcepcionRefind {
        int registrosAfectados = 0;
        String dml = "INSERT INTO comentario(usuario_id, anuncio_id, texto) VALUES (?,?,?)";
        conectar();
        try {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(dml);
            sentenciaPreparada.setString(1, comentario.getUsuario().getUsuarioFirebase());
            sentenciaPreparada.setObject(2, comentario.getAnuncio().getAnuncioId(), Types.INTEGER);
            sentenciaPreparada.setString(3, comentario.getTexto());
            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();

        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();

            String[] palabraClave = {"null"};
            String error = "";
            for (int i = 0; i < palabraClave.length; i++) {
                if (ex.getMessage().contains(palabraClave[i])) {
                    error = palabraClave[i];
                    break;
                }
            }
            switch (error) {
                case "null":
                    e.setMensajeUsuario("Es necesario rellenar todos los campos");
                    break;
                default:
                    e.setMensajeUsuario("Error general del sistema. Consulte con su administrador");
            }
            e.setMensajeAdmin(ex.getMessage());
            throw e;
        }
        return registrosAfectados;
    }

    public int eliminarComentario(Comentario comentario) throws ExcepcionRefind {
        conectar();
        int registrosAfectados = 0;
        String sql = "DELETE FROM comentario WHERE COMENTARIO_ID = ?";
        try {
            PreparedStatement sentenciaPreparada = conexion.prepareStatement(sql);
            sentenciaPreparada.setObject(1, comentario.getComentarioId(), Types.INTEGER);

            registrosAfectados = sentenciaPreparada.executeUpdate();
            sentenciaPreparada.close();
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();
            e.setMensajeUsuario("Error general de eliminarComentario");
            throw e;
        }
        return registrosAfectados;
    }
    public ArrayList<Comentario> obtenerComentarios(Anuncio anuncioId) throws ExcepcionRefind {
        ArrayList<Comentario> listaComentario = new ArrayList();
        String sql = "SELECT c.comentario_id, c.usuario_id, c.anuncio_id, c.texto, "
                + "u.nombre, u.apellido, u.foto "
                + "FROM comentario c, usuario u, anuncio a "
                + "WHERE u.usuario_firebase = c.usuario_id "
                + "and c.anuncio_id = a.anuncio_id "
                + "and c.anuncio_id= " + anuncioId.getAnuncioId();
        try {
            conectar();
            Statement sentencia = conexion.createStatement();
            ResultSet res = sentencia.executeQuery(sql);
            while (res.next()) {

                Usuario usuario = new Usuario();
                usuario.setUsuarioFirebase(res.getString("c.usuario_id"));
                usuario.setNombre(res.getString("u.nombre"));
                usuario.setApellido(res.getString("u.apellido"));
                usuario.setFoto(res.getString("u.foto"));

                Anuncio anuncio = new Anuncio();
                anuncio.setAnuncioId(res.getInt("c.anuncio_id"));

                Comentario comentario = new Comentario();
                comentario.setUsuario(usuario);
                comentario.setAnuncio(anuncio);
                comentario.setComentarioId(res.getInt("c.comentario_id"));
                comentario.setTexto(res.getString("c.texto"));
                listaComentario.add(comentario);
            }

            res.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();
            /*e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(sql);*/
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }

        return listaComentario;
    }

    /**
     * ------------- METODOS ANUNCIOS
     */
    public Anuncio obtenerAnuncio(Anuncio anuncioId) throws ExcepcionRefind {
        conectar();
        Anuncio anuncio = new Anuncio();
        String sql = "SELECT * FROM anuncio a WHERE anuncio_id= " + anuncioId.getAnuncioId();

        try {
            conectar();
            Statement sentencia = conexion.createStatement();
            ResultSet res = sentencia.executeQuery(sql);
            while (res.next()) {
                anuncio.setAnuncioId(anuncioId.getAnuncioId());
                anuncio.setTitulo(res.getString("titulo"));
                anuncio.setDescripcion(res.getString("descripcion"));
                anuncio.setTelefono(res.getString("telefono"));
                anuncio.setFoto(res.getString("foto"));

                /*categoria.setCategoriaId(res.getInt("categori_id"));
                categoria.setTitulo(res.getString("titulo"));
                anuncio.setCategoria(categoria);
                
                usuario.setUsuarioFirebase(res.getString("usuario_firebase"));
                anuncio.setUsuario(usuario);*/
            }
            res.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();
            e.setMensajeAdmin(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
        }
        return anuncio;
    }

    public ArrayList<Anuncio> obtenerAnuncios(Categoria categoriaId) throws ExcepcionRefind {
        ArrayList<Anuncio> listaAnuncios = new ArrayList();
        String sql = "SELECT anuncio_id, titulo, descripcion, foto FROM `anuncio` WHERE categoria_id= " + categoriaId.getCategoriaId();
        try {
            conectar();
            Statement sentencia = conexion.createStatement();
            ResultSet res = sentencia.executeQuery(sql);
            Anuncio anuncio = null;
            while (res.next()) {
                anuncio = new Anuncio();
                anuncio.setAnuncioId(res.getInt("anuncio_id"));
                anuncio.setTitulo(res.getString("titulo"));
                anuncio.setDescripcion(res.getString("descripcion"));
                anuncio.setFoto(res.getString("foto"));
                listaAnuncios.add(anuncio);
            }

            res.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();
            /*e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(sql);*/
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
        }

        return listaAnuncios;
    }

    /**
     * ------------- METODOS CATEGORIAS
     */
    public ArrayList<Categoria> obtenerCategorias() throws ExcepcionRefind {
        ArrayList<Categoria> listaCategoria = new ArrayList();
        String sql = "SELECT categoria_id, titulo, descripcion, foto from categoria";
        try {
            conectar();
            Statement sentencia = conexion.createStatement();
            ResultSet res = sentencia.executeQuery(sql);
            Categoria categoria = null;
            while (res.next()) {
                categoria = new Categoria();
                categoria.setCategoriaId(res.getInt("categoria_id"));
                categoria.setTitulo(res.getString("titulo"));
                categoria.setDescripcion(res.getString("descripcion"));
                categoria.setFoto(res.getString("foto"));
                listaCategoria.add(categoria);
            }

            res.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException ex) {
            ExcepcionRefind e = new ExcepcionRefind();
            /*e.setMensajeErrorAdministrador(ex.getMessage());
            e.setCodigoError(ex.getErrorCode());
            e.setSentenciaSQL(sql);*/
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }

        return listaCategoria;
    }

}
