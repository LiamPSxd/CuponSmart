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

public class FXMLGestionSucursalController implements Initializable, IRespuesta{
    private ObservableList<Sucursal> sucursales;
    private Usuario administradorComercial;
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private ComboBox<String> comboFiltro;
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
        configurarTabla();
        
        comboFiltro.getItems().addAll("Nombre", "Dirección");
    }
    
    private void configurarTabla(){
        clmNombre.setCellFactory(new PropertyValueFactory("nombre"));
        clmTelefono.setCellFactory(new PropertyValueFactory("telefono"));
        clmEncargado.setCellFactory(new PropertyValueFactory("nombreEncargado"));
        clmDireccion.setCellFactory(new PropertyValueFactory("direccion"));
        clmEmpresa.setCellFactory(new PropertyValueFactory("empresa"));
    }
    
    public void inicializarInformacion(List<Sucursal> sucursales, Usuario administradorComercial){
        this.administradorComercial = administradorComercial;
        
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
    }
    
    @Override
    public void notificarGuardado(){
        if(Verificaciones.Datos.claseNula(this.administradorComercial))
            inicializarInformacion(SucursalDAO.obtenerSucursales(), null);
        else
            inicializarInformacion(
                SucursalDAO.obtenerSucursalesPorIdEmpresa(this.administradorComercial.getIdEmpresa()),
                this.administradorComercial
            );
    }

    @FXML
    private void buscarSucursal(ActionEvent event){
        String busqueda = txtBusqueda.getText();
        String filtro = comboFiltro.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.cadena(busqueda) && Verificaciones.Datos.cadena(filtro)){
            switch(filtro){
                case "Nombre":
                    List<Sucursal> scs = SucursalDAO.obtenerSucursalesPorNombre(busqueda);
                    
                    if(Verificaciones.Datos.claseNula(this.administradorComercial))
                        inicializarInformacion(scs, null);
                    else{    
                        scs.removeIf((sucursal) -> (
                            !this.administradorComercial.getIdEmpresa().equals(sucursal.getIdEmpresa())
                        ));
                    
                        inicializarInformacion(scs, this.administradorComercial);
                    }
                    break;
                case "Dirección":
                    List<Sucursal> resultado = new ArrayList();

                    this.sucursales.forEach((sucursal) -> {
                        DireccionDAO.obtenerDireccionesPorCalleColoniaNumero(busqueda).forEach((direccion) -> {
                            if(sucursal.getIdDireccion().equals(direccion.getId())) resultado.add(sucursal);
                        });
                    });
                    
                    inicializarInformacion(
                        resultado,
                        Verificaciones.Datos.claseNula(this.administradorComercial) ? null : this.administradorComercial
                    );
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
    
    private void irPantallaFormSucursal(Sucursal sucursal, Usuario administradorComercial){
        try{
            FXMLLoader carga = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLFormSucursal.fxml"));
            Parent vista = carga.load();
            
            FXMLFormSucursalController controlador = carga.getController();
            controlador.inicializarInformacion(sucursal, administradorComercial);

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
            Verificaciones.Datos.claseNula(this.administradorComercial) ? null : this.administradorComercial
        );
    }

    @FXML
    private void modificarSucursal(ActionEvent event){
        Sucursal sucursal = tbSucursales.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(sucursal)){
            irPantallaFormSucursal(
                sucursal,
                Verificaciones.Datos.claseNula(this.administradorComercial) ? null : this.administradorComercial
            );
        }else{
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una sucursal para su modificación", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void eliminarSucursal(ActionEvent event){
        Sucursal sucursal = tbSucursales.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(sucursal)){
            if(Utilidades.mostrarAlertaConfirmacion(Constantes.Pantallas.CONFIRMAR_ELIMINACION, "¿Está seguro de eliminar la sucursal " + sucursal.getNombre() + "?")){
                Mensaje mensaje = SucursalDAO.eliminarSucursal(sucursal.getId());
                
                if(!mensaje.getError()){
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, mensaje.getMensaje(), Alert.AlertType.INFORMATION);
                    notificarGuardado();
                }else{
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
                }
            }
        }else{
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar una sucursal para su eliminación", Alert.AlertType.WARNING);
        }
    }
}