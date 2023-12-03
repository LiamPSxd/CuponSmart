package cuponsmart.modelo.dao;

import com.google.gson.Gson;
import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import cuponsmart.modelo.pojo.respuesta.RespuestaUsuario;
import cuponsmart.modelo.ws.ConexionWS;
import java.net.HttpURLConnection;
import utils.Constantes;

public class AutenticacionDAO{
    public static RespuestaUsuario iniciarSesion(String usuario, String contrasenia){
        RespuestaUsuario respuesta = new RespuestaUsuario();
        
        String url = Constantes.Servicios.AUTENTICACION + "loginEscritorio";
        String parametros = String.format("username=%s&contrasenia=%s", usuario, contrasenia);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOST(url, parametros);
        
        if(respuestaPeticion.getCodigo() == HttpURLConnection.HTTP_OK)
            respuesta = new Gson().fromJson(respuestaPeticion.getContenido(), RespuestaUsuario.class);
        else
            respuesta.setMensaje(Constantes.Errores.SOLICITUD);
        
        return respuesta;
    }
}