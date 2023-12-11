package cuponsmart.controlador;

import cuponsmart.modelo.dao.AutenticacionDAO;
import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.pojo.entidad.Rol;
import cuponsmart.modelo.pojo.entidad.Usuario;
import cuponsmart.modelo.pojo.respuesta.RespuestaUsuario;
import cuponsmart.vista.CuponSmart;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Constantes;
import utils.Utilidades;
import utils.Verificaciones;

public class FXMLInicioSesionController implements Initializable{
    @FXML
    private ImageView imagen;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContrasenia;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){}
    
    private void irPantallaMainAdmin(Usuario usuario){
        Stage stage = (Stage) txtUsuario.getScene().getWindow();
        
        try{
            Rol rol = CatalogoDAO.obtenerRolPorId(usuario.getIdRol());
            
            switch(rol.getNombre()){
                case Constantes.Retornos.ADMIN_GENERAL:
                    FXMLLoader cargaGeneral = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLMainAdminGeneral.fxml"));
                    Parent vistaGeneral = cargaGeneral.load();
                    
                    FXMLMainAdminGeneralController controladorGeneral = cargaGeneral.getController();
                    controladorGeneral.inicializarInformacion(usuario);
                    
                    stage.setScene(new Scene(vistaGeneral));
                    stage.setTitle(Constantes.Pantallas.ADMIN_GENERAL);
                    stage.show();
                    break;
                case Constantes.Retornos.ADMIN_COMERCIAL:
                    FXMLLoader cargaComercial = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLMainAdminComercial.fxml"));
                    Parent vistaComercial = cargaComercial.load();
                    
                    FXMLMainAdminComercialController controladorComercial = cargaComercial.getController();
                    controladorComercial.inicializarInformacion(usuario);
                    
                    stage.setScene(new Scene(vistaComercial));
                    stage.setTitle(Constantes.Pantallas.ADMIN_COMERCIAL);
                    stage.show();
                    break;
                default:
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, "Pantalla no encontrada", Alert.AlertType.ERROR);
            }
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.IO, Alert.AlertType.ERROR);
        }
    }
    
    private void verificarCredenciales(String usuario, String contrasenia){
        RespuestaUsuario respuesta = AutenticacionDAO.iniciarSesion(usuario, contrasenia);
        
        if(!respuesta.getError()){
            Utilidades.mostrarAlertaSimple("Credenciales correctas", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            
            irPantallaMainAdmin(respuesta.getContenido().get(0));
        }else
            Utilidades.mostrarAlertaSimple("Credenciales incorrectas", respuesta.getMensaje(), Alert.AlertType.ERROR);
    }

    @FXML
    private void iniciarSesion(ActionEvent event){
        String usuario = txtUsuario.getText();
        String contrasenia = txtContrasenia.getText();
        
        if(Verificaciones.cadena(usuario) && Verificaciones.cadena(contrasenia)){
            verificarCredenciales(usuario, contrasenia);
        }else if(!Verificaciones.cadena(usuario))
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.CAMPO_VACIO, "El usuario es obligatorio", Alert.AlertType.WARNING);
        else if(!Verificaciones.cadena(contrasenia))
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.CAMPO_VACIO, "La contraseña es obligatoria", Alert.AlertType.WARNING);
        else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.CAMPO_VACIO, "El usuario y la contraseña son obligatoria", Alert.AlertType.WARNING);
    }
}