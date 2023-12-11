package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.entidad.Estado;
import cuponsmart.modelo.pojo.entidad.Estatus;
import cuponsmart.modelo.pojo.entidad.Municipio;
import cuponsmart.modelo.pojo.entidad.Rol;
import cuponsmart.modelo.pojo.entidad.TipoPromocion;
import cuponsmart.modelo.pojo.respuesta.RespuestaEstado;
import cuponsmart.modelo.pojo.respuesta.RespuestaEstatus;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.pojo.respuesta.RespuestaMunicipio;
import cuponsmart.modelo.pojo.respuesta.RespuestaRol;
import cuponsmart.modelo.pojo.respuesta.RespuestaTipoPromocion;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import utils.Constantes;
import utils.Verificaciones;

public class CatalogoDAO{
    public static List<Estado> obtenerEstados(){
        List<Estado> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.CATALOGO + "obtenerEstados";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaEstado peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaEstado.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static Estado obtenerEstadoPorId(Integer idEstado){
        Estado respuesta = null;
        
        String url = Constantes.Servicios.CATALOGO + "obtenerEstadoPorId/" + idEstado;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaEstado peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaEstado.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static List<Estatus> obtenerEstatus(){
        List<Estatus> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.CATALOGO + "obtenerEstatus";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaEstatus peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaEstatus.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static Estatus obtenerEstatusPorId(Integer idEstatus){
        Estatus respuesta = null;
        
        String url = Constantes.Servicios.CATALOGO + "obtenerEstatusPorId/" + idEstatus;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaEstatus peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaEstatus.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static List<Rol> obtenerRoles(){
        List<Rol> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.CATALOGO + "obtenerRoles";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaRol peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaRol.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static Rol obtenerRolPorId(Integer idRol){
        Rol respuesta = null;
        
        String url = Constantes.Servicios.CATALOGO + "obtenerRolPorId/" + idRol;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaRol peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaRol.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static List<TipoPromocion> obtenerTiposPromocion(){
        List<TipoPromocion> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.CATALOGO + "obtenerTiposPromocion";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaTipoPromocion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaTipoPromocion.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static TipoPromocion obtenerTipoPromocionPorId(Integer idTipoPromocion){
        TipoPromocion respuesta = null;
        
        String url = Constantes.Servicios.CATALOGO + "obtenerTipoPromocionPorId/" + idTipoPromocion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaTipoPromocion peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaTipoPromocion.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static List<Municipio> obtenerMunicipios(){
        List<Municipio> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.CATALOGO + "obtenerMunicipios";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaMunicipio peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaMunicipio.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static Municipio obtenerMunicipioPorId(Integer idMunicipio){
        Municipio respuesta = null;
        
        String url = Constantes.Servicios.CATALOGO + "obtenerMunicipioPorId/" + idMunicipio;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaMunicipio peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaMunicipio.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static List<Municipio> obtenerMunicipiosPorEstado(Integer idEstado){
        List<Municipio> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.CATALOGO + "obtenerMunicipiosPorEstado/" + idEstado;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaMunicipio peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaMunicipio.class);
            
            if(!peticion.getError() && Verificaciones.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
}