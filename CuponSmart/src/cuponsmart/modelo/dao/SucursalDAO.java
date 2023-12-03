package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.entidad.Sucursal;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.pojo.respuesta.RespuestaSucursal;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import utils.Constantes;
import utils.Verificaciones;

public class SucursalDAO{
    public static List<Sucursal> obtenerSucursales(){
        List<Sucursal> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.SUCURSAL + "obtenerSucursales";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaSucursal peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaSucursal.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static List<Sucursal> obtenerSucursalesPorNombre(String nombre){
        List<Sucursal> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.SUCURSAL + "obtenerSucursalesPorNombre" + nombre;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaSucursal peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaSucursal.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static List<Sucursal> obtenerSucursalesPorIdEmpresa(Integer idEmpresa){
        List<Sucursal> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.SUCURSAL + "obtenerSucursalesPorIdEmpresa" + idEmpresa;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaSucursal peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaSucursal.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static Sucursal obtenerSucursalPorIdDireccion(Integer idDireccion){
        Sucursal respuesta = new Sucursal();
        
        String url = Constantes.Servicios.SUCURSAL + "obtenerSucursalPorIdDireccion/" + idDireccion;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaSucursal peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaSucursal.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static Sucursal obtenerSucursalPorId(Integer idSucursal){
        Sucursal respuesta = new Sucursal();
        
        String url = Constantes.Servicios.SUCURSAL + "obtenerSucursalPorId/" + idSucursal;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaSucursal peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaSucursal.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarSucursal(Sucursal sucursal){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.SUCURSAL + "registrar";
        String parametros = gson.toJson(sucursal);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.POST, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        
        return respuesta;
    }
    
    public static Mensaje modificarSucursal(Sucursal sucursal){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.SUCURSAL + "modificar";
        String parametros = gson.toJson(sucursal);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.PUT, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.MODIFICACION);
        
        return respuesta;
    }
    
    public static Mensaje eliminarSucursal(Integer idSucursal){
        Mensaje respuesta = new Mensaje();
        
        String url = Constantes.Servicios.SUCURSAL + "eliminar/" + idSucursal;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionDELETE(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.ELIMINACION);
        
        return respuesta;
    }
}