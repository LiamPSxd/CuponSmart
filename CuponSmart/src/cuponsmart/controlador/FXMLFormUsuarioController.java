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
            
            if(observable.getValue().getNombre().equals("Administrador Comercial")){
                comboEmpresa.setDisable(false);
                descargarEmpresas();
            }else
                comboEmpresa.setDisable(true);
        });
        
        comboEmpresa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.idEmpresa = observable.getValue().getId();
        });
    }
    
    private void descargarRol(){
        List<Rol> roles = CatalogoDAO.obtenerRoles();
        
        if(Verificaciones.listaNoVacia(roles)){
            comboRol.getItems().clear();
            comboRol.getItems().addAll(roles);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_ROL, Alert.AlertType.ERROR);
    }
    
    private void descargarEmpresas(){
        List<Empresa> empresas = EmpresaDAO.obtenerEmpresas();
        
        if(Verificaciones.listaNoVacia(empresas)){
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
        
        if(Verificaciones.numerico(this.usuario.getIdEmpresa()))
            comboEmpresa.getSelectionModel().select(EmpresaDAO.obtenerEmpresaPorId(this.usuario.getIdEmpresa()));
    }
    
    public void inicializarInformacion(Usuario usuario, IRespuesta observador){
        this.usuario = usuario;
        this.observador = observador;
        
        comboEmpresa.setDisable(true);
        
        if(Verificaciones.claseNoNula(this.usuario) && Verificaciones.numerico(this.usuario.getId())){
            txtTitulo.setText("Modificar Usuario");
            btnFinalizar.setText("Modificar");
            
            rellenarForm();
            
            comboEmpresa.setDisable(false);
            comboRol.setDisable(true);
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
    
    private void registrarUsuario(Usuario usuario){
        if(Verificaciones.claseNula(UsuarioDAO.obtenerUsuarioPorUsername(usuario.getUsername()))){
            Mensaje mensaje = UsuarioDAO.registrarUsuario(usuario);
        
            if(Verificaciones.claseNoNula(mensaje) && !mensaje.getError()){
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Usuario registrado exitosamente", Alert.AlertType.INFORMATION);
                observador.notificarGuardado();
                cerrarVentana();
            }else
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, "El username ya se encuentra registrado", Alert.AlertType.ERROR);
    }
    
    private void modificarUsuario(){
        Mensaje mensaje = UsuarioDAO.modificarUsuario(this.usuario);
        
        if(Verificaciones.claseNoNula(mensaje) && !mensaje.getError()){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Usuario modificado exitosamente", Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarVentana();
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
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
        
        if(Verificaciones.cadena(nombre) && Verificaciones.cadena(apellidoPaterno) && Verificaciones.cadena(apellidoMaterno) && Verificaciones.cadena(curp) &&
            Verificaciones.cadena(username) && Verificaciones.cadena(correo) && Verificaciones.cadena(contrasenia) && Verificaciones.cadena(confirmarContrasenia) &&
            Verificaciones.claseNoNula(rol)){
            if(rol.getNombre().equals("Administrador Comercial") && Verificaciones.claseNula(empresa))
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "Debe seleccionar una empresa", Alert.AlertType.WARNING);
            else{
                if(curp.length() == 18 && contrasenia.equals(confirmarContrasenia)){
                    switch(btnFinalizar.getText()){
                        case "Registrar":
                            registrarUsuario(new Usuario(0, nombre, apellidoPaterno, apellidoMaterno, curp, correo, username, contrasenia, this.idRol, Verificaciones.numerico(this.idEmpresa) ? this.idEmpresa : null));
                            break;
                        case "Modificar":
                            this.usuario.setNombre(nombre);
                            this.usuario.setApellidoPaterno(apellidoPaterno);
                            this.usuario.setApellidoMaterno(apellidoMaterno);
                            this.usuario.setCurp(curp);
                            this.usuario.setUsername(username);
                            this.usuario.setCorreo(correo);
                            this.usuario.setContrasenia(contrasenia);
                            this.usuario.setIdEmpresa(this.idEmpresa);

                            modificarUsuario();
                            break;
                        default:
                            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Retornos.SELECCION, Alert.AlertType.ERROR);
                    }
                }else if(curp.length() != 18)
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "El CURP debe tener 18 caracteres, favor de verificarlo", Alert.AlertType.WARNING);
                else if(!contrasenia.equals(confirmarContrasenia))
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "Las contraseñas no coinciden, favor de verificarlas", Alert.AlertType.WARNING);
            }
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.CAMPOS_VACIOS, Constantes.Errores.CAMPOS_VACIOS, Alert.AlertType.WARNING);
    }
}