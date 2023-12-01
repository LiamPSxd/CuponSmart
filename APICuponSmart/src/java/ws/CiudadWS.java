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
import modelo.dao.CiudadDAO;
import modelo.pojo.entidad.Ciudad;
import modelo.pojo.respuesta.RespuestaCiudad;
import utils.Constantes;
import utils.Verificaciones;

@Path("ciudades")
public class CiudadWS{
    @Context
    private UriInfo context;
    
    @GET
    @Path("obtenerCiudades")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaCiudad obtenerCiudades(){
        return CiudadDAO.obtenerCiudades();
    }
    
    @GET
    @Path("obtenerCiudadesPorIdMunicipio/{idMunicipio}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaCiudad obtenerCiudades(@PathParam("idMunicipio") Integer idMunicipio){
        return Verificaciones.Datos.numerico(idMunicipio) ? CiudadDAO.obtenerCiudadesPorIdMunicipio(idMunicipio) : (RespuestaCiudad) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerCiudadPorId/{idCiudad}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaCiudad obtenerCiudadPorId(@PathParam("idCiudad") Integer idCiudad){
        return Verificaciones.Datos.numerico(idCiudad) ? CiudadDAO.obtenerCiudadPorId(idCiudad) : (RespuestaCiudad) Verificaciones.Excepciones.badRequest();
    }
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaCiudad registrarCiudad(String jsonParam){
        RespuestaCiudad respuesta = new RespuestaCiudad();
        
        try{
            Gson gson = new Gson();
            Ciudad ciudad = gson.fromJson(jsonParam, Ciudad.class);
            
            if(Verificaciones.Datos.claseNoNula(ciudad) && Verificaciones.Datos.numerico(ciudad.getIdMunicipio())){
                respuesta = CiudadDAO.registrarCiudad(ciudad);
            }else{
                return (RespuestaCiudad) Verificaciones.Excepciones.badRequest();
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
    public RespuestaCiudad modificarCiudad(String jsonParam){
        RespuestaCiudad respuesta = new RespuestaCiudad();
        
        try{
            Gson gson = new Gson();
            Ciudad ciudad = gson.fromJson(jsonParam, Ciudad.class);
            
            if(Verificaciones.Datos.claseNoNula(ciudad) && Verificaciones.Datos.numerico(ciudad.getId())){
                respuesta = CiudadDAO.modificarCiudad(ciudad);
            }else{
                return (RespuestaCiudad) Verificaciones.Excepciones.badRequest();
            }
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        
        return respuesta;
    }
    
    @DELETE
    @Path("eliminar/{idCiudad}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaCiudad eliminarCiudad(@PathParam("idCiudad") Integer idCiudad){
        return Verificaciones.Datos.numerico(idCiudad) ? CiudadDAO.eliminarCiudad(idCiudad) : (RespuestaCiudad) Verificaciones.Excepciones.badRequest();
    }
}