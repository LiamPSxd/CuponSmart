package cuponsmart.controlador;

import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.dao.CiudadDAO;
import cuponsmart.modelo.dao.DireccionDAO;
import cuponsmart.modelo.dao.MediaDAO;
import cuponsmart.modelo.pojo.entidad.Ciudad;
import cuponsmart.modelo.pojo.entidad.Direccion;
import cuponsmart.modelo.pojo.entidad.Empresa;
import cuponsmart.modelo.pojo.entidad.Estado;
import cuponsmart.modelo.pojo.entidad.Municipio;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.interfaz.IRespuesta;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import utils.Constantes;
import utils.Utilidades;
import utils.Verificaciones;

public class FXMLFormEmpresaController implements Initializable{
    private Empresa empresa;
    private IRespuesta observador;
    private Integer idEstatus;
    private Integer idEstado;
    private Integer idMunicipio;
    private Integer idCiudad;
    private File imagenSeleccionada;
    
    @FXML
    private Label txtTitulo;
    @FXML
    private Circle imagenLogo;
    @FXML
    private Button btnLogo;
    @FXML
    private TextField txtNombreComercial;
    @FXML
    private ComboBox<String> comboEstatus;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtRFC;
    @FXML
    private TextField txtNombreRepresentanteLegal;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtPaginaWeb;
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
    private ComboBox<String> comboEstado;
    @FXML
    private ComboBox<String> comboMunicipio;
    @FXML
    private ComboBox<String> comboCiudad;
    @FXML
    private Button btnFinalizar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        colocarImagenCircle("/img/logo.png", imagenLogo);
        colocarImagenBoton("/img/foto.png", btnLogo);
        
        CatalogoDAO.obtenerEstatus().forEach((estatus) -> {
            comboEstatus.getItems().add(estatus.getEstado());
        });
        
