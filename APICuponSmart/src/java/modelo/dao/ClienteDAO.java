package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.Cliente;
import modelo.pojo.respuesta.RespuestaCliente;
import mybatis.MyBatisUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class ClienteDAO{
    public static RespuestaCliente obtenerClientes(){
        RespuestaCliente respuesta = new RespuestaCliente();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                List<Cliente> clientes = conexionBD.selectList("clientes.obtenerClientes");
                
                if(Verificaciones.Datos.listaNoVacia(clientes)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(clientes);
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
    
    public static RespuestaCliente obtenerClientePorId(Integer idCliente){
        RespuestaCliente respuesta = new RespuestaCliente();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                List<Cliente> clientes = new ArrayList<>();
                clientes.add(conexionDB.selectOne("clientes.obtenerClientePorId", idCliente));
                
                if(Verificaciones.Datos.listaNoVacia(clientes)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(clientes);
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
    
    public static RespuestaCliente registrarCliente(Cliente cliente){
        RespuestaCliente respuesta = new RespuestaCliente();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                int numFilasAfectadas = conexionBD.insert("clientes.registrarCliente", cliente);
                conexionBD.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.REGISTRO);
                }else{
                    respuesta.setMensaje(Constantes.Errores.REGISTRO);
                }
            }catch(PersistenceException e){
                respuesta.setMensaje(Constantes.Excepciones.PERSISTENCE);
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
    
    public static RespuestaCliente modificarCliente(Cliente cliente){
        RespuestaCliente respuesta = new RespuestaCliente();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                int numFilasAfectadas = conexionDB.update("clientes.modificarCliente", cliente);
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
    
    public static RespuestaCliente eliminarCliente(Integer idCliente){
        RespuestaCliente respuesta = new RespuestaCliente();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                int numFilasAfectadas = conexionDB.delete("clientes.eliminarCliente", idCliente);
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