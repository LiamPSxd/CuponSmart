package modelo.pojo.entidad;

public class Usuario{
    private Integer id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String curp;
    private String correo;
    private String username;
    private String contrasenia;
    private Integer idRol;
    private Integer idEmpresa;
    
    public Usuario(){}

    public Usuario(Integer id, String nombre, String apellidoPaterno, String apellidoMaterno, String curp, String correo, String username, String contrasenia, Integer idRol, Integer idEmpresa){
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.curp = curp;
        this.correo = correo;
        this.username = username;
        this.contrasenia = contrasenia;
        this.idRol = idRol;
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

    public String getCurp(){
        return curp;
    }

    public void setCurp(String curp){
        this.curp = curp;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getContrasenia(){
        return contrasenia;
    }

    public void setContrasenia(String contrasenia){
        this.contrasenia = contrasenia;
    }

    public Integer getIdRol(){
        return idRol;
    }

    public void setIdRol(Integer idRol){
        this.idRol = idRol;
    }

    public Integer getIdEmpresa(){
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa){
        this.idEmpresa = idEmpresa;
    }
}