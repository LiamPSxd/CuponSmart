package cuponsmart.controlador;

import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.dao.CiudadDAO;
import cuponsmart.modelo.dao.DireccionDAO;
import cuponsmart.modelo.dao.EmpresaDAO;
import cuponsmart.modelo.dao.SucursalDAO;
import cuponsmart.modelo.pojo.entidad.Ciudad;
import cuponsmart.modelo.pojo.entidad.Direccion;
import cuponsmart.modelo.pojo.entidad.Empresa;
import cuponsmart.modelo.pojo.entidad.Estado;
import cuponsmart.modelo.pojo.entidad.Municipio;
import cuponsmart.modelo.pojo.entidad.Sucursal;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.interfaz.IRespuesta;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Constantes;
import utils.Utilidades;
import utils.Verificaciones;

public class FXMLFormSucursalController implements Initializable{
    private Sucursal sucursal;
    private Direccion direccion;
    private Integer idEmpresa;
    private IRespuesta observador;
    private Integer idEstado;
    private Integer idMunicipio;
    private Integer idCiudad;
    
    @FXML
    private Label txtTitulo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtNombreEncargado;
    @FXML
    private TextField txtTelefono;
    @FXML
    private ComboBox<Empresa> comboEmpresa;
    @FXML
    private TextField txtCalle;
    @FXML
    private TextField txtColonia;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtCodigoPostal;
    @FXML
    private TextField txtLatitud;
    @FXML
    private TextField txtLongitud;
    @FXML
    private ComboBox<Estado> comboEstado;
    @FXML
    private ComboBox<Municipio> comboMunicipio;
    @FXML
    private ComboBox<Ciudad> comboCiudad;
    @FXML
    private Button btnFinalizar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        descargarEmpresas();
        descargarEstados();
        
