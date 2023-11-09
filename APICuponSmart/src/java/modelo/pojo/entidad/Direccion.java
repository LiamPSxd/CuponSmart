package modelo.pojo.entidad;

public class Direccion{
    private Integer id;
    private String calle;
    private String numero;
    private String codigoPostal;
    private String colonia;
    private String latitud;
    private String longitud;
    private Integer idCiudad;
    
    public Direccion(){}

    public Direccion(Integer id, String calle, String numero, String codigoPostal, String colonia, String latitud, String longitud, Integer idCiudad){
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.codigoPostal = codigoPostal;
        this.colonia = colonia;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idCiudad = idCiudad;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getCalle(){
        return calle;
    }

    public void setCalle(String calle){
        this.calle = calle;
    }

    public String getNumero(){
        return numero;
    }

    public void setNumero(String numero){
        this.numero = numero;
    }

    public String getCodigoPostal(){
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal){
        this.codigoPostal = codigoPostal;
    }

    public String getColonia(){
        return colonia;
    }

    public void setColonia(String colonia){
        this.colonia = colonia;
    }

    public String getLatitud(){
        return latitud;
    }

    public void setLatitud(String latitud){
        this.latitud = latitud;
    }

    public String getLongitud(){
        return longitud;
    }

    public void setLongitud(String longitud){
        this.longitud = longitud;
    }

    public Integer getIdCiudad(){
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad){
        this.idCiudad = idCiudad;
    }
}