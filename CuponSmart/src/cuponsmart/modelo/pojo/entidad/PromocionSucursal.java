package cuponsmart.modelo.pojo.entidad;

public class PromocionSucursal{
    private Integer idPromocion;
    private Integer idSucursal;
    
    public PromocionSucursal(){}

    public PromocionSucursal(Integer idPromocion, Integer idSucursal){
        this.idPromocion = idPromocion;
        this.idSucursal = idSucursal;
    }

    public Integer getIdPromocion(){
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion){
        this.idPromocion = idPromocion;
    }

    public Integer getIdSucursal(){
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal){
        this.idSucursal = idSucursal;
    }
}