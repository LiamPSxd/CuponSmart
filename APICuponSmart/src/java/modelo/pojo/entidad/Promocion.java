package modelo.pojo.entidad;

public class Promocion{
    private Integer id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private String imagenBase64;
    private String fechaInicio;
    private String fechaTermino;
    private String restricciones;
    private Integer numeroCupones;
    private String codigo;
    private Float valor;
    private Integer idEstatus;
    private Integer idCategoria;
    private Integer idEmpresa;
    private Integer idTipoPromocion;
    
    public Promocion(){}

    public Promocion(Integer id, String nombre, String descripcion, String imagen, String fechaInicio, String fechaTermino, String restricciones, Integer numeroCupones, String codigo, Float valor, Integer idEstatus, Integer idCategoria, Integer idEmpresa, Integer idTipoPromocion){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.restricciones = restricciones;
        this.numeroCupones = numeroCupones;
        this.codigo = codigo;
        this.valor = valor;
        this.idEstatus = idEstatus;
        this.idCategoria = idCategoria;
        this.idEmpresa = idEmpresa;
        this.idTipoPromocion = idTipoPromocion;
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

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getImagen(){
        return imagen;
    }

    public void setImagen(String imagen){
        this.imagen = imagen;
    }
    
    public String getImagenBase64(){
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64){
        this.imagenBase64 = imagenBase64;
    }

    public String getFechaInicio(){
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio){
        this.fechaInicio = fechaInicio;
    }

    public String getFechaTermino(){
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino){
        this.fechaTermino = fechaTermino;
    }

    public String getRestricciones(){
        return restricciones;
    }

    public void setRestricciones(String restricciones){
        this.restricciones = restricciones;
    }

    public Integer getNumeroCupones(){
        return numeroCupones;
    }

    public void setNumeroCupones(Integer numeroCupones){
        this.numeroCupones = numeroCupones;
    }

    public String getCodigo(){
        return codigo;
    }

    public void setCodigo(String codigo){
        this.codigo = codigo;
    }

    public Float getValor(){
        return valor;
    }

    public void setValor(Float valor){
        this.valor = valor;
    }

    public Integer getIdEstatus(){
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus){
        this.idEstatus = idEstatus;
    }

    public Integer getIdCategoria(){
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria){
        this.idCategoria = idCategoria;
    }

    public Integer getIdEmpresa(){
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa){
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdTipoPromocion(){
        return idTipoPromocion;
    }

    public void setIdTipoPromocion(Integer idTipoPromocion){
        this.idTipoPromocion = idTipoPromocion;
    }
}