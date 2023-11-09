package modelo.pojo.respuesta;

import java.util.List;
import modelo.pojo.entidad.Municipio;

public class RespuestaMunicipio extends Mensaje{
    private List<Municipio> contenido;
    
    public List<Municipio> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Municipio> contenido){
        this.contenido = contenido;
    }
}