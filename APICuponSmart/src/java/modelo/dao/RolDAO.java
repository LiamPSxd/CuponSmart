package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.Rol;
import modelo.pojo.respuesta.RespuestaRol;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class RolDAO{
    public static RespuestaRol obtenerRoles(){
        RespuestaRol respuesta = new RespuestaRol();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Rol> roles = conexionBD.selectList("roles.obtenerRoles");
                
                if(Verificaciones.Datos.listaNoVacia(roles)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(roles);
                }else{
                    respuesta.mensajeSinDatos();
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
    
    public static RespuestaRol obtenerRolPorId(Integer idRol){
        RespuestaRol respuesta = new RespuestaRol();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Rol> roles = new ArrayList<>();
                roles.add(conexionBD.selectOne("roles.obtenerRolPorId", idRol));
                
                if(Verificaciones.Datos.listaNoVacia(roles)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(roles);
                }else{
                    respuesta.mensajeNoId();
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
    
    public static RespuestaRol registrarRol(Rol rol){
        RespuestaRol respuesta = new RespuestaRol();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.insert("roles.registrarRol", rol);
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
    
    public static RespuestaRol modificarRol(Rol rol){
        RespuestaRol respuesta = new RespuestaRol();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNula(conexionBD.selectOne("roles.obtenerRolPorNombre", rol.getNombre()))){
                    int numFilasAfectadas = conexionBD.update("roles.modificarRol", rol);
                    conexionBD.commit();
                    
                    if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                        respuesta.setError(false);
                        respuesta.setMensaje(Constantes.Retornos.MODIFICACION);
                    }else{
                        respuesta.setMensaje(Constantes.Errores.MODIFICACION);
                    }
                }else{
                    respuesta.mensajeNoId();
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
    
    public static RespuestaRol eliminarRol(Integer idRol){
        RespuestaRol respuesta = new RespuestaRol();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.delete("roles.eliminarRol", idRol);
                conexionBD.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.ELIMINACION);
                }else{
                    respuesta.setMensaje(Constantes.Errores.ELIMINACION);
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
}