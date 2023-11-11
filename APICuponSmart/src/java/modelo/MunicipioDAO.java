package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.Municipio;
import modelo.pojo.respuesta.RespuestaMunicipio;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class MunicipioDAO{
    public static RespuestaMunicipio obtenerMunicipios(){
        RespuestaMunicipio respuesta = new RespuestaMunicipio();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Municipio> municipios = conexionBD.selectList("municipios.obtenerMunicipios");
                
                if(Verificaciones.Datos.listaNoVacia(municipios)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(municipios);
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
    
    public static RespuestaMunicipio obtenerMunicipiosPorEstado(Integer idEstado){
        RespuestaMunicipio respuesta = new RespuestaMunicipio();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Municipio> municipios = conexionBD.selectList("municipios.obtenerMunicipiosPorEstado", idEstado);
                
                if(Verificaciones.Datos.listaNoVacia(municipios)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(municipios);
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
    
    public static RespuestaMunicipio obtenerMunicipioPorId(Integer idMunicipio){
        RespuestaMunicipio respuesta = new RespuestaMunicipio();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Municipio> municipios = new ArrayList<>();
                municipios.add(conexionBD.selectOne("municipios.obtenerMunicipioPorId", idMunicipio));
                
                if(Verificaciones.Datos.listaNoVacia(municipios)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(municipios);
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
    
    public static RespuestaMunicipio registrarMunicipio(Municipio municipio){
        RespuestaMunicipio respuesta = new RespuestaMunicipio();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.insert("municipios.registrarMunicipio", municipio);
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
    
    public static RespuestaMunicipio modificarMunicipio(Municipio municipio){
        RespuestaMunicipio respuesta = new RespuestaMunicipio();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNula(conexionBD.selectOne("municipios.obtenerMunicipioPorNombre", municipio.getNombre()))){
                    int numFilasAfectadas = conexionBD.update("municipios.modificarMunicipio", municipio);
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
    
    public static RespuestaMunicipio eliminarMunicipio(Integer idMunicipio){
        RespuestaMunicipio respuesta = new RespuestaMunicipio();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.delete("municipios.eliminarMunicipio", idMunicipio);
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