package modelo.dao;

import java.util.List;
import modelo.pojo.entidad.PromocionSucursal;
import modelo.pojo.respuesta.RespuestaPromocionSucursal;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class PromocionSucursalDAO{
    public static RespuestaPromocionSucursal obtenerPromocionesSucursales(){
        RespuestaPromocionSucursal respuesta = new RespuestaPromocionSucursal();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<PromocionSucursal> promocionesSucursales = conexionBD.selectList("promocionesSucursales.obtenerPromocionesSucursales");
                
                if(Verificaciones.Datos.listaNoVacia(promocionesSucursales)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(promocionesSucursales);
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
    
    public static RespuestaPromocionSucursal obtenerPromocionesSucursalesPorIdPromocionOSucursal(String tipo, Integer id){
        RespuestaPromocionSucursal respuesta = new RespuestaPromocionSucursal();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<PromocionSucursal> promocionesSucursales = conexionBD.selectList("promocionesSucursales.obtenerPromocionesSucursalesPorId" + tipo, id);
                
                if(Verificaciones.Datos.listaNoVacia(promocionesSucursales)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(promocionesSucursales);
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
    
    public static RespuestaPromocionSucursal registrarPromocionSucursal(PromocionSucursal promocionSucursal){
        RespuestaPromocionSucursal respuesta = new RespuestaPromocionSucursal();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.insert("promocionesSucursales.registrarPromocionSucursal", promocionSucursal);
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
    
    public static RespuestaPromocionSucursal eliminarPromocionSucursal(PromocionSucursal promocionSucursal){
        RespuestaPromocionSucursal respuesta = new RespuestaPromocionSucursal();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.delete("promocionesSucursales.eliminarPromocionSucursal", promocionSucursal);
                conexionBD.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.ELIMINACION);
                }else{
                    respuesta.setMensaje(Constantes.Errores.ELIMINACION);
                }
            }catch(Exception e){
                respuesta.setMensaje(Constantes.Errores.ELIMINACION);
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }
        
        return respuesta;
    }
    
    public static RespuestaPromocionSucursal eliminarPromocionSucursales(Integer idPromocion){
        RespuestaPromocionSucursal respuesta = new RespuestaPromocionSucursal();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.delete("promocionesSucursales.eliminarPromocionSucursales", idPromocion);
                conexionBD.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.ELIMINACION);
                }else{
                    respuesta.setMensaje(Constantes.Errores.ELIMINACION);
                }
            }catch(Exception e){
                respuesta.setMensaje(Constantes.Errores.ELIMINACION);
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }
        
        return respuesta;
    }
}