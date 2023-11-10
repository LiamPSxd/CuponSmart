package cuponsmart.modelo.pojo.respuesta;

import java.util.List;
import cuponsmart.modelo.pojo.entidad.Ciudad;

public class RespuestaCiudad extends Mensaje{
    private List<Ciudad> contenido;
    
    public List<Ciudad> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Ciudad> contenido){
        this.contenido = contenido;
    }
}