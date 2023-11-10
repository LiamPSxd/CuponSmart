package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.Estado;
import modelo.pojo.respuesta.RespuestaEstado;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class EstadoDAO{
    public static RespuestaEstado obtenerEstados(){
        RespuestaEstado respuesta = new RespuestaEstado();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Estado> estados = conexionBD.selectList("estados.obtenerEstados");
                
                if(Verificaciones.Datos.listaNoVacia(estados)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(estados);
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
    
    public static RespuestaEstado obtenerEstadoPorId(Integer idEstado){
        RespuestaEstado respuesta = new RespuestaEstado();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Estado> estados = new ArrayList<>();
                estados.add(conexionBD.selectOne("estados.obtenerEstadoPorId", idEstado));
                
                if(Verificaciones.Datos.listaNoVacia(estados)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(estados);
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
    
    public static RespuestaEstado registrarEstado(Estado estado){
        RespuestaEstado respuesta = new RespuestaEstado();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.insert("estados.registrarEstado", estado);
                conexionBD.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.REGISTRO);
                }else{
                    respuesta.setMensaje(Constantes.Errores.REGISTRO);
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
                respuesta.setMensaje(Constantes.Excepciones.EXCEPTION);
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }
        
        return respuesta;
    }
    
    public static RespuestaEstado modificarEstado(Estado estado){
        RespuestaEstado respuesta = new RespuestaEstado();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                Estado existe = conexionBD.selectOne("estados.obtenerEstadoPorNombre", estado.getNombre());
                
                if(Verificaciones.Datos.claseNula(existe)){
                    int numFilasAfectadas = conexionBD.update("estados.modificarEstado", estado);
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
    
    public static RespuestaEstado eliminarEstado(Integer idEstado){
        RespuestaEstado respuesta = new RespuestaEstado();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.delete("estados.eliminarEstado", idEstado);
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