package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.Direccion;
import modelo.pojo.respuesta.RespuestaDireccion;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class DireccionDAO{
    public static RespuestaDireccion obtenerDirecciones(){
        RespuestaDireccion respuesta = new RespuestaDireccion();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
       
        if(conexionDB != null){
            try{
                List<Direccion> direcciones = conexionDB.selectList("direcciones.obtenerDirecciones");
                
                if(Verificaciones.Datos.listaNoVacia(direcciones)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(direcciones);
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
    
    public static RespuestaDireccion obtenerDireccionesPorCalleColoniaNumero(String busqueda){
        RespuestaDireccion respuesta = new RespuestaDireccion();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
       
        if(conexionDB != null){
            try{
                List<Direccion> direcciones = conexionDB.selectList("direcciones.obtenerDireccionesPorCalleColoniaNumero", busqueda);
                
                if(Verificaciones.Datos.listaNoVacia(direcciones)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(direcciones);
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
    
    public static RespuestaDireccion obtenerDireccionPorId(Integer idDireccion){
        RespuestaDireccion respuesta = new RespuestaDireccion();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                List<Direccion> direcciones = new ArrayList<>();
                direcciones.add(conexionDB.selectOne("direcciones.obtenerDireccionPorId", idDireccion));
                
                if(Verificaciones.Datos.listaNoVacia(direcciones)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(direcciones);
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
    
    public static RespuestaDireccion registrarDireccion(Direccion direccion){
        RespuestaDireccion respuesta = new RespuestaDireccion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.insert("direcciones.registrarDireccion", direccion);
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
    
    public static RespuestaDireccion modificarDireccion(Direccion direccion){
        RespuestaDireccion respuesta = new RespuestaDireccion();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                int numFilasAfectadas = conexionDB.update("direcciones.modificarDireccion", direccion);
                conexionDB.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.MODIFICACION);
                }else{
                    respuesta.setMensaje(Constantes.Errores.MODIFICACION);
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
    
    public static RespuestaDireccion eliminarDireccion(Integer idDireccion){
        RespuestaDireccion respuesta = new RespuestaDireccion();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                int numFilasAfectadas = conexionDB.delete("direcciones.eliminarDireccion", idDireccion);
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