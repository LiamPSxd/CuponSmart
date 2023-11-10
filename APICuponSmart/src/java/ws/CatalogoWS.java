package ws;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import modelo.EstadoDAO;
import modelo.EstatusDAO;
import modelo.MunicipioDAO;
import modelo.RolDAO;
import modelo.TipoPromocionDAO;
import modelo.pojo.respuesta.RespuestaEstado;
import modelo.pojo.respuesta.RespuestaEstatus;
import modelo.pojo.respuesta.RespuestaMunicipio;
import modelo.pojo.respuesta.RespuestaRol;
import modelo.pojo.respuesta.RespuestaTipoPromocion;
import utils.Verificaciones;


@Path("catalogo")
public class CatalogoWS {

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
        if(Verificaciones.Datos.numerico(idEstado)){
            return EstadoDAO.obtenerEstadoPorId(idEstado);
        }else{
            return (RespuestaEstado) Verificaciones.Excepciones.badRequest();
        }
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
        if(Verificaciones.Datos.numerico(idEstatus)){
            return EstatusDAO.obtenerEstatusPorId(idEstatus);
        }else{
            return (RespuestaEstatus) Verificaciones.Excepciones.badRequest();
        }
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
        if(Verificaciones.Datos.numerico(idRol)){
            return RolDAO.obtenerRolPorId(idRol);
        }else{
            return (RespuestaRol) Verificaciones.Excepciones.badRequest();
        }
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
        if(Verificaciones.Datos.numerico(idTipoPromocion)){
            return TipoPromocionDAO.obtenerTipoPromocionPorId(idTipoPromocion);
        }else{
            return (RespuestaTipoPromocion) Verificaciones.Excepciones.badRequest();
        }
    }
    
    @GET
    @Path("obtenerMunicipios")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaMunicipio obtenerMunicipios(){
        return MunicipioDAO.obtenerMunicipios();
    }
    
    @GET
    @Path("obtenerMunicipio/{idMunicipio}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaMunicipio obtenerMunicipio(@PathParam("idMunicipio") Integer idMunicipio){
        if(Verificaciones.Datos.numerico(idMunicipio)){
            return MunicipioDAO.obtenerMunicipioPorId(idMunicipio);
        }else{
            return (RespuestaMunicipio) Verificaciones.Excepciones.badRequest();
        }
    }
    @GET
    @Path("obtenerMunicipiosPorEstado/{idEstado}")
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaMunicipio obtenerMunicipiosPorEstado(@PathParam("idEstado") Integer idEstado){
        if(Verificaciones.Datos.numerico(idEstado)){
            return MunicipioDAO.obtenerMunicipiosPorEstado(idEstado);
        }else{
            return (RespuestaMunicipio) Verificaciones.Excepciones.badRequest();
        }
    }
}
