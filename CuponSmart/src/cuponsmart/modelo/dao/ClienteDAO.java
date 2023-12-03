package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.entidad.Cliente;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.RespuestaCliente;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import utils.Constantes;
import utils.Verificaciones;

public class ClienteDAO{
    public static List<Cliente> obtenerClientes(){
        List<Cliente> respuesta = new ArrayList();
        
        String url = Constantes.Servicios.CLIENTE + "obtenerClientes";
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaCliente peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaCliente.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido();
        }
        
        return respuesta;
    }
    
    public static Cliente obtenerClientePorId(Integer idCliente){
        Cliente respuesta = new Cliente();
        
        String url = Constantes.Servicios.CLIENTE + "obtenerClientePorId/" + idCliente;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionGET(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK){
            RespuestaCliente peticion = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaCliente.class);
            
            if(!peticion.getError() && Verificaciones.Datos.success(peticion.getMensaje()))
                respuesta = peticion.getContenido().get(0);
        }
        
        return respuesta;
    }
    
    public static Mensaje registrarCliente(Cliente cliente){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.CLIENTE + "registrar";
        String parametros = gson.toJson(cliente);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.POST, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.REGISTRO);
        
        return respuesta;
    }
    
    public static Mensaje modificarCliente(Cliente cliente){
        Mensaje respuesta = new Mensaje();
        Gson gson = new Gson();
        
        String url = Constantes.Servicios.CLIENTE + "modificar";
        String parametros = gson.toJson(cliente);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOSTPUTJSON(Constantes.MetodosHTTP.PUT, url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.MODIFICACION);
        
        return respuesta;
    }
    
    public static Mensaje eliminarCliente(Integer idCliente){
        Mensaje respuesta = new Mensaje();
        
        String url = Constantes.Servicios.CLIENTE + "eliminar/" + idCliente;
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionDELETE(url);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), Mensaje.class);
        else
            respuesta.setMensaje(Constantes.Errores.ELIMINACION);
        
        return respuesta;
    }
}