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
        resources.add(ws.PromocionSucursalWS.class);
        resources.add(ws.PromocionWS.class);
    }
}