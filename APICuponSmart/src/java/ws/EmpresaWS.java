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
import modelo.EmpresaDAO;
import modelo.pojo.entidad.Empresa;
import modelo.pojo.respuesta.RespuestaEmpresa;
import utils.Constantes;
import utils.Verificaciones;

@Path("empresas")
public class EmpresaWS {
    @Context
    private UriInfo context;
    
    @GET
    @Path("obtenerEmpresas")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEmpresa obtenerEmpresas(){
        return EmpresaDAO.obtenerEmpresas();
    }
    
    @GET
    @Path("obtenerEmpresaPorId/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEmpresa obtenerEmpresaPorId(@PathParam("idEmpresa") Integer idEmpresa){
        return Verificaciones.Datos.numerico(idEmpresa) ? EmpresaDAO.obtenerEmpresaPorId(idEmpresa): (RespuestaEmpresa) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerEmpresaPorNombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEmpresa obtenerEmpresaPorNombre(@PathParam("nombre") String nombre){
        return Verificaciones.Datos.cadena(nombre) ? EmpresaDAO.obtenerEmpresaPorCadena("Nombre", nombre): (RespuestaEmpresa) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerEmpresaPorRFC/{RFC}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEmpresa obtenerEmpresaPorRFC(@PathParam("RFC") String RFC){
        return Verificaciones.Datos.cadena(RFC) ? EmpresaDAO.obtenerEmpresaPorCadena("RFC", RFC): (RespuestaEmpresa) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerEmpresaPorRepresentanteLegal/{representanteLegal}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEmpresa obtenerEmpresaPorRepresentante(@PathParam("representanteLegal") String representanteLegal){
        return Verificaciones.Datos.cadena(representanteLegal) ? EmpresaDAO.obtenerEmpresaPorCadena("Representante", representanteLegal): (RespuestaEmpresa) Verificaciones.Excepciones.badRequest();
    }    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaEmpresa registrarEmpresa(String jsonParam){
        RespuestaEmpresa respuesta = new RespuestaEmpresa();
        try{
            Gson gson = new Gson();
            Empresa empresa = gson.fromJson(jsonParam, Empresa.class);
            if(Verificaciones.Datos.claseNoNula(empresa) && Verificaciones.Datos.numerico(empresa.getIdDireccion())){
                respuesta = EmpresaDAO.registrarEmpresa(empresa);
            }else{
                return (RespuestaEmpresa) Verificaciones.Excepciones.badRequest();
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
    public RespuestaEmpresa modificarEmpresa(String jsonParam){
        RespuestaEmpresa respuesta = new RespuestaEmpresa();
        
        try{
            Gson gson = new Gson();
            Empresa empresa = gson.fromJson(jsonParam, Empresa.class);
            if(Verificaciones.Datos.claseNoNula(empresa) && Verificaciones.Datos.numerico(empresa.getId())){
                respuesta = EmpresaDAO.modificarEmpresa(empresa);
            }else{
                return (RespuestaEmpresa) Verificaciones.Excepciones.badRequest();
            }            
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        return respuesta;
    }
    
    @DELETE
    @Path("eliminar/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEmpresa eliminarEmpresa(@PathParam("idEmpresa") Integer idEmpresa){
        return Verificaciones.Datos.numerico(idEmpresa) ? EmpresaDAO.eliminarEmpresa(idEmpresa) : (RespuestaEmpresa) Verificaciones.Excepciones.badRequest();
    }
}
