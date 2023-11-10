package cuponsmart.modelo.pojo.respuesta;

import java.util.List;
import cuponsmart.modelo.pojo.entidad.Direccion;

public class RespuestaDireccion extends Mensaje{
    private List<Direccion> contenido;
    
    public List<Direccion> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Direccion> contenido){
        this.contenido = contenido;
    }
}