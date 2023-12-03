package cuponsmart.modelo.pojo.entidad;

public class Municipio{
    private Integer id;
    private String nombre;
    private Integer idEstado;
    
    public Municipio(){}

    public Municipio(Integer id, String nombre, Integer idEstado){
        this.id = id;
        this.nombre = nombre;
        this.idEstado = idEstado;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
    
    @Override
    public String toString(){
        return this.getNombre();
    }
}