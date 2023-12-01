package modelo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import modelo.pojo.entidad.Cliente;
import modelo.pojo.entidad.Usuario;
import modelo.pojo.respuesta.RespuestaCliente;
import modelo.pojo.respuesta.RespuestaUsuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import utils.Constantes;
import utils.Verificaciones;

public class AutenticacionDAO{
    public static RespuestaUsuario verificarLoginEscritorio(String username, String contrasenia){
        RespuestaUsuario respuesta = new RespuestaUsuario();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("username", username);
                parametros.put("contrasenia", contrasenia);
                
                List<Usuario> usuarioSesion = new ArrayList<>();
                usuarioSesion.add(conexionBD.selectOne("autenticacion.loginEscritorio", parametros));
                
                if(Verificaciones.Datos.listaNoVacia(usuarioSesion)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.bienvenido(
                            usuarioSesion.get(0).getNombre(),
                            true
                    ));
                    respuesta.setContenido(usuarioSesion);
                }else{
                    respuesta.setMensaje(Constantes.Errores.CREDENCIALES_ADMIN);
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
    
    public static RespuestaCliente verificarLoginMovil(String correo, String contrasenia){
        RespuestaCliente respuesta = new RespuestaCliente();
        
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD != null){
            try{
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("correo", correo);
                parametros.put("contrasenia", contrasenia);
                
                List<Cliente> clienteSesion = new ArrayList<>();
                clienteSesion.add(conexionBD.selectOne("autenticacion.loginMovil", parametros));
                
                if(Verificaciones.Datos.listaNoVacia(clienteSesion)){
                    respuesta.setError(false);
                    respuesta.setMensaje(Constantes.Retornos.bienvenido(
                            clienteSesion.get(0).getNombre(),
                            false
                    ));
                    respuesta.setContenido(clienteSesion);
                }else{
                    respuesta.setMensaje(Constantes.Errores.CREDENCIALES_CLIENTE);
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