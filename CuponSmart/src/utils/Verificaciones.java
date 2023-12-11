package utils;

import java.util.List;

public class Verificaciones{
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
        Boolean respuesta = true;

        for(Object object : lista){
            respuesta = object != null;
        }

        return lista.size() > 0 && respuesta;
    }

    public static Boolean listaByteNoVacio(Byte[] lista){
        return lista != null;
    }

    public static Boolean success(String mensaje){
        return mensaje.equals(Constantes.Retornos.SUCCESS);
    }
}