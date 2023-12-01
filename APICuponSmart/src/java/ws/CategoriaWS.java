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
import modelo.dao.CategoriaDAO;
import modelo.pojo.entidad.Categoria;
import modelo.pojo.respuesta.RespuestaCategoria;
import utils.Constantes;
import utils.Verificaciones;

@Path("categorias")
public class CategoriaWS{
    @Context
    private UriInfo context;
    
    @GET
    @Path("obtenerCategorias")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaCategoria obtenerCategorias(){
        return CategoriaDAO.obtenerCategorias();
    }
    
    @GET
    @Path("obtenerCategoriaPorId/{idCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaCategoria obtenerCategoriaPorId(@PathParam("idCategoria") Integer idCategoria){
        return Verificaciones.Datos.numerico(idCategoria) ? CategoriaDAO.obtenerCategoriaPorId(idCategoria) : (RespuestaCategoria) Verificaciones.Excepciones.badRequest();
    }
    
    @POST
    @Path("registrar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RespuestaCategoria registrarCategoria(String jsonParam){
        RespuestaCategoria respuesta = new RespuestaCategoria();
        
        try{
            Gson gson = new Gson();
            Categoria categoria = gson.fromJson(jsonParam, Categoria.class);
            
            if(Verificaciones.Datos.claseNoNula(categoria) && Verificaciones.Datos.cadena(categoria.getNombre())){
                respuesta = CategoriaDAO.registrarCategoria(categoria);
            }else{
                return (RespuestaCategoria) Verificaciones.Excepciones.badRequest();
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
    public RespuestaCategoria modificarCategoria(String jsonParam){
        RespuestaCategoria respuesta = new RespuestaCategoria();
        
        try{
            Gson gson = new Gson();
            Categoria categoria = gson.fromJson(jsonParam, Categoria.class);
            
            if(Verificaciones.Datos.claseNoNula(categoria) && Verificaciones.Datos.numerico(categoria.getId()))
                respuesta = CategoriaDAO.modificarCategoria(categoria);
            else{
                return (RespuestaCategoria) Verificaciones.Excepciones.badRequest();
            }
        }catch(JsonSyntaxException e){
            respuesta.setMensaje(Constantes.Excepciones.JSON_SYNTAX);
        }
        
        return respuesta;
    }
    
    @DELETE
    @Path("eliminar/{idCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaCategoria eliminarCategoria(@PathParam("idCategoria") Integer idCategoria){
        return Verificaciones.Datos.numerico(idCategoria) ? CategoriaDAO.eliminarCategoria(idCategoria) : (RespuestaCategoria) Verificaciones.Excepciones.badRequest();
    }
}