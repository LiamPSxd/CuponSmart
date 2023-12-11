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
import modelo.dao.UsuarioDAO;
import modelo.pojo.entidad.Usuario;
import modelo.pojo.respuesta.RespuestaUsuario;
import utils.Constantes;
import utils.Verificaciones;

@Path("usuarios")
public class UsuarioWS{
    @Context
    private UriInfo context;
    
    @GET
    @Path("obtenerUsuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaUsuario obtenerUsuarios(){
        return UsuarioDAO.obtenerUsuarios();
    }
    
    @GET
    @Path("obtenerUsuariosPorIdRol/{idRol}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaUsuario obtenerUsuariosPorIdRol(@PathParam("idRol") Integer idRol){
        return Verificaciones.Datos.numerico(idRol) ? UsuarioDAO.obtenerUsuariosPorIdRol(idRol) : (RespuestaUsuario) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerUsuarioPorId/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaUsuario obtenerUsuarioPorId(@PathParam("idUsuario") Integer idUsuario){
        return Verificaciones.Datos.numerico(idUsuario) ? UsuarioDAO.obtenerUsuarioPorId(idUsuario) : (RespuestaUsuario) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerUsuarioPorNombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaUsuario obtenerUsuarioPorNombre(@PathParam("nombre") String nombre){
        return Verificaciones.Datos.cadena(nombre) ? UsuarioDAO.obtenerUsuarioPorCadena("Nombre", nombre) : (RespuestaUsuario) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerUsuarioPorUsername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaUsuario obtenerUsuarioPorUsername(@PathParam("username") String username){
        return Verificaciones.Datos.cadena(username) ? UsuarioDAO.obtenerUsuarioPorCadena("Username", username) : (RespuestaUsuario) Verificaciones.Excepciones.badRequest();
    }
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaUsuario registrarUsuario(String jsonParam){
        RespuestaUsuario respuesta = new RespuestaUsuario();
        
        try{
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(jsonParam, Usuario.class);
            
            if(Verificaciones.Datos.claseNoNula(usuario) && Verificaciones.Datos.numerico(usuario.getIdRol())){
                respuesta = UsuarioDAO.registrarUsuario(usuario);
            }else{
                return (RespuestaUsuario) Verificaciones.Excepciones.badRequest();
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
    public RespuestaUsuario modificarUsuario(String jsonParam){
        RespuestaUsuario respuesta = new RespuestaUsuario();
        
        try{
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(jsonParam, Usuario.class);
            
            if(Verificaciones.Datos.claseNoNula(usuario) && Verificaciones.Datos.numerico(usuario.getId())){
                respuesta = UsuarioDAO.modificarUsuario(usuario);
            }else{
                return (RespuestaUsuario) Verificaciones.Excepciones.badRequest();
            }
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        
        return respuesta;
    }
    
    @DELETE
    @Path("eliminar/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaUsuario eliminarUsuario(@PathParam("idUsuario") Integer idUsuario){
        return Verificaciones.Datos.numerico(idUsuario) ? UsuarioDAO.eliminarUsuario(idUsuario) : (RespuestaUsuario) Verificaciones.Excepciones.badRequest();
    }
}