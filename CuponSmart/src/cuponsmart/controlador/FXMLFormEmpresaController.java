package cuponsmart.controlador;

import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.dao.CiudadDAO;
import cuponsmart.modelo.dao.DireccionDAO;
import cuponsmart.modelo.dao.EmpresaDAO;
import cuponsmart.modelo.dao.MediaDAO;
import cuponsmart.modelo.pojo.entidad.Ciudad;
import cuponsmart.modelo.pojo.entidad.Direccion;
import cuponsmart.modelo.pojo.entidad.Empresa;
import cuponsmart.modelo.pojo.entidad.Estado;
import cuponsmart.modelo.pojo.entidad.Estatus;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
    private Direccion direccion;
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
    private Button btnSeleccionarLogo;
    @FXML
    private TextField txtNombreComercial;
    @FXML
    private ComboBox<Estatus> comboEstatus;
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
    private TextField txtNumero;
    @FXML
    private TextField txtCodigoPostal;
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
        Utilidades.colocarImagenCircle(getClass().getResource("/img/noMedia.png"), imagenLogo);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/foto.png"), btnSeleccionarLogo);
        
        descargarEstatus();
        descargarEstados();
        
        comboEstatus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.idEstatus = observable.getValue().getId();
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
    
    private void descargarEstatus(){
        List<Estatus> estatus = CatalogoDAO.obtenerEstatus();
        
        if(Verificaciones.Datos.listaNoVacia(estatus)){
            comboEstatus.getItems().clear();
            comboEstatus.getItems().addAll(estatus);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_ESTATUS, Alert.AlertType.ERROR);
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
    
    private void mostrarLogo(String logoBase64){
        imagenLogo.setFill(new ImagePattern(
            new Image(new ByteArrayInputStream(
                Base64.getDecoder().decode(logoBase64.replaceAll("\\n", ""))
            ))
        ));
    }
    
    private void obtenerLogo(){
        Empresa mensaje = MediaDAO.obtenerLogoEmpresa(this.empresa.getId());
        
        if(Verificaciones.Datos.claseNoNula(mensaje) && Verificaciones.Datos.cadena(mensaje.getLogoBase64()))
            mostrarLogo(mensaje.getLogoBase64());
    }
    
    private void rellenarForm(){
        txtNombreComercial.setText(this.empresa.getNombreComercial());
        comboEstatus.getSelectionModel().select(CatalogoDAO.obtenerEstatusPorId(this.empresa.getIdEstatus()));
        txtNombre.setText(this.empresa.getNombre());
        txtRFC.setText(this.empresa.getRfc());
        
        txtNombreRepresentanteLegal.setText(this.empresa.getNombreRepresentanteLegal());
        txtCorreo.setText(this.empresa.getCorreo());
        txtTelefono.setText(this.empresa.getTelefono());
        txtPaginaWeb.setText(this.empresa.getPaginaWeb());
        
        txtCalle.setText(this.direccion.getCalle());
        txtNumero.setText(this.direccion.getNumero());
        txtCodigoPostal.setText(this.direccion.getCodigoPostal());

        Ciudad ciudad = CiudadDAO.obtenerCiudadPorId(this.direccion.getIdCiudad());
        comboCiudad.getSelectionModel().select(ciudad);

        Municipio municipio = CatalogoDAO.obtenerMunicipioPorId(ciudad.getIdMunicipio());
        comboMunicipio.getSelectionModel().select(municipio);

        comboEstado.getSelectionModel().select(CatalogoDAO.obtenerEstadoPorId(municipio.getIdEstado()));
        
        obtenerLogo();
    }
    
    public void inicializarInformacion(Empresa empresa, IRespuesta observador){
        this.empresa = empresa;
        this.direccion = Verificaciones.Datos.claseNoNula(this.empresa) ? DireccionDAO.obtenerDireccionPorId(this.empresa.getIdDireccion()) : null;
        this.observador = observador;
        
        CatalogoDAO.obtenerEstatus().forEach((estatus) -> {
            if(estatus.getEstado().equals("Activo")) comboEstatus.getSelectionModel().select(estatus);
        });
        
        comboEstatus.setDisable(true);
        
        if(Verificaciones.Datos.claseNoNula(this.empresa) && Verificaciones.Datos.numerico(this.empresa.getId()) &&
            Verificaciones.Datos.claseNoNula(this.direccion) && Verificaciones.Datos.numerico(this.direccion.getId())){
            txtTitulo.setText("Modificar Empresa");
            btnFinalizar.setText("Modificar");
            
            rellenarForm();
            
            txtRFC.setDisable(true);
            comboEstatus.setDisable(false);
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
    
    private Boolean cargarLogo(File logo, Integer idEmpresa){
        try{
            byte[] logoBytes = Files.readAllBytes(logo.toPath());
            Mensaje mensaje = MediaDAO.registrarLogoEmpresa(idEmpresa, logoBytes);
            
            return !mensaje.getError();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, "No se pudo cargar el logo, por favor inténtelo más tarde", Alert.AlertType.ERROR);
        }
        
        return false;
    }
    
    private void registrarEmpresa(Empresa empresa, Direccion direccion){
        if(!Verificaciones.Datos.listaNoVacia(EmpresaDAO.obtenerEmpresasPorRFC(empresa.getRfc()))){
            Mensaje mensaje = DireccionDAO.registrarDireccion(direccion);
            
            if(Verificaciones.Datos.claseNoNula(mensaje) && !mensaje.getError()){
                Integer idDireccion = DireccionDAO.obtenerDirecciones().stream().filter((d) -> (
                    direccion.getCalle().equals(d.getCalle()) &&
                    direccion.getNumero().equals(d.getNumero()) &&
                    direccion.getCodigoPostal().equals(d.getCodigoPostal()) &&
                    direccion.getIdCiudad().equals(d.getIdCiudad())
                )).findFirst().get().getId();

                empresa.setIdDireccion(idDireccion);

                mensaje = EmpresaDAO.registrarEmpresa(empresa);

                if(Verificaciones.Datos.claseNoNula(mensaje) && !mensaje.getError()){
                    Integer idEmpresa = EmpresaDAO.obtenerEmpresas().stream().filter((e) -> (
                        empresa.getRfc().equals(e.getRfc())
                    )).findFirst().get().getId();
                    
                    if(imagenSeleccionada != null && !cargarLogo(imagenSeleccionada, idEmpresa))
                        Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.MEDIA, Alert.AlertType.ERROR);
                    
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Empresa registrada exitosamente", Alert.AlertType.INFORMATION);
                    observador.notificarGuardado();
                    cerrarVentana();
                }else
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
            }else
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, "El RFC ya se encuentra registrado", Alert.AlertType.ERROR);
    }
    
    private void modificarEmpresa(){
        Mensaje mensaje = DireccionDAO.modificarDireccion(this.direccion);
            
        if(Verificaciones.Datos.claseNoNula(mensaje) && !mensaje.getError()){
            mensaje = EmpresaDAO.modificarEmpresa(this.empresa);
            
            if(Verificaciones.Datos.claseNoNula(mensaje) && !mensaje.getError()){
                if(imagenSeleccionada != null && !cargarLogo(imagenSeleccionada, this.empresa.getId()))
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.MEDIA, Alert.AlertType.ERROR);
                
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Empresa modificada exitosamente", Alert.AlertType.INFORMATION);
                observador.notificarGuardado();
                cerrarVentana();
            }else
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
    }

    @FXML
    private void finalizar(ActionEvent event){
        String nombreComercial = txtNombreComercial.getText();
        Estatus estatus = comboEstatus.getSelectionModel().getSelectedItem();
        String nombre = txtNombre.getText();
        String rfc = txtRFC.getText();
        String nombreRepresentanteLegal = txtNombreRepresentanteLegal.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();
        String paginaWeb = txtPaginaWeb.getText();
        
        String calle = txtCalle.getText();
        String numero = txtNumero.getText();
        String codigoPostal = txtCodigoPostal.getText();
        Estado estado = comboEstado.getSelectionModel().getSelectedItem();
        Municipio municipio = comboMunicipio.getSelectionModel().getSelectedItem();
        Ciudad ciudad = comboCiudad.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.cadena(nombreComercial) && Verificaciones.Datos.claseNoNula(estatus) && Verificaciones.Datos.cadena(nombre) && Verificaciones.Datos.cadena(rfc) &&
            Verificaciones.Datos.cadena(nombreRepresentanteLegal) && Verificaciones.Datos.cadena(correo) && Verificaciones.Datos.cadena(telefono) && Verificaciones.Datos.cadena(paginaWeb) &&
            Verificaciones.Datos.cadena(calle) && Verificaciones.Datos.cadena(numero) && Verificaciones.Datos.cadena(codigoPostal) && Verificaciones.Datos.claseNoNula(estado) &&
            Verificaciones.Datos.claseNoNula(municipio) && Verificaciones.Datos.claseNoNula(ciudad)){
            if(rfc.length() == 12 && telefono.length() == 10 && codigoPostal.length() == 5){
                switch(btnFinalizar.getText()){
                    case "Registrar":
                        registrarEmpresa(
                            new Empresa(0, nombre, nombreComercial, "", nombreRepresentanteLegal, correo, telefono, paginaWeb, rfc, this.idEstatus, 0),
                            new Direccion(0, calle, numero, codigoPostal, "", "", "", this.idCiudad)
                        );
                        break;
                    case "Modificar":
                        this.empresa.setNombre(nombre);
                        this.empresa.setNombreComercial(nombreComercial);
                        this.empresa.setNombreRepresentanteLegal(nombreRepresentanteLegal);
                        this.empresa.setCorreo(correo);
                        this.empresa.setTelefono(telefono);
                        this.empresa.setPaginaWeb(paginaWeb);
                        this.empresa.setRfc(rfc);
                        this.empresa.setIdEstatus(this.idEstatus);

                        this.direccion.setCalle(calle);
                        this.direccion.setNumero(numero);
                        this.direccion.setCodigoPostal(codigoPostal);
                        this.direccion.setIdCiudad(this.idCiudad);

                        modificarEmpresa();
                        break;
                    default:
                        Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Retornos.SELECCION, Alert.AlertType.ERROR);
                }
            }else if(rfc.length() != 12)
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "El RFC debe tener 12 caracteres, favor de verificarlo", Alert.AlertType.WARNING);
            else if(telefono.length() != 10)
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "El teléfono debe tener 10 dígitos, favor de verificarlo", Alert.AlertType.WARNING);
            else if(codigoPostal.length() != 5)
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "El código postal debe tener 5 dígitos, favor de verificarlo", Alert.AlertType.WARNING);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.CAMPOS_VACIOS, Constantes.Errores.CAMPOS_VACIOS, Alert.AlertType.WARNING);
    }
}