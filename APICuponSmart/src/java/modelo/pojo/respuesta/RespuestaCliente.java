package modelo.pojo.respuesta;

import java.util.List;
import modelo.pojo.entidad.Cliente;

public class RespuestaCliente extends Mensaje{
    private List<Cliente> contenido;
    
    public List<Cliente> getContenido(){
        return contenido;
    }
    
    public void setContenido(List<Cliente> contenido){
        this.contenido = contenido;
    }
}