package cuponsmart.modelo.pojo.entidad;

import javafx.beans.property.SimpleBooleanProperty;

public class Sucursal{
    private Integer id;
    private String nombre;
    private String telefono;
    private String nombreEncargado;
    private Integer idDireccion;
    private String direccion;
    private Integer idEmpresa;
    private String empresa;
    private final SimpleBooleanProperty promocion = new SimpleBooleanProperty(false);
    
    public Sucursal(){}

    public Sucursal(Integer id, String nombre, String telefono, String nombreEncargado, Integer idDireccion, Integer idEmpresa){
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nombreEncargado = nombreEncargado;
        this.idDireccion = idDireccion;
        this.idEmpresa = idEmpresa;
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

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String getNombreEncargado(){
        return nombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado){
        this.nombreEncargado = nombreEncargado;
    }

    public Integer getIdDireccion(){
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion){
        this.idDireccion = idDireccion;
    }
    
    public String getDireccion(){
        return direccion;
    }

    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    public Integer getIdEmpresa(){
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa){
        this.idEmpresa = idEmpresa;
    }
    
    public String getEmpresa(){
        return empresa;
    }

    public void setEmpresa(String empresa){
        this.empresa = empresa;
    }
    
    public SimpleBooleanProperty promocionProperty(){
        return promocion;
    }
    
    public Boolean getPromocion(){
        return promocionProperty().get();
    }

    public void setPromocion(Boolean promocion){
        promocionProperty().set(promocion);
    }
}