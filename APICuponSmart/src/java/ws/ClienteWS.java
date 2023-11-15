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
import modelo.ClienteDAO;
import modelo.pojo.entidad.Cliente;
import modelo.pojo.respuesta.RespuestaCliente;
import utils.Constantes;
import utils.Verificaciones;


@Path("clientes")
public class ClienteWS {
    @Context
    private UriInfo context;
    
    @GET
    @Path("obtenerClientes")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaCliente obtenerClientes(){
        return ClienteDAO.obtenerClientes();
    }
    
    @GET
    @Path("obtenerClientePorId/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaCliente obtenerClientePorId(@PathParam("idCliente") Integer idCliente){
        return Verificaciones.Datos.numerico(idCliente) ? ClienteDAO.obtenerClientePorId(idCliente): (RespuestaCliente) Verificaciones.Excepciones.badRequest();
    }
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaCliente registrarCliente(String jsonParam){
        RespuestaCliente respuesta = new RespuestaCliente();
        try{
            Gson gson = new Gson();
            Cliente cliente = gson.fromJson(jsonParam, Cliente.class);
            if(Verificaciones.Datos.claseNoNula(cliente) && Verificaciones.Datos.numerico(cliente.getIdDireccion())){
                respuesta = ClienteDAO.registrarCliente(cliente);
            }else{
                return (RespuestaCliente) Verificaciones.Excepciones.badRequest();
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
    public RespuestaCliente modificarCliente(String jsonParam){
        RespuestaCliente respuesta = new RespuestaCliente();
        
        try{
            Gson gson = new Gson();
            Cliente cliente = gson.fromJson(jsonParam, Cliente.class);
            if(Verificaciones.Datos.claseNoNula(cliente) && Verificaciones.Datos.numerico(cliente.getId())){
                respuesta = ClienteDAO.modificarCliente(cliente);
            }else{
                return (RespuestaCliente) Verificaciones.Excepciones.badRequest();
            }            
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        return respuesta;
    }
    
    @DELETE
    @Path("eliminar/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaCliente eliminarCliente(@PathParam("idCliente") Integer idCliente){
        return Verificaciones.Datos.numerico(idCliente) ? ClienteDAO.eliminarCliente(idCliente) : (RespuestaCliente) Verificaciones.Excepciones.badRequest();
    }
}
