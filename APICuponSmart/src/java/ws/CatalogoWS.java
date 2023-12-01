package ws;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.dao.EstadoDAO;
import modelo.dao.EstatusDAO;
import modelo.dao.MunicipioDAO;
import modelo.dao.RolDAO;
import modelo.dao.TipoPromocionDAO;
import modelo.pojo.respuesta.RespuestaEstado;
import modelo.pojo.respuesta.RespuestaEstatus;
import modelo.pojo.respuesta.RespuestaMunicipio;
import modelo.pojo.respuesta.RespuestaRol;
import modelo.pojo.respuesta.RespuestaTipoPromocion;
import utils.Verificaciones;

@Path("catalogo")
public class CatalogoWS{
    @Context
    private UriInfo context;

    @GET
    @Path("obtenerEstados")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEstado obtenerEstados(){
        return EstadoDAO.obtenerEstados();
    }
    
    @GET
    @Path("obtenerEstadoPorId/{idEstado}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEstado obtenerEstadoPorId(@PathParam("idEstado") Integer idEstado){
        return Verificaciones.Datos.numerico(idEstado) ? EstadoDAO.obtenerEstadoPorId(idEstado) : (RespuestaEstado) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerEstatus")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEstatus obtenerEstatus(){
        return EstatusDAO.obtenerEstatus();
    }
    
    @GET
    @Path("obtenerEstatusPorId/{idEstatus}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaEstatus obtenerEstatusPorId(@PathParam("idEstatus") Integer idEstatus){
        return Verificaciones.Datos.numerico(idEstatus) ? EstatusDAO.obtenerEstatusPorId(idEstatus) : (RespuestaEstatus) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerRoles")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaRol obtenerRoles(){
        return RolDAO.obtenerRoles();
    }
    
    @GET
    @Path("obtenerRolPorId/{idRol}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaRol obtenerRolPorId(@PathParam("idRol") Integer idRol){
        return Verificaciones.Datos.numerico(idRol) ? RolDAO.obtenerRolPorId(idRol) : (RespuestaRol) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerTiposPromocion")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaTipoPromocion obtenerTiposPromocion(){
        return TipoPromocionDAO.obtenerTiposPromocion();
    }
    
    @GET
    @Path("obtenerTipoPromocionPorId/{idTipoPromocion}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaTipoPromocion obtenerTipoPromocion(@PathParam("idTipoPromocion") Integer idTipoPromocion){
        return Verificaciones.Datos.numerico(idTipoPromocion) ? TipoPromocionDAO.obtenerTipoPromocionPorId(idTipoPromocion) : (RespuestaTipoPromocion) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerMunicipios")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaMunicipio obtenerMunicipios(){
        return MunicipioDAO.obtenerMunicipios();
    }
    
    @GET
    @Path("obtenerMunicipioPorId/{idMunicipio}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaMunicipio obtenerMunicipioPorId(@PathParam("idMunicipio") Integer idMunicipio){
        return Verificaciones.Datos.numerico(idMunicipio) ? MunicipioDAO.obtenerMunicipioPorId(idMunicipio) : (RespuestaMunicipio) Verificaciones.Excepciones.badRequest();
    }
    
    @GET
    @Path("obtenerMunicipiosPorEstado/{idEstado}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaMunicipio obtenerMunicipiosPorEstado(@PathParam("idEstado") Integer idEstado){
        return Verificaciones.Datos.numerico(idEstado) ? MunicipioDAO.obtenerMunicipiosPorEstado(idEstado) : (RespuestaMunicipio) Verificaciones.Excepciones.badRequest();
    }
}