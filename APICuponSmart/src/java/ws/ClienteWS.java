package ws;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
    
    @POST
    @Path("registrarCliente")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaCliente registrarCliente(String jsonParam){
        RespuestaCliente respuesta = new RespuestaCliente();
        try{
            Gson gson = new Gson();
            Cliente cliente = gson.fromJson(jsonParam, Cliente.class);
            respuesta = ClienteDAO.registrarCliente(cliente);
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        return respuesta;
    }
}
