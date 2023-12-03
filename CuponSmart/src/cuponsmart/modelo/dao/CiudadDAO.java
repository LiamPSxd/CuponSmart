package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.entidad.Ciudad;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.RespuestaCiudad;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import utils.Constantes;
import utils.Verificaciones;

public class CiudadDAO{
    public static List<Ciudad> obtenerCiudades(){
        List<Ciudad> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.CIUDAD + "obtenerCiudades";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaCiudad peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaCiudad.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static List<Ciudad> obtenerCiudadesPorIdMunicipio(Integer idMunicipio){
        List<Ciudad> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.CIUDAD + "obtenerCiudadesPorIdMunicipio/" + idMunicipio;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaCiudad peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaCiudad.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static Ciudad obtenerCiudadPorId(Integer idCiudad){
        Ciudad respuesta = new Ciudad();
        
        String url = Constantes.Servicios.CIUDAD + "obtenerCiudadPorId/" + idCiudad;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
             RespuestaCiudad peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaCiudad.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarCiudad(Ciudad ciudad){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.CIUDAD + "registrar";
        String parametros = gson.toJson(ciudad);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.POST, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        
        return respuesta;
    }
    
    public static Mensaje modificarCiudad(Ciudad ciudad){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.CIUDAD + "modificar";
        String parametros = gson.toJson(ciudad);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.PUT, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.MODIFICACION);
        
        return respuesta;
    }
    
    public static Mensaje eliminarCiudad(Integer idCiudad){
        Mensaje respuesta = new Mensaje();
        
        String url = Constantes.Servicios.CIUDAD + "eliminar/" + idCiudad;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionDELETE(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.ELIMINACION);
        
        return respuesta;
    }
}