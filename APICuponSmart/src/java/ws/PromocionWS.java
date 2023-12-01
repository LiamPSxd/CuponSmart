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
import modelo.dao.PromocionDAO;
import modelo.pojo.entidad.Promocion;
import modelo.pojo.respuesta.RespuestaPromocion;
import utils.Constantes;
import utils.Verificaciones;

@Path("promociones")
public class PromocionWS{
    @Context
    private UriInfo context;
    
    @GET
    @Path("obtenerPromociones")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocion obtenerPromociones(){
        return PromocionDAO.obtenerPromociones();
    }
    
    @GET
    @Path("obtenerPromocionesPorFechaInicio/{fechaInicio}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocion obtenerPromocionesPorFechaInicio(@PathParam("fechaInicio") String fechaInicio){
        return Verificaciones.Datos.cadena(fechaInicio) ? PromocionDAO.obtenerPromocionesPorFechaInicioTerminoONombre(Constantes.Datos.FECHA_INICIO, fechaInicio) : (RespuestaPromocion) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerPromocionesPorFechaTermino/{fechaTermino}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocion obtenerPromocionesPorFechaTermino(@PathParam("fechaTermino") String fechaTermino){
        return Verificaciones.Datos.cadena(fechaTermino) ? PromocionDAO.obtenerPromocionesPorFechaInicioTerminoONombre(Constantes.Datos.FECHA_TERMINO, fechaTermino) : (RespuestaPromocion) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerPromocionesPorNombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocion obtenerPromocionesPorNombre(@PathParam("nombre") String nombre){
        return Verificaciones.Datos.cadena(nombre) ? PromocionDAO.obtenerPromocionesPorFechaInicioTerminoONombre(Constantes.Datos.NOMBRE, nombre) : (RespuestaPromocion) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerPromocionesPorIdEmpresa/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocion obtenerPromocionesPorIdEmpresa(@PathParam("idEmpresa") Integer idEmpresa){
        return Verificaciones.Datos.numerico(idEmpresa) ? PromocionDAO.obtenerPromocionesPorIdEmpresa(idEmpresa) : (RespuestaPromocion) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerPromocionPorId/{idPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocion obtenerPromocionPorId(@PathParam("idPromocion") Integer idPromocion){
        return Verificaciones.Datos.numerico(idPromocion) ? PromocionDAO.obtenerPromocionPorId(idPromocion) : (RespuestaPromocion) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerPromocionPorCodigo/{codigoPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocion obtenerPromocionPorCodigo(@PathParam("codigoPromocion") String codigoPromocion){
        return Verificaciones.Datos.cadena(codigoPromocion) ? PromocionDAO.obtenerPromocionPorCodigo(codigoPromocion) : (RespuestaPromocion) Verificaciones.Excepciones.badRequest();
    }
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaPromocion registrarPromocion(String jsonParam){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        try{
            Gson gson = new Gson();
            Promocion promocion = gson.fromJson(jsonParam, Promocion.class);
            
            if(Verificaciones.Datos.claseNoNula(promocion) && Verificaciones.Datos.numerico(promocion.getIdEmpresa())){
                respuesta = PromocionDAO.registrarPromocion(promocion);
            }else{
                return (RespuestaPromocion) Verificaciones.Excepciones.badRequest();
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
    public RespuestaPromocion modificarPromocion(String jsonParam){
        RespuestaPromocion respuesta = new RespuestaPromocion();
        
        try{
            Gson gson = new Gson();
            Promocion promocion = gson.fromJson(jsonParam, Promocion.class);
            
            if(Verificaciones.Datos.claseNoNula(promocion) && Verificaciones.Datos.numerico(promocion.getId())){
                respuesta = PromocionDAO.modificarPromocion(promocion);
            }else{
                return (RespuestaPromocion) Verificaciones.Excepciones.badRequest();
            }
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        
        return respuesta;
    }
    
    @DELETE
    @Path("eliminar/{idPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocion eliminarPromocion(@PathParam("idPromocion") Integer idPromocion){
        return Verificaciones.Datos.numerico(idPromocion) ? PromocionDAO.eliminarPromocion(idPromocion) : (RespuestaPromocion) Verificaciones.Excepciones.badRequest();
    }
}