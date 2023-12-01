package ws;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import modelo.dao.MediaDAO;
import modelo.pojo.respuesta.RespuestaEmpresa;
import modelo.pojo.respuesta.RespuestaPromocion;
import utils.Verificaciones;

@Path("media")
public class MediaWS{
    @Context
    private UriInfo context;
    
    @GET
    @Path("obtenerLogoEmpresa/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEmpresa obtenerLogoEmpresa(@PathParam("idEmpresa") Integer idEmpresa){
        return Verificaciones.Datos.numerico(idEmpresa) ? MediaDAO.obtenerLogoEmpresa(idEmpresa) : (RespuestaEmpresa) Verificaciones.Excepciones.badRequest();
    }
    
    @PUT
    @Path("registrarLogoEmpresa/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEmpresa registrarLogoEmpresa(@PathParam("idEmpresa") Integer idEmpresa,
                                                 Byte[] logo){
        return Verificaciones.Datos.numerico(idEmpresa) && Verificaciones.Datos.listaByteNoVacio(logo) ? MediaDAO.registrarLogoEmpresa(idEmpresa, logo) : (RespuestaEmpresa) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerImagenPromocion/{idPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocion obtenerImagenPromocion(@PathParam("idPromocion") Integer idPromocion){
        return Verificaciones.Datos.numerico(idPromocion) ? MediaDAO.obtenerImagenPromocion(idPromocion) : (RespuestaPromocion) Verificaciones.Excepciones.badRequest();
    }
    
    @PUT
    @Path("registrarImagenPromocion/{idPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaPromocion registrarImagenPromocion(@PathParam("idPromocion") Integer idPromocion,
                                                 Byte[] imagen){
        return Verificaciones.Datos.numerico(idPromocion) && Verificaciones.Datos.listaByteNoVacio(imagen) ? MediaDAO.registrarImagenPromocion(idPromocion, imagen) : (RespuestaPromocion) Verificaciones.Excepciones.badRequest();
    }
}