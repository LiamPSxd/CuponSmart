package cuponsmart.controlador;

import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.dao.CategoriaDAO;
import cuponsmart.modelo.dao.EmpresaDAO;
import cuponsmart.modelo.dao.PromocionDAO;
import cuponsmart.modelo.pojo.entidad.Promocion;
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

public class FXMLGestionPromocionController implements Initializable, IRespuesta{
    private ObservableList<Promocion> promociones;
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
    private TableView<Promocion> tbPromociones;
    @FXML
    private TableColumn clmNombre;
    @FXML
    private TableColumn clmDescripcion;
    @FXML
    private TableColumn clmFechaInicio;
    @FXML
    private TableColumn clmFechaTermino;
    @FXML
    private TableColumn clmCodigo;
    @FXML
    private TableColumn clmNumero;
    @FXML
    private TableColumn clmEstatus;
    @FXML
    private TableColumn clmCategoria;
    @FXML
    private TableColumn clmEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.promociones = FXCollections.observableArrayList();
        
        Utilidades.colocarImagenBoton(getClass().getResource("/img/busqueda.png"), imageBusqueda);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/registrar.png"), btnRegistrar);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/modificar.png"), btnModificar);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/eliminar.png"), btnEliminar);
        configurarTabla();
    }
    
    private void configurarTabla(){
        clmNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        clmDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        clmFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        clmFechaTermino.setCellValueFactory(new PropertyValueFactory("fechaTermino"));
        clmCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        clmNumero.setCellValueFactory(new PropertyValueFactory("numeroCupones"));
        clmEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        clmCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        clmEmpresa.setCellValueFactory(new PropertyValueFactory("empresa"));
    }
    
    private void cargarPromociones(List<Promocion> promociones){
        if(Verificaciones.Datos.listaNoVacia(promociones)){
            promociones.forEach((promocion) -> {
                promocion.setEstatus(
                    CatalogoDAO.obtenerEstatusPorId(promocion.getIdEstatus()).getEstado()
                );

                promocion.setCategoria(
                    CategoriaDAO.obtenerCategoriaPorId(promocion.getIdCategoria()).getNombre()
                );

                promocion.setEmpresa(
                    EmpresaDAO.obtenerEmpresaPorId(promocion.getIdEmpresa()).getNombreComercial()
                );
            });

            this.promociones.clear();
            this.promociones.addAll(promociones);

            tbPromociones.setItems(this.promociones);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "No se pudieron cargar las promociones, por favor inténtelo más tarde", Alert.AlertType.WARNING);
    }
    
    private void inicializarBusqueda(){
        if(this.promociones != null){
            FilteredList<Promocion> filtro = new FilteredList(this.promociones, p -> true);
            
            txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
                filtro.setPredicate((busqueda) -> {
                    if(newValue == null || newValue.isEmpty()) return true;
                    
                    String lower = newValue.toLowerCase();
                    
                    if(busqueda.getFechaInicio().toLowerCase().contains(lower)) return true;
                    
                    if(busqueda.getFechaTermino().toLowerCase().contains(lower)) return true;
                    
                    if(busqueda.getNombre().toLowerCase().contains(lower)) return true;
                    
                    return false;
                });
                
                SortedList<Promocion> promocionesFiltradas = new SortedList(filtro);
                promocionesFiltradas.comparatorProperty().bind(tbPromociones.comparatorProperty());
                tbPromociones.setItems(promocionesFiltradas);
            });
        }
    }
    
    public void inicializarInformacion(List<Promocion> promociones, Usuario administradorComercial){
        this.administradorComercial = administradorComercial;
        
        cargarPromociones(promociones);
        inicializarBusqueda();
    }
    
    @Override
    public void notificarGuardado(){
        cargarPromociones(
            Verificaciones.Datos.claseNula(this.administradorComercial) ? PromocionDAO.obtenerPromociones() : PromocionDAO.obtenerPromocionesPorIdEmpresa(this.administradorComercial.getIdEmpresa())
        );
    }
    
    private void irPantallaFormPromocion(Promocion promocion, Integer idEmpresa){
        try{
            FXMLLoader carga = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLFormPromocion.fxml"));
            Parent vista = carga.load();
            
            FXMLFormPromocionController controlador = carga.getController();
            controlador.inicializarInformacion(promocion, idEmpresa, this);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.setTitle(Constantes.Pantallas.FORM_PROMOCION);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.IO, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void registrarPromocion(ActionEvent event){
        irPantallaFormPromocion(
            null,
            Verificaciones.Datos.claseNula(this.administradorComercial) ? 0 : this.administradorComercial.getId()
        );
    }

    @FXML
    private void modificarPromocion(ActionEvent event){
        Promocion promocion = tbPromociones.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(promocion))
            irPantallaFormPromocion(
                promocion,
                Verificaciones.Datos.claseNula(this.administradorComercial) ? 0 : this.administradorComercial.getId()
            );
        else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una promoción para su modificación", Alert.AlertType.WARNING);
    }

    @FXML
    private void eliminarPromocion(ActionEvent event){
        Promocion promocion = tbPromociones.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(promocion))
            if(Utilidades.mostrarAlertaConfirmacion(Constantes.Pantallas.CONFIRMAR_ELIMINACION, "¿Está seguro de eliminar la promoción " + promocion.getNombre() + "?")){
                Mensaje mensaje = PromocionDAO.eliminarPromocion(promocion.getId());
                
                if(!mensaje.getError()){
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Promoción eliminada exitosamente", Alert.AlertType.INFORMATION);
                    notificarGuardado();
                }else
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
        else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una promoción para su eliminación", Alert.AlertType.WARNING);
    }
}