package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.entidad.Usuario;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.pojo.respuesta.RespuestaUsuario;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import utils.Constantes;
import utils.Verificaciones;

public class UsuarioDAO{
    public static List<Usuario> obtenerUsuarios(){
        List<Usuario> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.USUARIO + "obtenerUsuarios";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaUsuario peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaUsuario.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido();
            }
        }
        
        return respuesta;
    }
    
    public static List<Usuario> obtenerUsuariosPorIdRol(Integer idRol){
        List<Usuario> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.USUARIO + "obtenerUsuariosPorIdRol/" + idRol;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaUsuario peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaUsuario.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido();
            }
        }
        
        return respuesta;
    }
    
    public static Usuario obtenerUsuarioPorId(Integer idUsuario){
        Usuario respuesta = new Usuario();
        
        String url = Constantes.Servicios.USUARIO + "obtenerUsuarioPorId/" + idUsuario;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaUsuario peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaUsuario.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido().get(0);
            }
        }
        
        return respuesta;
    }
    
    public static Usuario obtenerUsuarioPorNombre(String nombre){
        Usuario respuesta = new Usuario();
        
        String url = Constantes.Servicios.USUARIO + "obtenerUsuarioPorNombre/" + nombre;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaUsuario peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaUsuario.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido().get(0);
            }
        }
        
        return respuesta;
    }
    
    public static Usuario obtenerUsuarioPorUsername(String username){
        Usuario respuesta = new Usuario();
        
        String url = Constantes.Servicios.USUARIO + "obtenerUsuarioPorUsername/" + username;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaUsuario peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaUsuario.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido().get(0);
            }
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarUsuario(Usuario usuario){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.USUARIO + "registrar";
        String parametros = gson.toJson(usuario);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.POST, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        }
        
        return respuesta;
    }
    
    public static Mensaje modificarUsuario(Usuario usuario){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.USUARIO + "modificar";
        String parametros = gson.toJson(usuario);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.PUT, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            respuesta.setMensaje(Constantes.Errores.MODIFICACION);
        }
        
        return respuesta;
    }
    
    public static Mensaje eliminarUsuario(Integer idUsuario){
        Mensaje respuesta = new Mensaje();
        
        String url = Constantes.Servicios.USUARIO + "eliminar/" + idUsuario;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionDELETE(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            respuesta.setMensaje(Constantes.Errores.ELIMINACION);
        }
        
        return respuesta;
    }
}