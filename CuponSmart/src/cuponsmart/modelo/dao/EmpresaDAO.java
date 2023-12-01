package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.entidad.Empresa;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.RespuestaEmpresa;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import utils.Constantes;
import utils.Verificaciones;

public class EmpresaDAO{
    public static List<Empresa> obtenerEmpresas(){
        List<Empresa> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.EMPRESA + "obtenerEmpresas";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaEmpresa peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaEmpresa.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido();
            }
        }
        
        return respuesta;
    }
    
    public static Empresa obtenerEmpresaPorId(Integer idEmpresa){
        Empresa respuesta = new Empresa();
        
        String url = Constantes.Servicios.EMPRESA + "obtenerEmpresaPorId/" + idEmpresa;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaEmpresa peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaEmpresa.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido().get(0);
            }
        }
        
        return respuesta;
    }
    
    public static Empresa obtenerEmpresaPorNombre(String nombreEmpresa){
        Empresa respuesta = new Empresa();
        
        String url = Constantes.Servicios.EMPRESA + "obtenerEmpresaPorNombre/" + nombreEmpresa;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaEmpresa peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaEmpresa.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido().get(0);
            }
        }
        
        return respuesta;
    }
    
    public static Empresa obtenerEmpresaPorRFC(String rfcEmpresa){
        Empresa respuesta = new Empresa();
        
        String url = Constantes.Servicios.EMPRESA + "obtenerEmpresaPorRFC/" + rfcEmpresa;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaEmpresa peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaEmpresa.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido().get(0);
            }
        }
        
        return respuesta;
    }
    
    public static Empresa obtenerEmpresaPorRepresentanteLegal(String representanteLegal){
        Empresa respuesta = new Empresa();
        
        String url = Constantes.Servicios.EMPRESA + "obtenerEmpresaPorRepresentanteLegal/" + representanteLegal;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaEmpresa peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaEmpresa.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje())){
                respuesta = peticion.getContenido().get(0);
            }
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarEmpresa(Empresa empresa){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.EMPRESA + "registrar";
        String parametros = gson.toJson(empresa);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.POST, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        }
        
        return respuesta;
    }
    
    public static Mensaje modificarEmpresa(Empresa empresa){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.EMPRESA + "modificar";
        String parametros = gson.toJson(empresa);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.PUT, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            respuesta.setMensaje(Constantes.Errores.MODIFICACION);
        }
        
        return respuesta;
    }
    
    public static Mensaje eliminarEmpresa(Integer idEmpresa){
        Mensaje respuesta = new Mensaje();
        
        String url = Constantes.Servicios.EMPRESA + "eliminar/" + idEmpresa;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionDELETE(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        }else{
            respuesta.setMensaje(Constantes.Errores.ELIMINACION);
        }
        
        return respuesta;
    }
}