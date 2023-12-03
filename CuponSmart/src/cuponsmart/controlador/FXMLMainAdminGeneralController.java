package cuponsmart.controlador;

import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.dao.EmpresaDAO;
import cuponsmart.modelo.dao.PromocionDAO;
import cuponsmart.modelo.dao.SucursalDAO;
import cuponsmart.modelo.dao.UsuarioDAO;
import cuponsmart.modelo.pojo.entidad.Promocion;
import cuponsmart.modelo.pojo.entidad.Usuario;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Constantes;
import utils.Utilidades;
import utils.Verificaciones;

public class FXMLMainAdminGeneralController implements Initializable{
    private Usuario administrador;
    
    @FXML
    private Label etqNombreAdmin;
    @FXML
    private ImageView imagenEmpresa;
    @FXML
    private ImageView imagenSucursal;
    @FXML
    private ImageView imagenPromocion;
    @FXML
    private ImageView imagenUsuario;
    @FXML
    private TextField txtCodigo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
    }
    
    public void inicializarInformacion(Usuario administrador){
        this.administrador = administrador;
        
        etqNombreAdmin.setText(this.administrador.getNombre() + " " + this.administrador.getApellidoPaterno() + " " + this.administrador.getApellidoMaterno());
    }
    
    @FXML
    private void irPantallaGestionEmpresa(MouseEvent event){
        try{
            FXMLLoader carga = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLGestionEmpresa.fxml"));
            Parent vista = carga.load();
            
            FXMLGestionEmpresaController controlador = carga.getController();
            controlador.inicializarInformacion(EmpresaDAO.obtenerEmpresas());

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.setTitle(Constantes.Pantallas.GESTION_EMPRESA);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.IO, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irPantallaGestionSucursal(MouseEvent event){
        try{
            FXMLLoader carga = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLGestionSucursal.fxml"));
            Parent vista = carga.load();
            
            FXMLGestionSucursalController controlador = carga.getController();
            controlador.inicializarInformacion(SucursalDAO.obtenerSucursales(), null);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.setTitle(Constantes.Pantallas.GESTION_SUCURSAL);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.IO, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irPantallaGestionPromocion(MouseEvent event){
        try{
            FXMLLoader carga = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLGestionPromocion.fxml"));
            Parent vista = carga.load();
            
            FXMLGestionPromocionController controlador = carga.getController();
            controlador.inicializarInformacion(PromocionDAO.obtenerPromociones(), null);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.setTitle(Constantes.Pantallas.GESTION_PROMOCION);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.IO, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irPantallaGestionUsuario(MouseEvent event){
        try{
            FXMLLoader carga = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLGestionUsuario.fxml"));
            Parent vista = carga.load();
            
            FXMLGestionUsuarioController controlador = carga.getController();
            controlador.inicializarInformacion(UsuarioDAO.obtenerUsuarios());

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.setTitle(Constantes.Pantallas.GESTION_USUARIO);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.IO, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void canjearCupon(ActionEvent event){
        String codigo = txtCodigo.getText();
        
        if(Verificaciones.Datos.cadena(codigo)){
            if(codigo.length() == 8){
                Promocion cupon = PromocionDAO.obtenerPromocionPorCodigo(codigo);

                if(Verificaciones.Datos.claseNoNula(cupon) && Verificaciones.Datos.numerico(cupon.getNumeroCupones())){
                    cupon.setNumeroCupones(cupon.getNumeroCupones() - 1);

                    if(cupon.getNumeroCupones() == 0){
                        CatalogoDAO.obtenerEstatus().forEach((estatus) -> {
                            if(estatus.getEstado().equals(Constantes.Retornos.ESTATUS_INACTIVO)){
                                cupon.setIdEstatus(estatus.getId());
                            }
                        });
                    }

                    Mensaje mensaje = PromocionDAO.modificarPromocion(cupon);
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, Constantes.Retornos.CUPON_EXITO, Alert.AlertType.INFORMATION);
                }else if(Verificaciones.Datos.numerico(cupon.getNumeroCupones()))
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Retornos.CUPON_INACTIVO, Alert.AlertType.WARNING);
                else
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Retornos.CUPON_FALLO, Alert.AlertType.WARNING);
            }else
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Retornos.CUPON_LONG, Alert.AlertType.WARNING);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.CAMPO_VACIO, Constantes.Retornos.CUPON_FALTANTE, Alert.AlertType.WARNING);
    }
}