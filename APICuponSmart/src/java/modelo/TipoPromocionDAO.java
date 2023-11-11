package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.TipoPromocion;
import modelo.pojo.respuesta.RespuestaTipoPromocion;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class TipoPromocionDAO{
    public static RespuestaTipoPromocion obtenerTiposPromocion(){
        RespuestaTipoPromocion respuesta = new RespuestaTipoPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<TipoPromocion> tipospromocion = conexionBD.selectList("tipospromocion.obtenerTiposPromocion");
                
                if(Verificaciones.Datos.listaNoVacia(tipospromocion)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(tipospromocion);
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
    
    public static RespuestaTipoPromocion obtenerTipoPromocionPorId(Integer idTipoPromocion){
        RespuestaTipoPromocion respuesta = new RespuestaTipoPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<TipoPromocion> tipospromocion = new ArrayList<>();
                tipospromocion.add(conexionBD.selectOne("tipospromocion.obtenerTipoPromocionPorId", idTipoPromocion));
                
                if(Verificaciones.Datos.listaNoVacia(tipospromocion)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(tipospromocion);
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
    
    public static RespuestaTipoPromocion registrarTipoPromocion(TipoPromocion tipopromocion){
        RespuestaTipoPromocion respuesta = new RespuestaTipoPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.insert("tipospromocion.registrarTipoPromocion", tipopromocion);
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
    
    public static RespuestaTipoPromocion modificarTipoPromocion(TipoPromocion tipopromocion){
        RespuestaTipoPromocion respuesta = new RespuestaTipoPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNula(conexionBD.selectOne("tipospromocion.obtenerTipoPromocionPorTipo", tipopromocion.getTipo()))){
                    int numFilasAfectadas = conexionBD.update("tipospromocion.modificarTipoPromocion", tipopromocion);
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
    
    public static RespuestaTipoPromocion eliminarTipoPromocion(Integer idTipoPromocion){
        RespuestaTipoPromocion respuesta = new RespuestaTipoPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.delete("tipospromocion.eliminarTipoPromocion", idTipoPromocion);
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