package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.Promocion;
import modelo.pojo.respuesta.RespuestaPromocion;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class PromocionDAO{
    public static RespuestaPromocion obtenerPromociones(){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Promocion> promociones = conexionBD.selectList("promociones.obtenerPromociones");
                
                if(Verificaciones.Datos.listaNoVacia(promociones)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(promociones);
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
    
    public static RespuestaPromocion obtenerCupones(){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Promocion> promociones = conexionBD.selectList("promociones.obtenerCupones");
                
                if(Verificaciones.Datos.listaNoVacia(promociones)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(promociones);
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
    
    public static RespuestaPromocion obtenerPromocionesPorFechaInicioTerminoONombre(String tipo, String param){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Promocion> promociones = conexionBD.selectList("promociones.obtenerPromocionesPor" + tipo, param);
                
                if(Verificaciones.Datos.listaNoVacia(promociones)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(promociones);
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
    
    public static RespuestaPromocion obtenerPromocionesPorIdEmpresa(Integer idEmpresa){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Promocion> promociones = conexionBD.selectList("promociones.obtenerPromocionesPorIdEmpresa", idEmpresa);
                
                if(Verificaciones.Datos.listaNoVacia(promociones)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(promociones);
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
    
    public static RespuestaPromocion obtenerPromocionPorId(Integer idPromocion){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Promocion> promociones = new ArrayList<>();
                promociones.add(conexionBD.selectOne("promociones.obtenerPromocionPorId", idPromocion));
                
                if(Verificaciones.Datos.listaNoVacia(promociones)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(promociones);
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
    
    public static RespuestaPromocion obtenerPromocionPorCodigo(String codigoPromocion){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Promocion> promociones = new ArrayList<>();
                promociones.add(conexionBD.selectOne("promociones.obtenerPromocionPorCodigo", codigoPromocion));
                
                if(Verificaciones.Datos.listaNoVacia(promociones)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(promociones);
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
    
    public static RespuestaPromocion registrarPromocion(Promocion promocion){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNula(conexionBD.selectOne("promociones.obtenerPromocionPorCodigo", promocion.getCodigo()))){
                    int numFilasAfectadas = conexionBD.insert("promociones.registrarPromocion", promocion);
                    conexionBD.commit();

                    if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                        respuesta.setError(false);
                        respuesta.setMensaje(Constantes.Retornos.REGISTRO);
                    }else{
                        respuesta.setMensaje(Constantes.Errores.REGISTRO);
                    }
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
    
    public static RespuestaPromocion modificarPromocion(Promocion promocion){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNoNula(conexionBD.selectOne("promociones.obtenerPromocionPorId", promocion.getId()))){
                    int numFilasAfectadas = conexionBD.update("promociones.modificarPromocion", promocion);
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
    
    public static RespuestaPromocion eliminarPromocion(Integer idPromocion){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.delete("promociones.eliminarPromocion", idPromocion);
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