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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
        configurarTabla();
        
        comboFiltro.getItems().addAll("Nombre", "RFC", "Representante Legal");
    }
    
    private void configurarTabla(){
        clmNombre.setCellFactory(new PropertyValueFactory("nombre"));
        clmRepresentante.setCellFactory(new PropertyValueFactory("nombreRepresentanteLegal"));
        clmCorreo.setCellFactory(new PropertyValueFactory("correo"));
        clmTelefono.setCellFactory(new PropertyValueFactory("telefono"));
        clmRFC.setCellFactory(new PropertyValueFactory("rfc"));
        clmEstatus.setCellFactory(new PropertyValueFactory("estatus"));
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
                    resultado.add(EmpresaDAO.obtenerEmpresaPorNombre(busqueda));
                    inicializarInformacion(resultado);
                    break;
                case "RFC":
                    resultado.add(EmpresaDAO.obtenerEmpresaPorRFC(busqueda));
                    inicializarInformacion(resultado);
                    break;
                case "Representante Legal":
                    resultado.add(EmpresaDAO.obtenerEmpresaPorRepresentanteLegal(busqueda));
                    inicializarInformacion(resultado);
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
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, mensaje.getMensaje(), Alert.AlertType.INFORMATION);
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