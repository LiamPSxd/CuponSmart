package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.Ciudad;
import modelo.pojo.respuesta.RespuestaCiudad;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class CiudadDAO{
    public static RespuestaCiudad obtenerCiudades(){
        RespuestaCiudad respuesta = new RespuestaCiudad();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Ciudad> ciudades = conexionBD.selectList("ciudades.obtenerCiudades");
                
                if(Verificaciones.Datos.listaNoVacia(ciudades)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(ciudades);
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
    
    public static RespuestaCiudad obtenerCiudadPorId(Integer idCiudad){
        RespuestaCiudad respuesta = new RespuestaCiudad();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Ciudad> ciudades = new ArrayList<>();
                ciudades.add(conexionBD.selectOne("ciudades.obtenerCiudadPorId", idCiudad));
                
                if(Verificaciones.Datos.listaNoVacia(ciudades)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(ciudades);
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
    
    public static RespuestaCiudad registrarCiudad(Ciudad ciudad){
        RespuestaCiudad respuesta = new RespuestaCiudad();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.insert("ciudades.registrarCiudad", ciudad);
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
    
    public static RespuestaCiudad modificarCiudad(Ciudad ciudad){
        RespuestaCiudad respuesta = new RespuestaCiudad();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNula(conexionBD.selectOne("ciudades.obtenerCiudadPorNombreYIdMunicipio", ciudad))){
                    int numFilasAfectadas = conexionBD.update("ciudades.modificarCiudad", ciudad);
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
    
    public static RespuestaCiudad eliminarCiudad(Integer idCiudad){
        RespuestaCiudad respuesta = new RespuestaCiudad();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.delete("ciudades.eliminarCiudad", idCiudad);
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