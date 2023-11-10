package cuponsmart.modelo.pojo.respuesta;

import java.util.List;
import cuponsmart.modelo.pojo.entidad.Empresa;

public class RespuestaEmpresa extends Mensaje{
    private List<Empresa> contenido;
    
    public List<Empresa> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Empresa> contenido){
        this.contenido = contenido;
    }
}