        comboEmpresa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.idEmpresa = observable.getValue().getId();
        });
        
        comboEstado.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.idEstado = observable.getValue().getId();
            
            descargarMunicipios(this.idEstado);
        });
        
        comboMunicipio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.idMunicipio = observable.getValue().getId();
            
            descargarCiudades(this.idMunicipio);
        });
        
        comboCiudad.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.idCiudad = observable.getValue().getId();
        });
    }
    
    private void descargarEmpresas(){
        List<Empresa> empresas = EmpresaDAO.obtenerEmpresas();
        
        if(Verificaciones.Datos.numerico(this.idEmpresa)){
            empresas.clear();
            empresas.add(EmpresaDAO.obtenerEmpresaPorId(this.idEmpresa));
        }
        
        if(Verificaciones.Datos.listaNoVacia(empresas)){
            comboEmpresa.getItems().clear();
            comboEmpresa.getItems().addAll(empresas);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_EMPRESA, Alert.AlertType.ERROR);
    }
    
    private void descargarEstados(){
        List<Estado> estados = CatalogoDAO.obtenerEstados();
        
        if(Verificaciones.Datos.listaNoVacia(estados)){
            comboEstado.getItems().clear();
            comboEstado.getItems().addAll(estados);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_ESTADO, Alert.AlertType.ERROR);
    }
    
    private void descargarMunicipios(Integer idEstado){
        List<Municipio> municipios = CatalogoDAO.obtenerMunicipiosPorEstado(idEstado);
        
        if(Verificaciones.Datos.listaNoVacia(municipios)){
            comboMunicipio.getItems().clear();
            comboMunicipio.getItems().addAll(municipios);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_MUNICIPIO, Alert.AlertType.ERROR);
    }
    
    private void descargarCiudades(Integer idMunicipio){
        List<Ciudad> ciudades = CiudadDAO.obtenerCiudadesPorIdMunicipio(idMunicipio);
        
        if(Verificaciones.Datos.listaNoVacia(ciudades)){
            comboCiudad.getItems().clear();
            comboCiudad.getItems().addAll(ciudades);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_CIUDAD, Alert.AlertType.ERROR);
    }
    
    private void rellenarForm(){
        txtNombre.setText(this.sucursal.getNombre());
        txtNombreEncargado.setText(this.sucursal.getNombreEncargado());
        txtTelefono.setText(this.sucursal.getTelefono());
        
        comboEmpresa.getSelectionModel().select(EmpresaDAO.obtenerEmpresaPorId(this.idEmpresa));
        
        txtCalle.setText(this.direccion.getCalle());
        txtColonia.setText(this.direccion.getColonia());
        txtNumero.setText(this.direccion.getNumero());
        txtCodigoPostal.setText(this.direccion.getCodigoPostal());
        txtLatitud.setText(this.direccion.getLatitud());
        txtLongitud.setText(this.direccion.getLongitud());
        
        Ciudad ciudad = CiudadDAO.obtenerCiudadPorId(this.direccion.getIdCiudad());
        comboCiudad.getSelectionModel().select(ciudad);
        
        Municipio municipio = CatalogoDAO.obtenerMunicipioPorId(ciudad.getIdMunicipio());
        comboMunicipio.getSelectionModel().select(municipio);
        
        comboEstado.getSelectionModel().select(CatalogoDAO.obtenerEstadoPorId(municipio.getIdEstado()));
    }
    
    public void inicializarInformacion(Sucursal sucursal, Integer idEmpresa, IRespuesta observador){
        this.sucursal = sucursal;
        this.direccion = Verificaciones.Datos.claseNoNula(this.sucursal) ? DireccionDAO.obtenerDireccionPorId(this.sucursal.getIdDireccion()) : null;
        this.idEmpresa = idEmpresa;
        this.observador = observador;
        
        if(Verificaciones.Datos.claseNoNula(this.sucursal) && Verificaciones.Datos.numerico(this.sucursal.getId()) &&
            Verificaciones.Datos.claseNoNula(this.direccion) && Verificaciones.Datos.numerico(this.direccion.getId())){
            txtTitulo.setText("Modificar Sucursal");
            btnFinalizar.setText("Modificar");
            
            rellenarForm();
        }
    }
    
    private void cerrarVentana(){
        Stage escenario = (Stage) txtNombre.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void cancelar(ActionEvent event){
        cerrarVentana();
    }
    
    private void registrarSucursal(Sucursal sucursal, Direccion direccion){
        Mensaje mensaje = DireccionDAO.registrarDireccion(direccion);
        
        if(Verificaciones.Datos.claseNoNula(mensaje) && !mensaje.getError()){
            Direccion dir = DireccionDAO.obtenerDirecciones().stream().filter((d) -> (
                direccion.getCalle().equals(d.getCalle()) &&
                direccion.getColonia().equals(d.getColonia()) &&
                direccion.getNumero().equals(d.getNumero()) &&
                direccion.getCodigoPostal().equals(d.getCodigoPostal()) &&
                direccion.getLatitud().equals(d.getLatitud()) &&
                direccion.getLongitud().equals(d.getLongitud()) &&
                direccion.getIdCiudad().equals(d.getIdCiudad())
            )).findFirst().get();
            
            sucursal.setIdDireccion(dir.getId());
            
            mensaje = SucursalDAO.registrarSucursal(sucursal);
            
            if(Verificaciones.Datos.claseNoNula(mensaje) && !mensaje.getError()){
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, mensaje.getMensaje(), Alert.AlertType.INFORMATION);
                observador.notificarGuardado();
                cerrarVentana();
            }else
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
    }
    
    private void moficarSucursal(){
        Mensaje mensaje = DireccionDAO.modificarDireccion(this.direccion);
        
        if(Verificaciones.Datos.claseNoNula(mensaje) && !mensaje.getError()){
            mensaje = SucursalDAO.modificarSucursal(this.sucursal);
            
            if(Verificaciones.Datos.claseNoNula(mensaje) && !mensaje.getError()){
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, mensaje.getMensaje(), Alert.AlertType.INFORMATION);
                observador.notificarGuardado();
                cerrarVentana();
            }else
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
    }

    @FXML
    private void finalizar(ActionEvent event){
        String nombre = txtNombre.getText();
        String nombreEncargado = txtNombreEncargado.getText();
        String telefono = txtTelefono.getText();
        
        String calle = txtCalle.getText();
        String colonia = txtColonia.getText();
        String numero = txtNumero.getText();
        String codigoPostal = txtCodigoPostal.getText();
        String latitud = txtLatitud.getText();
        String longitud = txtLongitud.getText();
        Estado estado = comboEstado.getSelectionModel().getSelectedItem();
        Municipio municipio = comboMunicipio.getSelectionModel().getSelectedItem();
        Ciudad ciudad = comboCiudad.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.cadena(nombre) && Verificaciones.Datos.cadena(nombreEncargado) && Verificaciones.Datos.cadena(telefono) && Verificaciones.Datos.cadena(calle) &&
            Verificaciones.Datos.cadena(colonia) && Verificaciones.Datos.cadena(numero) && Verificaciones.Datos.cadena(codigoPostal) && Verificaciones.Datos.cadena(latitud) &&
            Verificaciones.Datos.cadena(longitud) && Verificaciones.Datos.claseNoNula(estado) && Verificaciones.Datos.claseNoNula(municipio) && Verificaciones.Datos.claseNoNula(ciudad)){
            switch(btnFinalizar.getText()){
                case "Registrar":
                    registrarSucursal(
                        new Sucursal(0, nombre, telefono, nombreEncargado, 0, this.idEmpresa),
                        new Direccion(0, calle, numero, codigoPostal, colonia, latitud, longitud, this.idCiudad)
                    );
                    break;
                case "Modificar":
                    this.sucursal.setNombre(nombre);
                    this.sucursal.setNombreEncargado(nombreEncargado);
                    this.sucursal.setTelefono(telefono);
                    
                    this.direccion.setCalle(calle);
                    this.direccion.setColonia(colonia);
                    this.direccion.setNumero(numero);
                    this.direccion.setCodigoPostal(codigoPostal);
                    this.direccion.setLatitud(latitud);
                    this.direccion.setLongitud(longitud);
                    this.direccion.setIdCiudad(this.idCiudad);
                    
                    moficarSucursal();
                    break;
                default:
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Retornos.SELECCION, Alert.AlertType.ERROR);
            }
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.CAMPOS_VACIOS, Constantes.Errores.CAMPOS_VACIOS, Alert.AlertType.WARNING);
    }
}