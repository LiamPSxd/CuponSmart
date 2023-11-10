package cuponsmart.modelo.pojo.respuesta;

import utils.Constantes;

public class Mensaje{
    private Boolean error;
    private String mensaje;
    
    public Mensaje(){
        this.error = true;
    }

    public Mensaje(Boolean error, String mensaje){
        this.error = error;
        this.mensaje = mensaje;
    }

    public Boolean getError(){
        return error;
    }

    public void setError(Boolean error){
        this.error = error;
    }

    public String getMensaje(){
        return mensaje;
    }

    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }
    
    public void mensajeSuccess(){
        this.mensaje = Constantes.Retornos.SUCCESS;
    }
    
    public void mensajeSinDatos(){
        this.mensaje = Constantes.Retornos.SIN_DATOS;
    }
    
    public void mensajeNoId(){
        this.mensaje = Constantes.Retornos.NO_ID;
    }
    
    public void mensajeSinConexionBD(){
        this.mensaje = Constantes.Errores.SIN_CONEXION_BD;
    }
}