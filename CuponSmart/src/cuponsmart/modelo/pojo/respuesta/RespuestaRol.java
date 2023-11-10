package cuponsmart.modelo.pojo.respuesta;

import java.util.List;
import cuponsmart.modelo.pojo.entidad.Rol;

public class RespuestaRol extends Mensaje{
    private List<Rol> contenido;
    
    public List<Rol> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Rol> contenido){
        this.contenido = contenido;
    }
}