package cuponsmart.modelo.pojo.respuesta;

public class RespuestaHTTP{
    private Integer codigo;
    private String contenido;
    
    public RespuestaHTTP(){}
    
    public RespuestaHTTP(Integer codigo, String contenido){
        this.codigo = codigo;
        this.contenido = contenido;
    }
    
    public Integer getCodigo(){
        return codigo;
    }
    
    public void setCodigo(Integer codigo){
        this.codigo = codigo;
    }
    
    public String getContenido(){
        return contenido;
    }
    
    public void setContenido(String contenido){
        this.contenido = contenido;
    }
}