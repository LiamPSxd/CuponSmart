package modelo.pojo.respuesta;

import java.util.List;
import modelo.pojo.entidad.Promocion;

public class RespuestaPromocion extends Mensaje{
    private List<Promocion> contenido;
    
    public List<Promocion> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Promocion> contenido){
        this.contenido = contenido;
    }
}