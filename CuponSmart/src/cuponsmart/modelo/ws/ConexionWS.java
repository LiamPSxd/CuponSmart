package cuponsmart.modelo.ws;

import cuponsmart.modelo.pojo.respuesta.RespuestaHTTP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import utils.Constantes;

public class ConexionWS{
    private static String obtenerContenidoWS(InputStream inputStream) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuilder respuesta = new StringBuilder();
        
        while((inputLine = br.readLine()) != null){
            respuesta.append(inputLine);
        }
        
        br.close();
        
        return respuesta.toString();
    }
    
    public static RespuestaHTTP peticionGET(String url){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        
        try{
            HttpURLConnection conexionHttp = (HttpURLConnection) new URL(url).openConnection();
            conexionHttp.setRequestMethod(Constantes.MetodosHTTP.GET);
            
            respuesta.setCodigo(conexionHttp.getResponseCode());
            respuesta.setContenido(
                respuesta.getCodigo() == HttpURLConnection.HTTP_OK ? obtenerContenidoWS(conexionHttp.getInputStream()) : Constantes.Retornos.codigoRespuestaHTTP(respuesta.getCodigo())
            );
        }catch(MalformedURLException e){
            respuesta.setCodigo(Constantes.Errores.ERROR_URL);
            respuesta.setContenido(Constantes.Excepciones.MALFORMED_URL);
        }catch(IOException e){
            respuesta.setCodigo(Constantes.Errores.ERROR_PETICION);
            respuesta.setContenido(Constantes.Excepciones.IO);
        }
        
        return respuesta;
    }
    
    public static RespuestaHTTP peticionPOST(String url, String parametros){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        
        try{
            HttpURLConnection conexionHttp = (HttpURLConnection) new URL(url).openConnection();
            conexionHttp.setRequestMethod(Constantes.MetodosHTTP.POST);
            conexionHttp.setRequestProperty(Constantes.Servicios.CONTENT_TYPE, Constantes.Servicios.APPLICATION_WWW_FORM);
            conexionHttp.setDoOutput(true);
            
            OutputStream os = conexionHttp.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();
            
            respuesta.setCodigo(conexionHttp.getResponseCode());
            respuesta.setContenido(
                respuesta.getCodigo() == HttpURLConnection.HTTP_OK ? obtenerContenidoWS(conexionHttp.getInputStream()) : Constantes.Retornos.codigoRespuestaHTTP(respuesta.getCodigo())
            );
        }catch(MalformedURLException e){
            respuesta.setCodigo(Constantes.Errores.ERROR_URL);
            respuesta.setContenido(Constantes.Excepciones.MALFORMED_URL);
        }catch(IOException e){
            respuesta.setCodigo(Constantes.Errores.ERROR_PETICION);
            respuesta.setContenido(Constantes.Excepciones.IO);
        }
        
        return respuesta;
    }
    
    public static RespuestaHTTP peticionPOSTPUTJSON(String metodo, String url, String jsonParam){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        
        try{
            HttpURLConnection conexionHttp = (HttpURLConnection) new URL(url).openConnection();
            conexionHttp.setRequestMethod(metodo);
            conexionHttp.setRequestProperty(Constantes.Servicios.CONTENT_TYPE, Constantes.Servicios.APPLICATION_JSON);
            conexionHttp.setDoOutput(true);
            
            OutputStream os = conexionHttp.getOutputStream();
            os.write(jsonParam.getBytes());
            os.flush();
            os.close();
            
            respuesta.setCodigo(conexionHttp.getResponseCode());
            respuesta.setContenido(
                respuesta.getCodigo() == HttpURLConnection.HTTP_OK ? obtenerContenidoWS(conexionHttp.getInputStream()) : Constantes.Retornos.codigoRespuestaHTTP(respuesta.getCodigo())
            );
        }catch(MalformedURLException e){
            respuesta.setCodigo(Constantes.Errores.ERROR_URL);
            respuesta.setContenido(Constantes.Excepciones.MALFORMED_URL);
        }catch(IOException e){
            respuesta.setCodigo(Constantes.Errores.ERROR_PETICION);
            respuesta.setContenido(Constantes.Excepciones.IO);
        }
        
        return respuesta;
    }
    
    public static RespuestaHTTP peticionPUTMedia(String url, byte[] media){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        
        try{
            HttpURLConnection conexionHttp = (HttpURLConnection) new URL(url).openConnection();
            conexionHttp.setRequestMethod(Constantes.MetodosHTTP.PUT);
            conexionHttp.setDoOutput(true);
            
            OutputStream os = conexionHttp.getOutputStream();
            os.write(media);
            os.flush();
            os.close();
            
            respuesta.setCodigo(conexionHttp.getResponseCode());
            respuesta.setContenido(
                respuesta.getCodigo() == HttpURLConnection.HTTP_OK ? obtenerContenidoWS(conexionHttp.getInputStream()) : Constantes.Retornos.codigoRespuestaHTTP(respuesta.getCodigo())
            );
        }catch(MalformedURLException e){
            respuesta.setCodigo(Constantes.Errores.ERROR_URL);
            respuesta.setContenido(Constantes.Excepciones.MALFORMED_URL);
        }catch(IOException e){
            respuesta.setCodigo(Constantes.Errores.ERROR_PETICION);
            respuesta.setContenido(Constantes.Excepciones.IO);
        }
        
        return respuesta;
    }
    
    public static RespuestaHTTP peticionDELETE(String url){
        RespuestaHTTP respuesta = new RespuestaHTTP();
        
        try{
            HttpURLConnection conexionHttp = (HttpURLConnection) new URL(url).openConnection();
            conexionHttp.setRequestMethod(Constantes.MetodosHTTP.DELETE);
            
            respuesta.setCodigo(conexionHttp.getResponseCode());
            respuesta.setContenido(
                respuesta.getCodigo() == HttpURLConnection.HTTP_OK ? obtenerContenidoWS(conexionHttp.getInputStream()) : Constantes.Retornos.codigoRespuestaHTTP(respuesta.getCodigo())
            );
        }catch(MalformedURLException e){
            respuesta.setCodigo(Constantes.Errores.ERROR_URL);
            respuesta.setContenido(Constantes.Excepciones.MALFORMED_URL);
        }catch(IOException e){
            respuesta.setCodigo(Constantes.Errores.ERROR_PETICION);
            respuesta.setContenido(Constantes.Excepciones.IO);
        }
        
        return respuesta;
    }
}