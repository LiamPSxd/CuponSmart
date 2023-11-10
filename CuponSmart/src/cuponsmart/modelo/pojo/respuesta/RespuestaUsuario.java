package cuponsmart.modelo.pojo.respuesta;

import java.util.List;
import cuponsmart.modelo.pojo.entidad.Usuario;

public class RespuestaUsuario extends Mensaje{
    private List<Usuario> contenido;
    
    public List<Usuario> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Usuario> contenido){
        this.contenido = contenido;
    }
}