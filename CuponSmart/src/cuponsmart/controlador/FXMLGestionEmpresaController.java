package cuponsmart.controlador;

import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.dao.EmpresaDAO;
import cuponsmart.modelo.pojo.entidad.Empresa;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.interfaz.IRespuesta;
import cuponsmart.vista.CuponSmart;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Constantes;
import utils.Utilidades;
import utils.Verificaciones;

public class FXMLGestionEmpresaController implements Initializable, IRespuesta{
    private ObservableList<Empresa> empresas;
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private ComboBox<String> comboFiltro;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<Empresa> tbEmpresas;
    @FXML
    private TableColumn clmNombre;
    @FXML
    private TableColumn clmRepresentante;
    @FXML
    private TableColumn clmCorreo;
    @FXML
    private TableColumn clmTelefono;
    @FXML
    private TableColumn clmRFC;
    @FXML
    private TableColumn clmEstatus;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.empresas = FXCollections.observableArrayList();
        
        colocarImagenBoton("/img/registrar.png", btnRegistrar);
        colocarImagenBoton("/img/modificar.png", btnModificar);
        colocarImagenBoton("/img/eliminar.png", btnEliminar);
        configurarTabla();
        
        comboFiltro.getItems().addAll("Nombre", "RFC", "Representante Legal");
    }
    
    private void colocarImagenBoton(String resource, Button boton){
        URL url = getClass().getResource(resource);
        Image imagen = new Image(url.toString(), 32, 32, false, true);
        
        boton.setGraphic(new ImageView(imagen));
    }
    
    private void configurarTabla(){
        clmNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        clmRepresentante.setCellValueFactory(new PropertyValueFactory("nombreRepresentanteLegal"));
        clmCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        clmTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        clmRFC.setCellValueFactory(new PropertyValueFactory("rfc"));
        clmEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
    }
    
    public void inicializarInformacion(List<Empresa> empresas){
        empresas.forEach((empresa) -> {
            empresa.setEstatus(
                CatalogoDAO.obtenerEstatusPorId(empresa.getIdEstatus()).getEstado()
            );
        });
        
        this.empresas.clear();
        this.empresas.addAll(empresas);
        
        tbEmpresas.setItems(this.empresas);
    }
    
    @Override
    public void notificarGuardado(){
        inicializarInformacion(EmpresaDAO.obtenerEmpresas());
    }

    @FXML
    private void buscarEmpresa(ActionEvent event){
        String busqueda = txtBusqueda.getText();
        String filtro = comboFiltro.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.cadena(busqueda) && Verificaciones.Datos.cadena(filtro)){
            List<Empresa> resultado = new ArrayList();
            
            switch(filtro){
                case "Nombre":
                    inicializarInformacion(EmpresaDAO.obtenerEmpresasPorNombre(busqueda));
                    break;
                case "RFC":
                    inicializarInformacion(EmpresaDAO.obtenerEmpresasPorRFC(busqueda));
                    break;
                case "Representante Legal":
                    inicializarInformacion(EmpresaDAO.obtenerEmpresasPorRepresentanteLegal(busqueda));
                    break;
                default:
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, "Filtro no encontrado", Alert.AlertType.ERROR);
            }
        }else if(!Verificaciones.Datos.cadena(busqueda)){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Retornos.BUSQUEDA, Alert.AlertType.WARNING);
        }else if(!Verificaciones.Datos.cadena(filtro)){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Retornos.FILTRO, Alert.AlertType.WARNING);
        }else{
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Retornos.BUSQUEDA_FILTRO, Alert.AlertType.WARNING);
        }
    }
    
    private void irPantallaFormEmpresa(Empresa empresa){
        try{
            FXMLLoader carga = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLFormEmpresa.fxml"));
            Parent vista = carga.load();
            
            FXMLFormEmpresaController controlador = carga.getController();
            controlador.inicializarInformacion(empresa, this);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.setTitle(Constantes.Pantallas.FORM_EMPRESA);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.IO, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void registrarEmpresa(ActionEvent event){
        irPantallaFormEmpresa(null);
    }

    @FXML
    private void modificarEmpresa(ActionEvent event){
        Empresa empresa = tbEmpresas.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(empresa)){
            irPantallaFormEmpresa(empresa);
        }else{
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una empresa para su modificación", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void eliminarEmpresa(ActionEvent event){
        Empresa empresa = tbEmpresas.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(empresa)){
            if(Utilidades.mostrarAlertaConfirmacion(Constantes.Pantallas.CONFIRMAR_ELIMINACION, "¿Está seguro de eliminar la empresa " + empresa.getNombreComercial() + "?")){
                Mensaje mensaje = EmpresaDAO.eliminarEmpresa(empresa.getId());
                
                if(!mensaje.getError()){
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Empresa eliminada exitosamente", Alert.AlertType.INFORMATION);
                    notificarGuardado();
                }else{
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
                }
            }
        }else{
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una empresa para su eliminación", Alert.AlertType.WARNING);
        }
    }
}