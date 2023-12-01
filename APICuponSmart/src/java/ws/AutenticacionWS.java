package ws;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import modelo.dao.AutenticacionDAO;
import modelo.pojo.respuesta.RespuestaCliente;
import modelo.pojo.respuesta.RespuestaUsuario;
import utils.Verificaciones;

@Path("autenticacion")
public class AutenticacionWS{
    @Context
    private UriInfo context;

    @POST
    @Path("loginEscritorio")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaUsuario loginEscritorio(@FormParam("username") String username,
                                            @FormParam("contrasenia") String contrasenia){
        return Verificaciones.Datos.cadena(username) && Verificaciones.Datos.cadena(contrasenia) ? AutenticacionDAO.verificarLoginEscritorio(username, contrasenia) : (RespuestaUsuario) Verificaciones.Excepciones.badRequest();
    }
    
    @POST
    @Path("loginMovil")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaCliente loginMovil(@FormParam("correo") String correo,
                                       @FormParam("contrasenia") String contrasenia){
        return Verificaciones.Datos.cadena(correo) && Verificaciones.Datos.cadena(contrasenia) ? AutenticacionDAO.verificarLoginMovil(correo, contrasenia) : (RespuestaCliente) Verificaciones.Excepciones.badRequest();
    }
}