package cuponsmart.modelo.pojo.respuesta;

import java.util.List;
import cuponsmart.modelo.pojo.entidad.Sucursal;

public class RespuestaSucursal extends Mensaje{
    private List<Sucursal> contenido;
    
    public List<Sucursal> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Sucursal> contenido){
        this.contenido = contenido;
    }
}