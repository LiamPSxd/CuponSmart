package cuponsmart.modelo.pojo.respuesta;

import java.util.List;
import cuponsmart.modelo.pojo.entidad.Estado;

public class RespuestaEstado extends Mensaje{
    private List<Estado> contenido;
    
    public List<Estado> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Estado> contenido){
        this.contenido = contenido;
    }
}