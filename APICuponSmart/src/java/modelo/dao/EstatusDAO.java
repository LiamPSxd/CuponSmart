package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.Estatus;
import modelo.pojo.respuesta.RespuestaEstatus;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class EstatusDAO{
    public static RespuestaEstatus obtenerEstatus(){
        RespuestaEstatus respuesta = new RespuestaEstatus();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Estatus> estatus = conexionBD.selectList("estatus.obtenerEstatus");
                
                if(Verificaciones.Datos.listaNoVacia(estatus)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(estatus);
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
    
    public static RespuestaEstatus obtenerEstatusPorId(Integer idEstatus){
        RespuestaEstatus respuesta = new RespuestaEstatus();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Estatus> estatus = new ArrayList<>();
                estatus.add(conexionBD.selectOne("estatus.obtenerEstatusPorId", idEstatus));
                
                if(Verificaciones.Datos.listaNoVacia(estatus)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(estatus);
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
    
    public static RespuestaEstatus registrarEstatus(Estatus estatus){
        RespuestaEstatus respuesta = new RespuestaEstatus();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.insert("estatus.registrarEstatus", estatus);
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
    
    public static RespuestaEstatus modificarEstatus(Estatus estatus){
        RespuestaEstatus respuesta = new RespuestaEstatus();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNula(conexionBD.selectOne("estatus.obtenerEstatusPorEstado", estatus.getEstado()))){
                    int numFilasAfectadas = conexionBD.update("estatus.modificarEstatus", estatus);
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
    
    public static RespuestaEstatus eliminarEstatus(Integer idEstatus){
        RespuestaEstatus respuesta = new RespuestaEstatus();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.delete("estatus.eliminarEstatus", idEstatus);
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