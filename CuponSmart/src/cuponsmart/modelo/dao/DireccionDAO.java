package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.entidad.Direccion;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.RespuestaDireccion;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import utils.Constantes;
import utils.Verificaciones;

public class DireccionDAO{
    public static List<Direccion> obtenerDirecciones(){
        List<Direccion> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.DIRECCION + "obtenerDirecciones";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaDireccion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaDireccion.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido();
            }
        }
        
        return respuesta;
    }
    
    public static List<Direccion> obtenerDireccionesPorCalleColoniaNumero(String busqueda){
        List<Direccion> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.DIRECCION + "obtenerDireccionesPorCalleColoniaNumero/" + busqueda;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaDireccion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaDireccion.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido();
            }
        }
        
        return respuesta;
    }
    
    public static Direccion obtenerDireccionPorId(Integer idDireccion){
        Direccion respuesta = new Direccion();
        
        String url = Constantes.Servicios.DIRECCION + "obtenerDireccionPorId/" + idDireccion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaDireccion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaDireccion.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido().get(0);
            }
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarDireccion(Direccion direccion){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.DIRECCION + "registrar";
        String parametros = gson.toJson(direccion);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.POST, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        }
        
        return respuesta;
    }
    
    public static Mensaje modificarDireccion(Direccion direccion){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.DIRECCION + "modificar";
        String parametros = gson.toJson(direccion);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.PUT, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            respuesta.setMensaje(Constantes.Errores.MODIFICACION);
        }
        
        return respuesta;
    }
    
    public static Mensaje eliminarDireccion(Integer idDireccion){
        Mensaje respuesta = new Mensaje();
        
        String url = Constantes.Servicios.DIRECCION + "eliminar/" + idDireccion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionDELETE(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            respuesta.setMensaje(Constantes.Errores.ELIMINACION);
        }
        
        return respuesta;
    }
}