        comboEstatus.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
            CatalogoDAO.obtenerEstatus().forEach((estatus) -> {
                if(newValue.getSelectedItem().equals(estatus.getEstado())) this.idEstatus = estatus.getId();
            });
        });
        
        List<Estado> estados = CatalogoDAO.obtenerEstados();
        estados.forEach((estado) -> {
            comboEstado.getItems().add(estado.getNombre());
        });
        
        comboEstado.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
            estados.forEach((e) -> {
                if(newValue.getSelectedItem().equals(e.getNombre())) this.idEstado = e.getId();
            });
        });
        
        List<Municipio> municipios = CatalogoDAO.obtenerMunicipiosPorEstado(this.idEstado);
        municipios.forEach((municipio) -> {
            comboMunicipio.getItems().add(municipio.getNombre());
        });
        
        comboMunicipio.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
            municipios.forEach((m) -> {
                if(newValue.getSelectedItem().equals(m.getNombre())) this.idMunicipio = m.getId();
            });
        });
        
        List<Ciudad> ciudades = CiudadDAO.obtenerCiudadesPorIdMunicipio(this.idMunicipio);
        ciudades.forEach((ciudad) -> {
            comboCiudad.getItems().add(ciudad.getNombre());
        });
        
        comboCiudad.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
            ciudades.forEach((c) -> {
                if(newValue.getSelectedItem().equals(c.getNombre())) this.idCiudad = c.getId();
            });
        });
    }
    
    private void colocarImagenCircle(String resource, Circle circle){
        URL url = getClass().getResource(resource);
        circle.setFill(new ImagePattern(new Image(url.toString())));
    }
    
    private void colocarImagenBoton(String resource, Button boton){
        URL url = getClass().getResource(resource);
        Image imagen = new Image(url.toString(), 24, 24, false, true);
        
        boton.setGraphic(new ImageView(imagen));
    }
    
    private void mostrarLogo(String logoBase64){
        imagenLogo.setFill(new ImagePattern(
            new Image(new ByteArrayInputStream(
                Base64.getDecoder().decode(logoBase64.replaceAll("\\n", ""))
            ))
        ));
    }
    
    private void obtenerLogo(){
        Empresa mensaje = MediaDAO.obtenerLogoEmpresa(this.empresa.getId()).getContenido().get(0);
        
        if(Verificaciones.Datos.claseNoNula(mensaje) && Verificaciones.Datos.cadena(mensaje.getLogoBase64()))
            mostrarLogo(mensaje.getLogoBase64());
    }
    
    private void rellenarForm(){
        txtNombreComercial.setText(this.empresa.getNombreComercial());
        comboEstatus.getSelectionModel().select(this.empresa.getEstatus());
        txtNombre.setText(this.empresa.getNombre());
        txtRFC.setText(this.empresa.getRfc());
        
        txtNombreRepresentanteLegal.setText(this.empresa.getNombreRepresentanteLegal());
        txtCorreo.setText(this.empresa.getCorreo());
        txtTelefono.setText(this.empresa.getTelefono());
        txtPaginaWeb.setText(this.empresa.getPaginaWeb());
        
        Direccion direccion = DireccionDAO.obtenerDireccionPorId(this.empresa.getIdDireccion());
        
        if(Verificaciones.Datos.claseNoNula(direccion)){
            txtCalle.setText(direccion.getCalle());
            txtColonia.setText(direccion.getColonia());
            txtNumero.setText(direccion.getNumero());
            txtCodigoPostal.setText(direccion.getCodigoPostal());
            txtLatitud.setText(direccion.getLatitud());
            txtLongitud.setText(direccion.getLongitud());
            
            Ciudad ciudad = CiudadDAO.obtenerCiudadPorId(direccion.getIdCiudad());
            comboCiudad.getSelectionModel().select(ciudad.getNombre());
            
            Municipio municipio = CatalogoDAO.obtenerMunicipioPorId(ciudad.getIdMunicipio());
            comboMunicipio.getSelectionModel().select(municipio.getNombre());
            
            comboEstado.getSelectionModel().select(
                CatalogoDAO.obtenerEstadoPorId(municipio.getIdEstado()).getNombre()
            );
        }
        
        obtenerLogo();
    }
    
    public void inicializarInformacion(Empresa empresa, IRespuesta observador){
        this.empresa = empresa;
        this.observador = observador;
        
        comboEstatus.setDisable(true);
        btnLogo.setDisable(true);
        
        if(Verificaciones.Datos.claseNoNula(this.empresa)){
            txtTitulo.setText("Modificar Empresa");
            btnFinalizar.setText("Modificar");
            
            rellenarForm();
            
            txtRFC.setDisable(true);
            comboEstatus.setDisable(false);
            btnLogo.setDisable(false);
        }
    }
    
    private void mostrarLogo(File logo){
        try{
            imagenLogo.setFill(new ImagePattern(
                SwingFXUtils.toFXImage(ImageIO.read(logo), null)
            ));
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.IO, Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void seleccionarLogo(ActionEvent event){
        FileChooser seleccionarLogo = new FileChooser();
        seleccionarLogo.setTitle(Constantes.Pantallas.SELECCION_IMAGEN);
        seleccionarLogo.getExtensionFilters().add(
            new FileChooser.ExtensionFilter(Constantes.Pantallas.ARCHIVOS_IMAGEN, "*.PNG", "*.JPG", "*.JPEG")
        );
        
        imagenSeleccionada = seleccionarLogo.showOpenDialog(txtNombre.getScene().getWindow());
        
        if(imagenSeleccionada != null) mostrarLogo(imagenSeleccionada);
    }
    
    private void cerrarVentana(){
        Stage escenario = (Stage) txtNombre.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void cancelar(ActionEvent event){
        cerrarVentana();
    }
    
    private Boolean cargarLogo(File logo){
        try{
            byte[] logoBytes = Files.readAllBytes(logo.toPath());
            Mensaje mensaje = MediaDAO.registrarLogoEmpresa(this.empresa.getId(), logoBytes);
            
            return !mensaje.getError();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, "No se pudo cargar el logo", Alert.AlertType.ERROR);
        }
        
        return false;
    }

    @FXML
    private void finalizar(ActionEvent event){
        String nombreComercial = txtNombreComercial.getText();
        String estatus = comboEstatus.getSelectionModel().getSelectedItem();
        String nombre = txtNombre.getText();
        String rfc = txtRFC.getText();
        String nombreRepresentanteLegal = txtNombreRepresentanteLegal.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();
        String paginaWeb = txtPaginaWeb.getText();
        
        String calle = txtCalle.getText();
        String colonia = txtColonia.getText();
        String numero = txtNumero.getText();
        String codigoPostal = txtCodigoPostal.getText();
        String latitud = txtLatitud.getText();
        String longitud = txtLongitud.getText();
        String estado = comboEstado.getSelectionModel().getSelectedItem();
        String municipio = comboMunicipio.getSelectionModel().getSelectedItem();
        String ciudad = comboCiudad.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.cadena(nombreComercial) && Verificaciones.Datos.cadena(estatus) && Verificaciones.Datos.cadena(nombre) && Verificaciones.Datos.cadena(rfc) &&
            Verificaciones.Datos.cadena(nombreRepresentanteLegal) && Verificaciones.Datos.cadena(correo) && Verificaciones.Datos.cadena(telefono) && Verificaciones.Datos.cadena(paginaWeb) &&
            Verificaciones.Datos.cadena(calle) && Verificaciones.Datos.cadena(colonia) && Verificaciones.Datos.cadena(numero) && Verificaciones.Datos.cadena(codigoPostal) && Verificaciones.Datos.cadena(latitud) &&
            Verificaciones.Datos.cadena(longitud) && Verificaciones.Datos.cadena(estado) && Verificaciones.Datos.cadena(municipio) && Verificaciones.Datos.cadena(ciudad)){
            Direccion direccion = new Direccion(0, calle, numero, codigoPostal, colonia, latitud, longitud, this.idCiudad);
            Empresa empresa = new Empresa(0, nombre, nombreComercial, null, nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, this.idEstatus, 0);
                
            switch(btnFinalizar.getText()){
                case "Registrar":
                    Mensaje mensaje = DireccionDAO.registrarDireccion(direccion);
            
                    if(Verificaciones.Datos.claseNoNula(mensaje) && !mensaje.getError()){
                    }
                    break;
                case "Modificar":
                    if(imagenSeleccionada != null){
                        if(cargarLogo(imagenSeleccionada)){
                        }else
                            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.REGISTRO, Alert.AlertType.ERROR);
                    }else
                        Utilidades.mostrarAlertaSimple("Selecciona un logo", "Para subir un logo de la empresa, debes seleccionarla previamente", Alert.AlertType.WARNING);
                    break;
                default:
            }
        }
    }
}