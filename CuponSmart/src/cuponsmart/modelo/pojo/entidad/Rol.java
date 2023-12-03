package cuponsmart.modelo.pojo.entidad;

public class Rol{
    private Integer id;
    private String nombre;
    
    public Rol(){}

    public Rol(Integer id, String nombre){
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
    
    @Override
    public String toString(){
        return this.getNombre();
    }
}