package modelo.pojo.entidad;

public class Cliente{
    private Integer id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    private String fechaNacimiento;
    private String contrasenia;
    private String foto;
    private String fotoBase64;
    private Integer idDireccion;
    
    public Cliente(){}

    public Cliente(Integer id, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String correo, String fechaNacimiento, String contrasenia, String foto, Integer idDireccion){
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.contrasenia = contrasenia;
        this.foto = foto;
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

    public String getApellidoPaterno(){
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno){
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno(){
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno){
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }

    public String getFechaNacimiento(){
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getContrasenia(){
        return contrasenia;
    }

    public void setContrasenia(String contrasenia){
        this.contrasenia = contrasenia;
    }
    
    public String getFoto(){
        return foto;
    }

    public void setFoto(String foto){
        this.foto = foto;
    }
    
    public String getFotoBase64(){
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64){
        this.fotoBase64 = fotoBase64;
    }

    public Integer getIdDireccion(){
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion){
        this.idDireccion = idDireccion;
    }
}