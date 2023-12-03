package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.entidad.PromocionSucursal;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.pojo.respuesta.RespuestaPromocionSucursal;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import utils.Constantes;
import utils.Verificaciones;

public class PromocionSucursalDAO{
    public static List<PromocionSucursal> obtenerPromocionesSucursales(){
        List<PromocionSucursal> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.PROMOCION_SUCURSAL + "obtenerPromocionesSucursales";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaPromocionSucursal peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocionSucursal.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static List<PromocionSucursal> obtenerPromocionesSucursalesPorIdPromocion(Integer idPromocion){
        List<PromocionSucursal> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.PROMOCION_SUCURSAL + "obtenerPromocionesSucursalesPorIdPromocion/" + idPromocion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaPromocionSucursal peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocionSucursal.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static List<PromocionSucursal> obtenerPromocionesSucursalesPorIdSucursal(Integer idSucursal){
        List<PromocionSucursal> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.PROMOCION_SUCURSAL + "obtenerPromocionesSucursalesPorIdSucursal/" + idSucursal;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaPromocionSucursal peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaPromocionSucursal.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarPromocionSucursal(PromocionSucursal promocionSucursal){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.PROMOCION_SUCURSAL + "registrar";
        String parametros = gson.toJson(promocionSucursal);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.POST, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        
        return respuesta;
    }
    
    public static Mensaje eliminarPromocionSucursal(PromocionSucursal promocionSucursal){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.PROMOCION_SUCURSAL + "eliminar";
        String parametros = gson.toJson(promocionSucursal);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.DELETE, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.ELIMINACION);
        
        return respuesta;
    }
}