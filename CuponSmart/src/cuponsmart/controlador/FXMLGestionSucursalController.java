package cuponsmart.controlador;

import cuponsmart.modelo.dao.DireccionDAO;
import cuponsmart.modelo.dao.EmpresaDAO;
import cuponsmart.modelo.dao.SucursalDAO;
import cuponsmart.modelo.pojo.entidad.Direccion;
import cuponsmart.modelo.pojo.entidad.Sucursal;
import cuponsmart.modelo.pojo.entidad.Usuario;
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

public class FXMLGestionSucursalController implements Initializable, IRespuesta{
    private ObservableList<Sucursal> sucursales;
    private Usuario administradorComercial;
    
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
    private TableView<Sucursal> tbSucursales;
    @FXML
    private TableColumn clmNombre;
    @FXML
    private TableColumn clmTelefono;
    @FXML
    private TableColumn clmEncargado;
    @FXML
    private TableColumn clmDireccion;
    @FXML
    private TableColumn clmEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.sucursales = FXCollections.observableArrayList();
        
        Utilidades.colocarImagenBoton(getClass().getResource("/img/registrar.png"), btnRegistrar);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/modificar.png"), btnModificar);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/eliminar.png"), btnEliminar);
        configurarTabla();
    }
    
    private void configurarTabla(){
        clmNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        clmTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        clmEncargado.setCellValueFactory(new PropertyValueFactory("nombreEncargado"));
        clmDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        clmEmpresa.setCellValueFactory(new PropertyValueFactory("empresa"));
    }
    
    private void cargarSucursales(List<Sucursal> sucursales){
        if(Verificaciones.Datos.listaNoVacia(sucursales)){
            sucursales.forEach((sucursal) -> {
                Direccion direccion = DireccionDAO.obtenerDireccionPorId(sucursal.getIdDireccion());
                sucursal.setDireccion(direccion.getCalle() + " " + direccion.getColonia() + " " + direccion.getNumero());

                sucursal.setEmpresa(
                    EmpresaDAO.obtenerEmpresaPorId(sucursal.getIdEmpresa()).getNombreComercial()
                );
            });

            this.sucursales.clear();
            this.sucursales.addAll(sucursales);

            tbSucursales.setItems(this.sucursales);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "No se pudieron cargar las sucursales, por favor inténtelo más tarde", Alert.AlertType.WARNING);
    }
    
    private void inicializarBusqueda(){
        if(this.sucursales != null){
            FilteredList<Sucursal> filtro = new FilteredList(this.sucursales, p -> true);
            
            txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
                filtro.setPredicate((busqueda) -> {
                    if(newValue == null || newValue.isEmpty()) return true;
                    
                    String lower = newValue.toLowerCase();
                    
                    if(busqueda.getNombre().toLowerCase().contains(lower)) return true;
                    
                    if(busqueda.getDireccion().toLowerCase().contains(lower)) return true;
                    
                    return false;
                });
                
                SortedList<Sucursal> sucursalesFiltradas = new SortedList(filtro);
                sucursalesFiltradas.comparatorProperty().bind(tbSucursales.comparatorProperty());
                tbSucursales.setItems(sucursalesFiltradas);
            });
        }
    }
    
    public void inicializarInformacion(List<Sucursal> sucursales, Usuario administradorComercial){
        this.administradorComercial = administradorComercial;
        
        cargarSucursales(sucursales);
        inicializarBusqueda();
    }
    
    @Override
    public void notificarGuardado(){
        cargarSucursales(
            Verificaciones.Datos.claseNula(this.administradorComercial) ? SucursalDAO.obtenerSucursales() : SucursalDAO.obtenerSucursalesPorIdEmpresa(this.administradorComercial.getIdEmpresa())
        );
    }
    
    private void irPantallaFormSucursal(Sucursal sucursal, Integer idEmpresa){
        try{
            FXMLLoader carga = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLFormSucursal.fxml"));
            Parent vista = carga.load();
            
            FXMLFormSucursalController controlador = carga.getController();
            controlador.inicializarInformacion(sucursal, idEmpresa, this);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.setTitle(Constantes.Pantallas.FORM_SUCURSAL);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.IO, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void registrarSucursal(ActionEvent event){
        irPantallaFormSucursal(
            null,
            Verificaciones.Datos.claseNula(this.administradorComercial) ? 0 : this.administradorComercial.getIdEmpresa()
        );
    }

    @FXML
    private void modificarSucursal(ActionEvent event){
        Sucursal sucursal = tbSucursales.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(sucursal))
            irPantallaFormSucursal(
                sucursal,
                Verificaciones.Datos.claseNula(this.administradorComercial) ? 0 : this.administradorComercial.getIdEmpresa()
            );
        else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una sucursal para su modificación", Alert.AlertType.WARNING);
    }

    @FXML
    private void eliminarSucursal(ActionEvent event){
        Sucursal sucursal = tbSucursales.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(sucursal))
            if(Utilidades.mostrarAlertaConfirmacion(Constantes.Pantallas.CONFIRMAR_ELIMINACION, "¿Está seguro de eliminar la sucursal " + sucursal.getNombre() + "?")){
                Mensaje mensaje = SucursalDAO.eliminarSucursal(sucursal.getId());
                
                if(!mensaje.getError()){
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Sucursal eliminada exitosamente", Alert.AlertType.INFORMATION);
                    notificarGuardado();
                }else
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
        else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una sucursal para su eliminación", Alert.AlertType.WARNING);
    }
}