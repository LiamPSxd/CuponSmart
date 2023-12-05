package cuponsmart.controlador;

import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.dao.EmpresaDAO;
import cuponsmart.modelo.dao.UsuarioDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Constantes;
import utils.Utilidades;
import utils.Verificaciones;

public class FXMLGestionUsuarioController implements Initializable, IRespuesta{
    private ObservableList<Usuario> usuarios;
    
    @FXML
    private TextField txtBusqueda;
    @FXML
    private ComboBox<String> comboFiltro;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<Usuario> tbUsuarios;
    @FXML
    private TableColumn clmNombre;
    @FXML
    private TableColumn clmApellidoPaterno;
    @FXML
    private TableColumn clmApellidoMaterno;
    @FXML
    private TableColumn clmCorreo;
    @FXML
    private TableColumn clmRol;
    @FXML
    private TableColumn clmEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.usuarios = FXCollections.observableArrayList();
        
        colocarImagenBoton("/img/registrar.png", btnRegistrar);
        colocarImagenBoton("/img/modificar.png", btnModificar);
        colocarImagenBoton("/img/eliminar.png", btnEliminar);
        configurarTabla();
        
        comboFiltro.getItems().addAll("Nombre", "Username", "Rol");
    }
    
    private void colocarImagenBoton(String resource, Button boton){
        URL url = getClass().getResource(resource);
        Image imagen = new Image(url.toString(), 32, 32, false, true);
        
        boton.setGraphic(new ImageView(imagen));
    }
    
    private void configurarTabla(){
        clmNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        clmApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        clmApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        clmCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        clmRol.setCellValueFactory(new PropertyValueFactory("rol"));
        clmEmpresa.setCellValueFactory(new PropertyValueFactory("empresa"));
    }
    
    public void inicializarInformacion(List<Usuario> usuarios){
        usuarios.forEach((usuario) -> {
            usuario.setRol(
                CatalogoDAO.obtenerRolPorId(usuario.getIdRol()).getNombre()
            );
            
            usuario.setEmpresa(
                EmpresaDAO.obtenerEmpresaPorId(usuario.getIdEmpresa()).getNombre()
            );
        });
        
        this.usuarios.clear();
        this.usuarios.addAll(usuarios);
        
        tbUsuarios.setItems(this.usuarios);
    }
    
    @Override
    public void notificarGuardado(){
        inicializarInformacion(UsuarioDAO.obtenerUsuarios());
    }

    @FXML
    private void buscarUsuario(ActionEvent event){
        String busqueda = txtBusqueda.getText();
        String filtro = comboFiltro.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.cadena(busqueda) && Verificaciones.Datos.cadena(filtro)){
            List<Usuario> resultado = new ArrayList();
            
            switch(filtro){
                case "Nombre":
                    resultado.add(UsuarioDAO.obtenerUsuarioPorNombre(busqueda));
                    inicializarInformacion(resultado);
                    break;
                case "Username":
                    resultado.add(UsuarioDAO.obtenerUsuarioPorUsername(busqueda));
                    inicializarInformacion(resultado);
                    break;
                case "Rol":
                    CatalogoDAO.obtenerRoles().forEach((rol) -> {
                        if(rol.getNombre().equals(busqueda)){
                            inicializarInformacion(UsuarioDAO.obtenerUsuariosPorIdRol(rol.getId()));
                        }
                    });
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
    
    private void irPantallaFormUsuario(Usuario usuario){
        try{
            FXMLLoader carga = new FXMLLoader(CuponSmart.class.getResource(Constantes.Pantallas.URL_VISTA + "FXMLFormUsuario.fxml"));
            Parent vista = carga.load();
            
            FXMLFormUsuarioController controlador = carga.getController();
            controlador.inicializarInformacion(usuario, this);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.setTitle(Constantes.Pantallas.FORM_USUARIO);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.IO, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void registrarUsuario(ActionEvent event){
        irPantallaFormUsuario(null);
    }

    @FXML
    private void modificarUsuario(ActionEvent event){
        Usuario usuario = tbUsuarios.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(usuario)){
            irPantallaFormUsuario(usuario);
        }else{
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar un usuario para su modificación", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void eliminarUsuario(ActionEvent event){
        Usuario usuario = tbUsuarios.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(usuario)){
            if(Utilidades.mostrarAlertaConfirmacion(Constantes.Pantallas.CONFIRMAR_ELIMINACION, "¿Está seguro de eliminar al usuario " + usuario.getUsername() + "?")){
                Mensaje mensaje = UsuarioDAO.eliminarUsuario(usuario.getId());
                
                if(!mensaje.getError()){
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, mensaje.getMensaje(), Alert.AlertType.INFORMATION);
                    notificarGuardado();
                }else{
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
                }
            }
        }else{
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar un usuario para su eliminación", Alert.AlertType.WARNING);
        }
    }
}