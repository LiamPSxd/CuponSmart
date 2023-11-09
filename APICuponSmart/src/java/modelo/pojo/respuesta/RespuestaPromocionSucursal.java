package modelo.pojo.respuesta;

import java.util.List;
import modelo.pojo.entidad.PromocionSucursal;

public class RespuestaPromocionSucursal extends Mensaje{
    private List<PromocionSucursal> contenido;
    
    public List<PromocionSucursal> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<PromocionSucursal> contenido){
        this.contenido = contenido;
    }
}