package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.entidad.Promocion;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.pojo.respuesta.RespuestaPromocion;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import utils.Constantes;
import utils.Verificaciones;

public class PromocionDAO{
    public static List<Promocion> obtenerPromociones(){
        List<Promocion> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.PROMOCION + "obtenerPromociones";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaPromocion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocion.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static List<Promocion> obtenerPromocionesPorFechaInicio(String fechaInicio){
        List<Promocion> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.PROMOCION + "obtenerPromocionesPorFechaInicio/" + fechaInicio;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaPromocion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocion.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static List<Promocion> obtenerPromocionesPorFechaTermino(String fechaTermino){
        List<Promocion> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.PROMOCION + "obtenerPromocionesPorFechaTermino/" + fechaTermino;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaPromocion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocion.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static List<Promocion> obtenerPromocionesPorNombre(String nombre){
        List<Promocion> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.PROMOCION + "obtenerPromocionesPorNombre/" + nombre;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaPromocion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocion.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static List<Promocion> obtenerPromocionesPorIdEmpresa(Integer idEmpresa){
        List<Promocion> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.PROMOCION + "obtenerPromocionesPorIdEmpresa/" + idEmpresa;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaPromocion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocion.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static Promocion obtenerPromocionPorId(Integer idPromocion){
        Promocion respuesta = null;
        
        String url = Constantes.Servicios.PROMOCION + "obtenerPromocionPorId/" + idPromocion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaPromocion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocion.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static Promocion obtenerPromocionPorCodigo(String codigoPromocion){
        Promocion respuesta = null;
        
        String url = Constantes.Servicios.PROMOCION + "obtenerPromocionPorCodigo/" + codigoPromocion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaPromocion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocion.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarPromocion(Promocion promocion){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.PROMOCION + "registrar";
        String parametros = gson.toJson(promocion);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.POST, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        
        return respuesta;
    }
    
    public static Mensaje modificarPromocion(Promocion promocion){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.PROMOCION + "modificar";
        String parametros = gson.toJson(promocion);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.PUT, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.MODIFICACION);
        
        return respuesta;
    }
    
    public static Mensaje eliminarPromocion(Integer idPromocion){
        Mensaje respuesta = new Mensaje();
        
        String url = Constantes.Servicios.PROMOCION + "eliminar/" + idPromocion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionDELETE(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.ELIMINACION);
        
        return respuesta;
    }
}