package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.Sucursal;
import modelo.pojo.respuesta.RespuestaSucursal;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class SucursalDAO{
    public static RespuestaSucursal obtenerSucursales(){
        RespuestaSucursal respuesta = new RespuestaSucursal();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Sucursal> sucursales = conexionBD.selectList("sucursales.obtenerSucursales");
                
                if(Verificaciones.Datos.listaNoVacia(sucursales)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(sucursales);
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
    
    public static RespuestaSucursal obtenerSucursalesPorNombre(String nombre){
        RespuestaSucursal respuesta = new RespuestaSucursal();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Sucursal> sucursales = conexionBD.selectList("sucursales.obtenerSucursalesPorNombre", nombre);
                
                if(Verificaciones.Datos.listaNoVacia(sucursales)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(sucursales);
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
    
    public static RespuestaSucursal obtenerSucursalPorIdSucursalOIdDireccion(String tipo, Integer id){
        RespuestaSucursal respuesta = new RespuestaSucursal();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Sucursal> sucursales = new ArrayList<>();
                sucursales.add(conexionBD.selectOne("sucursales.obtenerSucursalPor" + tipo, id));
                
                if(Verificaciones.Datos.listaNoVacia(sucursales)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(sucursales);
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
    
    public static RespuestaSucursal registrarSucursal(Sucursal sucursal){
        RespuestaSucursal respuesta = new RespuestaSucursal();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.insert("sucursales.registrarSucursal", sucursal);
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
    
    public static RespuestaSucursal modificarSucursal(Sucursal sucursal){
        RespuestaSucursal respuesta = new RespuestaSucursal();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNula(conexionBD.selectOne("sucursales.obtenerSucursalPorId", sucursal.getId()))){
                    int numFilasAfectadas = conexionBD.update("sucursales.modificarSucursal", sucursal);
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
    
    public static RespuestaSucursal eliminarSucursal(Integer idSucursal){
        RespuestaSucursal respuesta = new RespuestaSucursal();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.delete("sucursales.eliminarSucursal", idSucursal);
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