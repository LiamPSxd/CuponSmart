package utils;

import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class Verificaciones{
    public static class Datos{
        public static Boolean numerico(Number num){
            return num != null && num.intValue() > 0;
        }
        
        public static Boolean cadena(String dato){
            return dato != null && !dato.isEmpty();
        }

        public static Boolean claseNoNula(Object objeto){
            return objeto != null;
        }

        public static Boolean claseNula(Object objeto){
            return objeto == null;
        }

        public static Boolean listaNoVacia(List<?> lista){
            return lista.size() > 0;
        }
    }
    
    public static class Excepciones{
        public static Object badRequest(){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}