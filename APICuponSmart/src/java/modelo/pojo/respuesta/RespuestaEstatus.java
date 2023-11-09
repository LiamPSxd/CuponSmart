package modelo.pojo.respuesta;

import java.util.List;
import modelo.pojo.entidad.Estatus;

public class RespuestaEstatus extends Mensaje{
    private List<Estatus> contenido;
    
    public List<Estatus> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Estatus> contenido){
        this.contenido = contenido;
    }
}