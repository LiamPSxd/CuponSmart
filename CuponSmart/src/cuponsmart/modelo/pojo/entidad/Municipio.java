package cuponsmart.modelo.pojo.entidad;

public class Municipio{
    private Integer id;
    private String nombre;
    
    public Municipio(){}

    public Municipio(Integer id, String nombre){
        this.id = id;
        this.nombre = nombre;
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
}