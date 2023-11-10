package cuponsmart.modelo.pojo.entidad;

public class Empresa{
    private Integer id;
    private String nombre;
    private String nombreComercial;
    private String logo;
    private String logoBase64;
    private String nombreRepresentanteLegal;
    private String correo;
    private String telefono;
    private String paginaWeb;
    private String rfc;
    private Integer idEstatus;
    private Integer idDireccion;
    
    public Empresa(){}

    public Empresa(Integer id, String nombre, String nombreComercial, String logo, String nombreRepresentanteLegal, String correo, String telefono, String paginaWeb, String rfc, Integer idEstatus, Integer idDireccion){
        this.id = id;
        this.nombre = nombre;
        this.nombreComercial = nombreComercial;
        this.logo = logo;
        this.nombreRepresentanteLegal = nombreRepresentanteLegal;
        this.correo = correo;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.rfc = rfc;
        this.idEstatus = idEstatus;
        this.idDireccion = idDireccion;
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

    public String getNombreComercial(){
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial){
        this.nombreComercial = nombreComercial;
    }

    public String getLogo(){
        return logo;
    }

    public void setLogo(String logo){
        this.logo = logo;
    }
    
    public String getLogoBase64(){
        return logoBase64;
    }

    public void setLogoBase64(String logoBase64){
        this.logoBase64 = logoBase64;
    }

    public String getNombreRepresentanteLegal(){
        return nombreRepresentanteLegal;
    }

    public void setNombreRepresentanteLegal(String nombreRepresentanteLegal){
        this.nombreRepresentanteLegal = nombreRepresentanteLegal;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String getPaginaWeb(){
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb){
        this.paginaWeb = paginaWeb;
    }

    public String getRfc(){
        return rfc;
    }

    public void setRfc(String rfc){
        this.rfc = rfc;
    }

    public Integer getIdEstatus(){
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus){
        this.idEstatus = idEstatus;
    }

    public Integer getIdDireccion(){
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion){
        this.idDireccion = idDireccion;
    }
}