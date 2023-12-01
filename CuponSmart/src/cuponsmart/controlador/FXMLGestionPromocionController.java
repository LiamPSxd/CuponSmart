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

public class FXMLGestionPromocionController implements Initializable, IRespuesta{
    private ObservableList<Promocion> promociones;
    private Usuario administradorComercial;
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private ComboBox<String> comboFiltro;
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
        configurarTabla();
        
        comboFiltro.getItems().addAll("Fecha de Inicio", "Fecha de Término", "Nombre");
    }
    
    private void configurarTabla(){
        clmNombre.setCellFactory(new PropertyValueFactory("nombre"));
        clmDescripcion.setCellFactory(new PropertyValueFactory("descripcion"));
        clmFechaInicio.setCellFactory(new PropertyValueFactory("fechaInicio"));
        clmFechaTermino.setCellFactory(new PropertyValueFactory("fechaTermino"));
        clmCodigo.setCellFactory(new PropertyValueFactory("codigo"));
        clmNumero.setCellFactory(new PropertyValueFactory("numeroCupones"));
        clmEstatus.setCellFactory(new PropertyValueFactory("estatus"));
        clmCategoria.setCellFactory(new PropertyValueFactory("categoria"));
        clmEmpresa.setCellFactory(new PropertyValueFactory("empresa"));
    }
    
    public void inicializarInformacion(List<Promocion> promociones, Usuario administradorComercial){
        this.administradorComercial = administradorComercial;
        
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
    }
    
    @Override
    public void notificarGuardado(){
        if(Verificaciones.Datos.claseNula(this.administradorComercial))
            inicializarInformacion(PromocionDAO.obtenerPromociones(), null);
        else
            inicializarInformacion(
                PromocionDAO.obtenerPromocionesPorIdEmpresa(this.administradorComercial.getIdEmpresa()),
                this.administradorComercial
            );
    }

    @FXML
    private void buscarPromocion(ActionEvent event){
        String busqueda = txtBusqueda.getText();
        String filtro = comboFiltro.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.cadena(busqueda) && Verificaciones.Datos.cadena(filtro)){
            List<Promocion> pms = new ArrayList();
            
            switch(filtro){
                case "Fecha de Inicio":
                    pms = PromocionDAO.obtenerPromocionesPorFechaInicio(busqueda);
                    break;
                case "Fecha de Término":
                    pms = PromocionDAO.obtenerPromocionesPorFechaTermino(busqueda);
                    break;
                case "Nombre":
                    pms = PromocionDAO.obtenerPromocionesPorNombre(busqueda);
                    break;
                default:
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, "Filtro no encontrado", Alert.AlertType.ERROR);
            }
            
            if(Verificaciones.Datos.claseNula(this.administradorComercial))
                inicializarInformacion(pms, null);
            else{
                pms.removeIf((promocion) -> (
                    !this.administradorComercial.getIdEmpresa().equals(promocion.getIdEmpresa())
                ));

                inicializarInformacion(pms, this.administradorComercial);
            }
        }else if(!Verificaciones.Datos.cadena(busqueda)){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Retornos.BUSQUEDA, Alert.AlertType.WARNING);
        }else if(!Verificaciones.Datos.cadena(filtro)){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Retornos.FILTRO, Alert.AlertType.WARNING);
        }else{
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Retornos.BUSQUEDA_FILTRO, Alert.AlertType.WARNING);
        }
    }
    
    private void irPantallaFormPromocion(Promocion promocion, Usuario administradorComercial){
        try{
            FXMLLoader carga = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLFormPromocion.fxml"));
            Parent vista = carga.load();
            
            FXMLFormPromocionController controlador = carga.getController();
            controlador.inicializarInformacion(promocion, administradorComercial);

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
            Verificaciones.Datos.claseNula(this.administradorComercial) ? null : this.administradorComercial
        );
    }

    @FXML
    private void modificarPromocion(ActionEvent event){
        Promocion promocion = tbPromociones.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(promocion)){
            irPantallaFormPromocion(
                promocion,
                Verificaciones.Datos.claseNula(this.administradorComercial) ? null : this.administradorComercial
            );
        }else{
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una promoción para su modificación", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void eliminarPromocion(ActionEvent event){
        Promocion promocion = tbPromociones.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(promocion)){
            if(Utilidades.mostrarAlertaConfirmacion(Constantes.Pantallas.CONFIRMAR_ELIMINACION, "¿Está seguro de eliminar la promoción " + promocion.getNombre() + "?")){
                Mensaje mensaje = PromocionDAO.eliminarPromocion(promocion.getId());
                
                if(!mensaje.getError()){
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, mensaje.getMensaje(), Alert.AlertType.INFORMATION);
                    notificarGuardado();
                }else{
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
                }
            }
        }else{
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una promoción para su eliminación", Alert.AlertType.WARNING);
        }
    }
}