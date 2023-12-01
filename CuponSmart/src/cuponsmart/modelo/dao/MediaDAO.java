package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.RespuestaEmpresa;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.pojo.respuesta.RespuestaPromocion;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import utils.Constantes;

public class MediaDAO{
    public static RespuestaEmpresa obtenerLogoEmpresa(Integer idEmpresa){
        RespuestaEmpresa respuesta = new RespuestaEmpresa();
        
        String url = Constantes.Servicios.MEDIA + "obtenerLogoEmpresa/" + idEmpresa;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaEmpresa.class);
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarLogoEmpresa(Integer idEmpresa, byte[] logo){
        Mensaje respuesta = new Mensaje();
        
        String url = Constantes.Servicios.MEDIA + "registrarLogoEmpresa/" + idEmpresa;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPUTMedia(url, logo);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        }
        
        return respuesta;
    }
    
    public static RespuestaPromocion obtenerImagenPromocion(Integer idPromocion){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        String url = Constantes.Servicios.MEDIA + "obtenerImagenPromocion/" + idPromocion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocion.class);
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarImagenPromocion(Integer idPromocion, byte[] imagen){
        Mensaje respuesta = new Mensaje();
        
        String url = Constantes.Servicios.MEDIA + "registrarImagenPromocion/" + idPromocion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPUTMedia(url, imagen);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        }
        
        return respuesta;
    }
}