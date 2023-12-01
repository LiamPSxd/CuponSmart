package ws;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import modelo.dao.PromocionSucursalDAO;
import modelo.pojo.entidad.PromocionSucursal;
import modelo.pojo.respuesta.RespuestaPromocionSucursal;
import utils.Constantes;
import utils.Verificaciones;

@Path("promocionesSucursales")
public class PromocionSucursalWS{
    @Context
    private UriInfo context;
    
    @GET
    @Path("obtenerPromocionesSucursales")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocionSucursal obtenerPromocionesSucursales(){
        return PromocionSucursalDAO.obtenerPromocionesSucursales();
    }
    
    @GET
    @Path("obtenerPromocionesSucursalesPorIdPromocion/{idPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocionSucursal obtenerPromocionesSucursalesPorIdPromocion(@PathParam("idPromocion") Integer idPromocion){
        return Verificaciones.Datos.numerico(idPromocion) ? PromocionSucursalDAO.obtenerPromocionesSucursalesPorIdPromocionOSucursal(Constantes.Datos.PROMOCION, idPromocion) : (RespuestaPromocionSucursal) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerPromocionesSucursalesPorIdSucursal/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocionSucursal obtenerPromocionesSucursalesPorIdSucursal(@PathParam("idSucursal") Integer idSucursal){
        return Verificaciones.Datos.numerico(idSucursal) ? PromocionSucursalDAO.obtenerPromocionesSucursalesPorIdPromocionOSucursal(Constantes.Datos.SUCURSAL, idSucursal) : (RespuestaPromocionSucursal) Verificaciones.Excepciones.badRequest();
    }
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaPromocionSucursal registrarPromocionSucursal(String jsonParam){
        RespuestaPromocionSucursal respuesta = new RespuestaPromocionSucursal();
        
        try{
            Gson gson = new Gson();
            PromocionSucursal promocionSucursal = gson.fromJson(jsonParam, PromocionSucursal.class);
            
            if(Verificaciones.Datos.claseNoNula(promocionSucursal) && Verificaciones.Datos.numerico(promocionSucursal.getIdPromocion())){
                respuesta = PromocionSucursalDAO.registrarPromocionSucursal(promocionSucursal);
            }else{
                return (RespuestaPromocionSucursal) Verificaciones.Excepciones.badRequest();
            }
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        
        return respuesta;
    }
    
    @DELETE
    @Path("eliminar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaPromocionSucursal eliminarPromocionSucursal(String jsonParam){
        RespuestaPromocionSucursal respuesta = new RespuestaPromocionSucursal();
        
        try{
            Gson gson = new Gson();
            PromocionSucursal promocionSucursal = gson.fromJson(jsonParam, PromocionSucursal.class);
            
            if(Verificaciones.Datos.claseNoNula(promocionSucursal) && Verificaciones.Datos.numerico(promocionSucursal.getIdPromocion())){
                respuesta = PromocionSucursalDAO.eliminarPromocionSucursal(promocionSucursal);
            }else{
                return (RespuestaPromocionSucursal) Verificaciones.Excepciones.badRequest();
            }
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        
        return respuesta;
    }
}