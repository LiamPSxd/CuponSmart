package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.Usuario;
import modelo.pojo.respuesta.RespuestaUsuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class UsuarioDAO{
    public static RespuestaUsuario obtenerUsuarios(){
        RespuestaUsuario respuesta = new RespuestaUsuario();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                List<Usuario> usuarios = conexionDB.selectList("usuarios.obtenerUsuarios");
                
                if(Verificaciones.Datos.listaNoVacia(usuarios)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(usuarios);
                }else{
                    respuesta.mensajeSinDatos();
                }
            }catch(Exception e){
                respuesta.setMensaje(Constantes.Excepciones.EXCEPTION);
            }finally{
                conexionDB.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }
        
        return respuesta;
    }
    
    public static RespuestaUsuario obtenerUsuariosPorIdRol(Integer idRol){
        RespuestaUsuario respuesta = new RespuestaUsuario();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                List<Usuario> usuarios = conexionDB.selectList("usuarios.obtenerUsuariosPorIdRol", idRol);
                
                if(Verificaciones.Datos.listaNoVacia(usuarios)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(usuarios);
                }else{
                    respuesta.mensajeSinDatos();
                }
            }catch(Exception e){
                respuesta.setMensaje(Constantes.Excepciones.EXCEPTION);
            }finally{
                conexionDB.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }
        
        return respuesta;
    }
    
    public static RespuestaUsuario obtenerUsuarioPorId(Integer idUsuario){
        RespuestaUsuario respuesta = new RespuestaUsuario();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                List<Usuario> usuarios = new ArrayList<>();
                usuarios.add(conexionDB.selectOne("usuarios.obtenerUsuarioPorId", idUsuario));
                
                if(Verificaciones.Datos.listaNoVacia(usuarios)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(usuarios);
                }else{
                    respuesta.mensajeNoId();
                }
            }catch(Exception e){
                respuesta.setMensaje(Constantes.Excepciones.EXCEPTION);
            }finally{
                conexionDB.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }
        
        return respuesta;
    }
    
    public static RespuestaUsuario obtenerUsuarioPorCadena(String criterio, String parametro){
        RespuestaUsuario respuesta = new RespuestaUsuario();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                List<Usuario> usuarios = new ArrayList<>();
                usuarios.add(conexionDB.selectOne("usuarios.obtenerUsuarioPor" + criterio, parametro));
                
                if(Verificaciones.Datos.listaNoVacia(usuarios)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(usuarios);
                }else{
                    respuesta.mensajeNoId();
                }
            }catch(Exception e){
                respuesta.setMensaje(Constantes.Excepciones.EXCEPTION);
            }finally{
                conexionDB.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }
        
        return respuesta;
    }
    
    public static RespuestaUsuario registrarUsuario(Usuario cliente){
        RespuestaUsuario respuesta = new RespuestaUsuario();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.insert("usuarios.registrarUsuario", cliente);
                conexionBD.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.REGISTRO);
                }else{
                    respuesta.setMensaje(Constantes.Errores.REGISTRO);
                }
            }catch(Exception e){
                respuesta.setMensaje(Constantes.Excepciones.EXCEPTION);
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }
        
        return respuesta;
    }
    
    public static RespuestaUsuario modificarUsuario(Usuario cliente){
        RespuestaUsuario respuesta = new RespuestaUsuario();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                int numFilasAfectadas = conexionDB.update("usuarios.modificarUsuario", cliente);
                conexionDB.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.MODIFICACION);
                }else{
                    respuesta.setMensaje(Constantes.Errores.MODIFICACION);
                }
            }catch(Exception e){
                respuesta.setMensaje(e.getMessage());
            }finally{
                conexionDB.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }
        
        return respuesta;
    }
    
    public static RespuestaUsuario eliminarUsuario(Integer idUsuario){
        RespuestaUsuario respuesta = new RespuestaUsuario();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                int numFilasAfectadas = conexionDB.delete("usuarios.eliminarUsuario", idUsuario);
                conexionDB.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.ELIMINACION);
                }else{
                    respuesta.setMensaje(Constantes.Errores.ELIMINACION);
                }
            }catch(Exception e){
                respuesta.setMensaje(Constantes.Excepciones.EXCEPTION);
            }finally{
                conexionDB.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }
        
        return respuesta;
    }
}