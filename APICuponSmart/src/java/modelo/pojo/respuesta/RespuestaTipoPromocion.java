package modelo.pojo.respuesta;

import java.util.List;
import modelo.pojo.entidad.TipoPromocion;

public class RespuestaTipoPromocion extends Mensaje{
    private List<TipoPromocion> contenido;
    
    public List<TipoPromocion> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<TipoPromocion> contenido){
        this.contenido = contenido;
    }
}