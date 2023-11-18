package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.pojo.entidad.Empresa;
import modelo.pojo.respuesta.RespuestaEmpresa;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class EmpresaDAO {
    public static RespuestaEmpresa obtenerEmpresas(){
        RespuestaEmpresa respuesta = new RespuestaEmpresa();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try {
                List<Empresa> empresas = conexionDB.selectList("empresas.obtenerEmpresas");
                if(Verificaciones.Datos.listaNoVacia(empresas)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(empresas);
                }else{
                    respuesta.mensajeSinDatos();
                }
            } catch (Exception e) {
                respuesta.setMensaje(Constantes.Excepciones.EXCEPTION);
            }finally{
                conexionDB.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }        
        return respuesta;
    }
    
    public static RespuestaEmpresa obtenerEmpresaPorId(Integer id){
        RespuestaEmpresa respuesta = new RespuestaEmpresa();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try {
                List<Empresa> empresas = new ArrayList<>();
                empresas.add(conexionDB.selectOne("empresas.obtenerEmpresaPorId", id));                
                if(Verificaciones.Datos.listaNoVacia(empresas)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(empresas);
                }else{
                    respuesta.mensajeNoId();
                }
            } catch (Exception e) {
                respuesta.setMensaje(Constantes.Excepciones.EXCEPTION);
            }finally{
                conexionDB.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }        
        return respuesta;
    }
    
    public static RespuestaEmpresa obtenerEmpresaPorCadena(String criterio, String parametro){
        RespuestaEmpresa respuesta = new RespuestaEmpresa();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try {
                List<Empresa> empresas = new ArrayList<>();
                empresas.add(conexionDB.selectOne("empresas.obtenerEmpresaPor"+criterio, parametro));          
                if(Verificaciones.Datos.listaNoVacia(empresas)){
                    respuesta.setError(false);
                    respuesta.mensajeSuccess();
                    respuesta.setContenido(empresas);
                }else{
                    respuesta.mensajeNoId();
                }
            } catch (Exception e) {
                respuesta.setMensaje(Constantes.Excepciones.EXCEPTION);
            }finally{
                conexionDB.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }        
        return respuesta;
    }
    
    public static RespuestaEmpresa registrarEmpresa(Empresa cliente){
        RespuestaEmpresa respuesta = new RespuestaEmpresa();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try {
                int numFilasAfectadas = conexionBD.insert("empresas.registrarEmpresa", cliente);
                conexionBD.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);                    
                    respuesta.setMensaje(Constantes.Retornos.REGISTRO);
                }else{
                    respuesta.setMensaje(Constantes.Errores.REGISTRO);
                }
            } catch (Exception e) {                
                respuesta.setMensaje(Constantes.Excepciones.EXCEPTION);
            }finally{
                conexionBD.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }        
        return respuesta;
    }
    
    public static RespuestaEmpresa modificarEmpresa(Empresa cliente){
        RespuestaEmpresa respuesta = new RespuestaEmpresa();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try {
                int numFilasAfectadas = conexionDB.update("empresas.modificarEmpresa", cliente);
                conexionDB.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.MODIFICACION);                    
                }else{
                    respuesta.setMensaje(Constantes.Errores.MODIFICACION);
                }                
            } catch (Exception e) {
                respuesta.setMensaje(e.getMessage());
            }finally{
                conexionDB.close();
            }
        }else{
            respuesta.mensajeSinConexionBD();
        }        
        return respuesta;
    }
    
    public static RespuestaEmpresa eliminarEmpresa(Integer idEmpresa){
        RespuestaEmpresa respuesta = new RespuestaEmpresa();
        
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try {
                int numFilasAfectadas = conexionDB.delete("empresas.eliminarEmpresa", idEmpresa);
                conexionDB.commit();
                
                if(Verificaciones.Datos.numerico(numFilasAfectadas)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.ELIMINACION);                    
                }else{
                    respuesta.setMensaje(Constantes.Errores.ELIMINACION);
                }                
            } catch (Exception e) {
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
