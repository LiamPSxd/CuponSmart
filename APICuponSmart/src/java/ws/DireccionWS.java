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
import modelo.dao.DireccionDAO;
import modelo.pojo.entidad.Direccion;
import modelo.pojo.respuesta.RespuestaDireccion;
import utils.Constantes;
import utils.Verificaciones;

@Path("direcciones")
public class DireccionWS{
    @Context
    private UriInfo context;
    
    @GET
    @Path("obtenerDirecciones")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaDireccion obtenerDirecciones(){
        return DireccionDAO.obtenerDirecciones();
    }
    
    @GET
    @Path("obtenerDireccionesPorCalleColoniaNumero/{busqueda}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaDireccion obtenerDireccionesPorCalleColoniaNumero(@PathParam("busqueda") String busqueda){
        return Verificaciones.Datos.cadena(busqueda) ? DireccionDAO.obtenerDireccionesPorCalleColoniaNumero(busqueda) : (RespuestaDireccion) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerDireccionPorId/{idDireccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaDireccion obtenerDireccionPorIdDireccion(@PathParam("idDireccion") Integer idDireccion){
        return Verificaciones.Datos.numerico(idDireccion) ? DireccionDAO.obtenerDireccionPorId(idDireccion) : (RespuestaDireccion) Verificaciones.Excepciones.badRequest();
    }
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaDireccion registrarDireccion(String jsonParam){
        RespuestaDireccion respuesta = new RespuestaDireccion();
        
        try{
            Gson gson = new Gson();
            Direccion direccion = gson.fromJson(jsonParam, Direccion.class);
            
            if(Verificaciones.Datos.claseNoNula(direccion) && Verificaciones.Datos.numerico(direccion.getIdCiudad())){
                respuesta = DireccionDAO.registrarDireccion(direccion);
            }else{
                return (RespuestaDireccion) Verificaciones.Excepciones.badRequest();
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
    public RespuestaDireccion modificarDireccion(String jsonParam){
        RespuestaDireccion respuesta = new RespuestaDireccion();
        
        try{
            Gson gson = new Gson();
            Direccion direccion = gson.fromJson(jsonParam, Direccion.class);
            
            if(Verificaciones.Datos.claseNoNula(direccion) && Verificaciones.Datos.numerico(direccion.getId())){
                respuesta = DireccionDAO.modificarDireccion(direccion);
            }else{
                return (RespuestaDireccion) Verificaciones.Excepciones.badRequest();
            }
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        
        return respuesta;
    }
    
    @DELETE
    @Path("eliminar/{idDireccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaDireccion eliminarDireccion(@PathParam("idDireccion") Integer idDireccion){
        return Verificaciones.Datos.numerico(idDireccion) ? DireccionDAO.eliminarDireccion(idDireccion) : (RespuestaDireccion) Verificaciones.Excepciones.badRequest();
    }
}