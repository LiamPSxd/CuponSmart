package cuponsmart.controlador;

import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.dao.CategoriaDAO;
import cuponsmart.modelo.dao.EmpresaDAO;
import cuponsmart.modelo.dao.PromocionDAO;
import cuponsmart.modelo.dao.PromocionSucursalDAO;
import cuponsmart.modelo.pojo.entidad.Promocion;
import cuponsmart.modelo.pojo.entidad.Usuario;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.interfaz.IRespuesta;
import cuponsmart.vista.CuponSmart;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
    private Button btnRegresar;
    @FXML
    private Button imageBusqueda;
    @FXML
    private TextField txtBusqueda;
    @FXML
    private Button btnLista;
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
    private TableColumn clmCantidad;
    @FXML
    private TableColumn clmEstatus;
    @FXML
    private TableColumn clmCategoria;
    @FXML
    private TableColumn clmEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.promociones = FXCollections.observableArrayList();
        
        Utilidades.colocarImagenBoton(getClass().getResource("/img/regresar.png"), btnRegresar);
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
        clmCantidad.setCellValueFactory(new PropertyValueFactory("numeroCupones"));
        clmEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        clmCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
        clmEmpresa.setCellValueFactory(new PropertyValueFactory("empresa"));
    }
    
    private void cargarPromociones(List<Promocion> promociones){
        if(Verificaciones.listaNoVacia(promociones)){
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
            Verificaciones.claseNula(this.administradorComercial) ? PromocionDAO.obtenerPromociones() : PromocionDAO.obtenerPromocionesPorIdEmpresa(this.administradorComercial.getIdEmpresa())
        );
    }
    
    @FXML
    private void regresar(ActionEvent event){
        Stage escenario = (Stage) txtBusqueda.getScene().getWindow();
        escenario.close();
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
            Verificaciones.claseNula(this.administradorComercial) ? 0 : this.administradorComercial.getIdEmpresa()
        );
    }

    @FXML
    private void modificarPromocion(ActionEvent event){
        Promocion promocion = tbPromociones.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.claseNoNula(promocion))
            irPantallaFormPromocion(
                promocion,
                Verificaciones.claseNula(this.administradorComercial) ? 0 : this.administradorComercial.getIdEmpresa()
            );
        else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una promoción para su modificación", Alert.AlertType.WARNING);
    }

    @FXML
    private void eliminarPromocion(ActionEvent event){
        Promocion promocion = tbPromociones.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.claseNoNula(promocion)){
            if(Utilidades.mostrarAlertaConfirmacion(Constantes.Pantallas.CONFIRMAR_ELIMINACION, "¿Está seguro de eliminar la promoción " + promocion.getNombre() + "?")){
                Mensaje mensaje;
                
                if(Verificaciones.listaNoVacia(PromocionSucursalDAO.obtenerPromocionesSucursalesPorIdPromocion(promocion.getId()))){
                    mensaje = PromocionSucursalDAO.eliminarPromocionSucursales(promocion.getId());

                    if(mensaje.getError())
                        Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
                }
                
                mensaje = PromocionDAO.eliminarPromocion(promocion.getId());
                
                if(!mensaje.getError()){
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Promoción eliminada exitosamente", Alert.AlertType.INFORMATION);
                    notificarGuardado();
                }else
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una promoción para su eliminación", Alert.AlertType.WARNING);
    }

    @FXML
    private void mostrarCupones(ActionEvent event){
        List<Promocion> cupones = Verificaciones.claseNoNula(this.administradorComercial)
                ? PromocionDAO.obtenerPromocionesPorIdEmpresa(this.administradorComercial.getIdEmpresa())
                : PromocionDAO.obtenerPromociones();
        
        switch(btnLista.getText()){
            case "Lista de Cupones":
                List<Promocion> cuponesFiltrados = new ArrayList();
                
                cupones.forEach((cupon) -> {
                    try{
                        cupon.setEstatus(
                            CatalogoDAO.obtenerEstatusPorId(cupon.getIdEstatus()).getEstado()
                        );
                        
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        Date fechaActual = formato.parse(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
                        Date fechaTermino = formato.parse(cupon.getFechaTermino());

                        if(fechaTermino.after(fechaActual) &&
                            Verificaciones.numerico(cupon.getNumeroCupones()) &&
                            cupon.getEstatus().equals("Activo"))
                            cuponesFiltrados.add(cupon);
                    }catch(ParseException e){
                        Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.PARSE, Alert.AlertType.ERROR);
                    }
                });
                
                if(Verificaciones.listaNoVacia(cuponesFiltrados)){
                    cupones = cuponesFiltrados;
                    btnLista.setText("Lista de Promociones");
                }else
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "No hay cupones disponibles para mostrar", Alert.AlertType.WARNING);
                break;
            case "Lista de Promociones":
                btnLista.setText("Lista de Cupones");
                break;
            default:
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Retornos.SELECCION, Alert.AlertType.ERROR);
        }
        
        cargarPromociones(cupones);
    }
}