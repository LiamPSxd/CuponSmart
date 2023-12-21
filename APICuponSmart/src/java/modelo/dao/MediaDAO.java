package modelo.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import modelo.pojo.entidad.Cliente;
import modelo.pojo.entidad.Empresa;
import modelo.pojo.entidad.Promocion;
import modelo.pojo.respuesta.RespuestaCliente;
import modelo.pojo.respuesta.RespuestaEmpresa;
import modelo.pojo.respuesta.RespuestaPromocion;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class MediaDAO{
    public static RespuestaEmpresa obtenerLogoEmpresa(Integer idEmpresa){
        RespuestaEmpresa respuesta = new RespuestaEmpresa();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNoNula(conexionBD.selectOne("empresas.obtenerEmpresaPorId", idEmpresa))){
                    List<Empresa> empresa = new ArrayList<>();
                    empresa.add(conexionBD.selectOne("media.obtenerLogoEmpresa", idEmpresa));
                    
                    if(Verificaciones.Datos.listaNoVacia(empresa)){
                        respuesta.setError(false);
                        respuesta.mensajeSuccess();
                        respuesta.setContenido(empresa);
                    }else{
                        respuesta.mensajeSinDatos();
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
    
    public static RespuestaEmpresa registrarLogoEmpresa(Integer idEmpresa, byte[] logo){
        RespuestaEmpresa respuesta = new RespuestaEmpresa();
        
        LinkedHashMap<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("idEmpresa", idEmpresa);
        parametros.put("logo", logo);
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNoNula(conexionBD.selectOne("empresas.obtenerEmpresaPorId", idEmpresa))){
                    int numFilasAfectadas = conexionBD.update("media.registrarLogoEmpresa", parametros);
                    conexionBD.commit();
                    
                    if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                        respuesta.setError(false);
                        respuesta.setMensaje(Constantes.Retornos.REGISTRO);
                    }else{
                        respuesta.setMensaje(Constantes.Errores.REGISTRO);
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
    
    public static RespuestaPromocion obtenerImagenPromocion(Integer idPromocion){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNoNula(conexionBD.selectOne("promociones.obtenerPromocionPorId", idPromocion))){
                    List<Promocion> promocion = new ArrayList<>();
                    promocion.add(conexionBD.selectOne("media.obtenerImagenPromocion", idPromocion));
                    
                    if(Verificaciones.Datos.listaNoVacia(promocion)){
                        respuesta.setError(false);
                        respuesta.mensajeSuccess();
                        respuesta.setContenido(promocion);
                    }else{
                        respuesta.mensajeSinDatos();
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
    
    public static RespuestaPromocion registrarImagenPromocion(Integer idPromocion, byte[] imagen){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        LinkedHashMap<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("idPromocion", idPromocion);
        parametros.put("imagen", imagen);
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNoNula(conexionBD.selectOne("promociones.obtenerPromocionPorId", idPromocion))){
                    int numFilasAfectadas = conexionBD.update("media.registrarImagenPromocion", parametros);
                    conexionBD.commit();
                    
                    if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                        respuesta.setError(false);
                        respuesta.setMensaje(Constantes.Retornos.REGISTRO);
                    }else{
                        respuesta.setMensaje(Constantes.Errores.REGISTRO);
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
    
    public static RespuestaCliente obtenerFotoCliente(Integer idCliente){
        RespuestaCliente respuesta = new RespuestaCliente();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNoNula(conexionBD.selectOne("clientes.obtenerClientePorId", idCliente))){
                    List<Cliente> cliente = new ArrayList<>();
                    cliente.add(conexionBD.selectOne("media.obtenerFotoCliente", idCliente));
                    
                    if(Verificaciones.Datos.listaNoVacia(cliente)){
                        respuesta.setError(false);
                        respuesta.mensajeSuccess();
                        respuesta.setContenido(cliente);
                    }else{
                        respuesta.mensajeSinDatos();
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
    
    public static RespuestaCliente registrarFotoCliente(Integer idCliente, byte[] foto){
        RespuestaCliente respuesta = new RespuestaCliente();
        
        LinkedHashMap<String, Object> parametros = new LinkedHashMap<>();
        parametros.put("idCliente", idCliente);
        parametros.put("foto", foto);
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                if(Verificaciones.Datos.claseNoNula(conexionBD.selectOne("clientes.obtenerClientePorId", idCliente))){
                    int numFilasAfectadas = conexionBD.update("media.registrarFotoCliente", parametros);
                    conexionBD.commit();
                    
                    if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                        respuesta.setError(false);
                        respuesta.setMensaje(Constantes.Retornos.REGISTRO);
                    }else{
                        respuesta.setMensaje(Constantes.Errores.REGISTRO);
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
}