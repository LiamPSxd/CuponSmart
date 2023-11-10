package cuponsmart.modelo.pojo.entidad;

public class Estatus{
    private Integer id;
    private String estado;
    
    public Estatus(){}

    public Estatus(Integer id, String estado){
        this.id = id;
        this.estado = estado;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }
}