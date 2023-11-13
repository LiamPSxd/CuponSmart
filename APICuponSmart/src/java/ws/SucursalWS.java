package ws;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import modelo.SucursalDAO;
import modelo.pojo.entidad.Sucursal;
import modelo.pojo.respuesta.RespuestaSucursal;
import utils.Constantes;
import utils.Verificaciones;

@Path("sucursales")
public class SucursalWS{
    @Context
    private UriInfo context;
    
    @GET
    @Path("obtenerSucursales")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaSucursal obtenerSucursales(){
        return SucursalDAO.obtenerSucursales();
    }
    
    @GET
    @Path("obtenerSucursalesPorNombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaSucursal obtenerSucursalesPorNombre(@PathParam("nombre") String nombre){
        return Verificaciones.Datos.cadena(nombre) ? SucursalDAO.obtenerSucursalesPorNombre(nombre) : (RespuestaSucursal) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerSucursalPorIdDireccion/{idDireccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaSucursal obtenerSucursalesPorIdDireccion(@PathParam("idDireccion") Integer idDireccion){
        return Verificaciones.Datos.numerico(idDireccion) ? SucursalDAO.obtenerSucursalPorIdSucursalOIdDireccion(Constantes.Datos.ID_DIRECCION, idDireccion) : (RespuestaSucursal) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerSucursalPorId/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaSucursal obtenerSucursalPorId(@PathParam("idSucursal") Integer idSucursal){
        return Verificaciones.Datos.numerico(idSucursal) ? SucursalDAO.obtenerSucursalPorIdSucursalOIdDireccion(Constantes.Datos.ID, idSucursal) : (RespuestaSucursal) Verificaciones.Excepciones.badRequest();
    }
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaSucursal registrarSucursal(String jsonParam){
        RespuestaSucursal respuesta = new RespuestaSucursal();
        
        try{
            Gson gson = new Gson();
            Sucursal sucursal = gson.fromJson(jsonParam, Sucursal.class);
            
            if(Verificaciones.Datos.claseNoNula(sucursal) && Verificaciones.Datos.cadena(sucursal.getNombre())){
                respuesta = SucursalDAO.registrarSucursal(sucursal);
            }else{
                return (RespuestaSucursal) Verificaciones.Excepciones.badRequest();
            }
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        
        return respuesta;
    }
    
    @PUT
    @Path("modificar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaSucursal modificarSucursal(String jsonParam){
        RespuestaSucursal respuesta = new RespuestaSucursal();
        
        try{
            Gson gson = new Gson();
            Sucursal sucursal = gson.fromJson(jsonParam, Sucursal.class);
            
            if(Verificaciones.Datos.claseNoNula(sucursal) && Verificaciones.Datos.numerico(sucursal.getId()))
                respuesta = SucursalDAO.modificarSucursal(sucursal);
            else{
                return (RespuestaSucursal) Verificaciones.Excepciones.badRequest();
            }
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        
        return respuesta;
    }
    
    @DELETE
    @Path("eliminar/{idSucursal}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaSucursal eliminarSucursal(@PathParam("idSucursal") Integer idSucursal){
        return Verificaciones.Datos.numerico(idSucursal) ? SucursalDAO.eliminarSucursal(idSucursal) : (RespuestaSucursal) Verificaciones.Excepciones.badRequest();
    }
}