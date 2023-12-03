package cuponsmart.modelo.pojo.entidad;

public class Ciudad{
    private Integer id;
    private String nombre;
    private Integer idMunicipio;
    
    public Ciudad(){}

    public Ciudad(Integer id, String nombre, Integer idMunicipio){
        this.id = id;
        this.nombre = nombre;
        this.idMunicipio = idMunicipio;
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

    public Integer getIdMunicipio(){
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio){
        this.idMunicipio = idMunicipio;
    }
    
    @Override
    public String toString(){
        return this.getNombre();
    }
}