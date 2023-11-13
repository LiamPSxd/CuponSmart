package ws;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application{
    @Override
    public Set<Class<?>> getClasses(){
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources){
        resources.add(ws.AutenticacionWS.class);
        resources.add(ws.CatalogoWS.class);
        resources.add(ws.CategoriaWS.class);
        resources.add(ws.CiudadWS.class);
        resources.add(ws.ClienteWS.class);
<<<<<<< HEAD
        resources.add(ws.DireccionWS.class);
        resources.add(ws.EmpresaWS.class);
        resources.add(ws.PromocionWS.class);
        resources.add(ws.UsuarioWS.class);
=======
        resources.add(ws.PromocionSucursalWS.class);
        resources.add(ws.PromocionWS.class);
        resources.add(ws.SucursalWS.class);
>>>>>>> b7265d0ef83a6c6627d6e638d6dd47259e6a0d36
    }
}