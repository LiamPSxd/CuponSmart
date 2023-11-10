package cuponsmart.modelo.pojo.respuesta;

import cuponsmart.modelo.pojo.entidad.Categoria;
import java.util.List;

public class RespuestaCategoria extends Mensaje{
    private List<Categoria> contenido;
    
    public List<Categoria> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Categoria> contenido){
        this.contenido = contenido;
    }
}