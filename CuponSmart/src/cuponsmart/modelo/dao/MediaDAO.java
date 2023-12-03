package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.entidad.Empresa;
import cuponsmart.modelo.pojo.entidad.Promocion;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.RespuestaEmpresa;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.pojo.respuesta.RespuestaPromocion;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import utils.Constantes;
import utils.Verificaciones;

public class MediaDAO{
    public static Empresa obtenerLogoEmpresa(Integer idEmpresa){
        Empresa respuesta = new Empresa();
        
        String url = Constantes.Servicios.MEDIA + "obtenerLogoEmpresa/" + idEmpresa;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaEmpresa peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaEmpresa.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarLogoEmpresa(Integer idEmpresa, byte[] logo){
        Mensaje respuesta = new Mensaje();
        
        String url = Constantes.Servicios.MEDIA + "registrarLogoEmpresa/" + idEmpresa;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPUTMedia(url, logo);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        
        return respuesta;
    }
    
    public static Promocion obtenerImagenPromocion(Integer idPromocion){
        Promocion respuesta = new Promocion();
        
        String url = Constantes.Servicios.MEDIA + "obtenerImagenPromocion/" + idPromocion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaPromocion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocion.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarImagenPromocion(Integer idPromocion, byte[] imagen){
        Mensaje respuesta = new Mensaje();
        
        String url = Constantes.Servicios.MEDIA + "registrarImagenPromocion/" + idPromocion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPUTMedia(url, imagen);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        
        return respuesta;
    }
}