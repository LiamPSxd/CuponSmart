package cuponsmart.controlador;

import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.dao.EmpresaDAO;
import cuponsmart.modelo.dao.UsuarioDAO;
import cuponsmart.modelo.pojo.entidad.Empresa;
import cuponsmart.modelo.pojo.entidad.Rol;
import cuponsmart.modelo.pojo.entidad.Usuario;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Constantes;
import utils.Utilidades;
import utils.Verificaciones;

public class FXMLFormUsuarioController implements Initializable{
    private Usuario usuario;
    private IRespuesta observador;
    private Integer idRol;
    private Integer idEmpresa;
    
    @FXML
    private Label txtTitulo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidoPaterno;
    @FXML
    private TextField txtApellidoMaterno;
    @FXML
    private TextField txtCURP;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtContrasenia;
    @FXML
    private PasswordField txtConfirmarContrasenia;
    @FXML
    private ComboBox<Rol> comboRol;
    @FXML
    private ComboBox<Empresa> comboEmpresa;
    @FXML
    private Button btnFinalizar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        descargarRol();
        
        comboRol.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.idRol = observable.getValue().getId();
            
            if(observable.getValue().getNombre().equals("Administrador Comercial")) descargarEmpresas();
        });
    }
    
    private void descargarRol(){
        List<Rol> roles = CatalogoDAO.obtenerRoles();
        
        if(Verificaciones.Datos.listaNoVacia(roles)){
            comboRol.getItems().clear();
            comboRol.getItems().addAll(roles);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_ROL, Alert.AlertType.ERROR);
    }
    
    private void descargarEmpresas(){
        List<Empresa> empresas = EmpresaDAO.obtenerEmpresas();
        
        if(Verificaciones.Datos.listaNoVacia(empresas)){
            comboEmpresa.getItems().clear();
            comboEmpresa.getItems().addAll(empresas);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_EMPRESA, Alert.AlertType.ERROR);
    }
    
    private void rellenarForm(){
        txtNombre.setText(this.usuario.getNombre());
        txtApellidoPaterno.setText(this.usuario.getApellidoPaterno());
        txtApellidoMaterno.setText(this.usuario.getApellidoMaterno());
        txtCURP.setText(this.usuario.getCurp());
        txtUsername.setText(this.usuario.getUsername());
        txtCorreo.setText(this.usuario.getCorreo());
        txtContrasenia.setText(this.usuario.getContrasenia());
        comboRol.getSelectionModel().select(CatalogoDAO.obtenerRolPorId(this.usuario.getIdRol()));
        
        if(Verificaciones.Datos.numerico(this.usuario.getIdEmpresa()))
            comboEmpresa.getSelectionModel().select(EmpresaDAO.obtenerEmpresaPorId(this.usuario.getIdEmpresa()));
    }
    
    public void inicializarInformacion(Usuario usuario, IRespuesta observador){
        this.usuario = usuario;
        this.observador = observador;
        
        comboEmpresa.setDisable(true);
        
        if(Verificaciones.Datos.claseNoNula(this.usuario) && Verificaciones.Datos.numerico(this.usuario.getId())){
            txtTitulo.setText("Modificar Usuario");
            btnFinalizar.setText("Finalizar");
            
            rellenarForm();
            
            comboEmpresa.setDisable(false);
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
    
    private void verificarMensaje(Mensaje mensaje){
        if(Verificaciones.Datos.claseNoNula(mensaje) && !mensaje.getError()){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, mensaje.getMensaje(), Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarVentana();
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
    }
    
    private void registrarUsuario(Usuario usuario){
        verificarMensaje(UsuarioDAO.registrarUsuario(usuario));
    }
    
    private void modificarUsuario(){
        verificarMensaje(UsuarioDAO.modificarUsuario(this.usuario));
    }

    @FXML
    private void finalizar(ActionEvent event){
        String nombre = txtNombre.getText();
        String apellidoPaterno = txtApellidoPaterno.getText();
        String apellidoMaterno = txtApellidoMaterno.getText();
        String curp = txtCURP.getText();
        String username = txtUsername.getText();
        String correo = txtCorreo.getText();
        String contrasenia = txtContrasenia.getText();
        String confirmarContrasenia = txtConfirmarContrasenia.getText();
        Rol rol = comboRol.getSelectionModel().getSelectedItem();
        Empresa empresa = comboEmpresa.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.cadena(nombre) && Verificaciones.Datos.cadena(apellidoPaterno) && Verificaciones.Datos.cadena(apellidoMaterno) && Verificaciones.Datos.cadena(curp) &&
            Verificaciones.Datos.cadena(username) && Verificaciones.Datos.cadena(correo) && Verificaciones.Datos.cadena(contrasenia) && Verificaciones.Datos.cadena(confirmarContrasenia) &&
            Verificaciones.Datos.claseNoNula(rol) && Verificaciones.Datos.claseNoNula(empresa)){
            if(contrasenia.equals(confirmarContrasenia))
                switch(btnFinalizar.getText()){
                    case "Registrar":
                        registrarUsuario(new Usuario(0, nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, this.idRol, Verificaciones.Datos.numerico(this.idEmpresa) ? this.idEmpresa : 0));
                        break;
                    case "Modificar":
                        this.usuario.setNombre(nombre);
                        this.usuario.setApellidoPaterno(apellidoPaterno);
                        this.usuario.setApellidoMaterno(apellidoMaterno);
                        this.usuario.setCurp(curp);
                        this.usuario.setUsername(username);
                        this.usuario.setCorreo(correo);
                        this.usuario.setContrasenia(contrasenia);
                        this.usuario.setIdRol(this.idRol);
                        this.usuario.setIdEmpresa(Verificaciones.Datos.numerico(this.idEmpresa) ? this.idEmpresa : 0);

                        modificarUsuario();
                        break;
                    default:
                        Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Retornos.SELECCION, Alert.AlertType.ERROR);
                }
            else
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "Las contraseñas no coinciden, favor de verificarlas", Alert.AlertType.WARNING);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.CAMPOS_VACIOS, Constantes.Errores.CAMPOS_VACIOS, Alert.AlertType.WARNING);
    }
}