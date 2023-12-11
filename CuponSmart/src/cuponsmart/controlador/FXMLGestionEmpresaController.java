package cuponsmart.controlador;

import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.dao.EmpresaDAO;
import cuponsmart.modelo.pojo.entidad.Empresa;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.interfaz.IRespuesta;
import cuponsmart.vista.CuponSmart;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private Button btnRegresar;
    @FXML
    private Button imageBusqueda;
    @FXML
    private TextField txtBusqueda;
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
        
        Utilidades.colocarImagenBoton(getClass().getResource("/img/regresar.png"), btnRegresar);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/busqueda.png"), imageBusqueda);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/registrar.png"), btnRegistrar);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/modificar.png"), btnModificar);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/eliminar.png"), btnEliminar);
        configurarTabla();
    }
    
    private void configurarTabla(){
        clmNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        clmRepresentante.setCellValueFactory(new PropertyValueFactory("nombreRepresentanteLegal"));
        clmCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        clmTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        clmRFC.setCellValueFactory(new PropertyValueFactory("rfc"));
        clmEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
    }
    
    private void cargarEmpresas(List<Empresa> empresas){
        if(Verificaciones.listaNoVacia(empresas)){
            empresas.forEach((empresa) -> {
                empresa.setEstatus(
                    CatalogoDAO.obtenerEstatusPorId(empresa.getIdEstatus()).getEstado()
                );
            });

            this.empresas.clear();
            this.empresas.addAll(empresas);

            tbEmpresas.setItems(this.empresas);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "No se pudieron cargar las empresas, por favor inténtelo más tarde", Alert.AlertType.WARNING);
    }
    
    private void inicializarBusqueda(){
        if(this.empresas != null){
            FilteredList<Empresa> filtro = new FilteredList(this.empresas, p -> true);
            
            txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
                filtro.setPredicate((busqueda) -> {
                    if(newValue == null || newValue.isEmpty()) return true;
                    
                    String lower = newValue.toLowerCase();
                    
                    if(busqueda.getNombre().toLowerCase().contains(lower)) return true;
                    
                    if(busqueda.getRfc().toLowerCase().contains(lower)) return true;
                    
                    if(busqueda.getNombreRepresentanteLegal().toLowerCase().contains(lower)) return true;
                    
                    return false;
                });
                
                SortedList<Empresa> empresasFiltradas = new SortedList(filtro);
                empresasFiltradas.comparatorProperty().bind(tbEmpresas.comparatorProperty());
                tbEmpresas.setItems(empresasFiltradas);
            });
        }
    }
    
    public void inicializarInformacion(List<Empresa> empresas){
        cargarEmpresas(empresas);
        inicializarBusqueda();
    }
    
    @Override
    public void notificarGuardado(){
        cargarEmpresas(EmpresaDAO.obtenerEmpresas());
    }
    
    @FXML
    private void regresar(ActionEvent event){
        Stage escenario = (Stage) txtBusqueda.getScene().getWindow();
        escenario.close();
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
        
        if(Verificaciones.claseNoNula(empresa)) irPantallaFormEmpresa(empresa);
        else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una empresa para su modificación", Alert.AlertType.WARNING);
    }

    @FXML
    private void eliminarEmpresa(ActionEvent event){
        Empresa empresa = tbEmpresas.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.claseNoNula(empresa)){
            if(Utilidades.mostrarAlertaConfirmacion(Constantes.Pantallas.CONFIRMAR_ELIMINACION, "¿Está seguro de eliminar la empresa " + empresa.getNombreComercial() + "?")){
                Mensaje mensaje = EmpresaDAO.eliminarEmpresa(empresa.getId());
                
                if(!mensaje.getError()){
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Empresa eliminada exitosamente", Alert.AlertType.INFORMATION);
                    notificarGuardado();
                }else
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una empresa para su eliminación", Alert.AlertType.WARNING);
    }
}