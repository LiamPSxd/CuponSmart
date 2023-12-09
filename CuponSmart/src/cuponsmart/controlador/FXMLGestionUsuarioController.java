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

public class FXMLGestionUsuarioController implements Initializable, IRespuesta{
    private ObservableList<Usuario> usuarios;
    
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
        
        Utilidades.colocarImagenBoton(getClass().getResource("/img/registrar.png"), btnRegistrar);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/modificar.png"), btnModificar);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/eliminar.png"), btnEliminar);
        configurarTabla();
    }
    
    private void configurarTabla(){
        clmNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        clmApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        clmApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        clmCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        clmRol.setCellValueFactory(new PropertyValueFactory("rol"));
        clmEmpresa.setCellValueFactory(new PropertyValueFactory("empresa"));
    }
    
    private void cargarUsuarios(List<Usuario> usuarios){
        if(Verificaciones.Datos.listaNoVacia(usuarios)){
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
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "No se pudieron cargar los usuarios, por favor inténtelo más tarde", Alert.AlertType.WARNING);
    }
    
    private void inicializarBusqueda(){
        if(this.usuarios != null){
            FilteredList<Usuario> filtro = new FilteredList(this.usuarios, p -> true);
            
            txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
                filtro.setPredicate((busqueda) -> {
                    if(newValue == null || newValue.isEmpty()) return true;
                    
                    String lower = newValue.toLowerCase();
                    
                    if(busqueda.getNombre().toLowerCase().contains(lower)) return true;
                    
                    if(busqueda.getUsername().toLowerCase().contains(lower)) return true;
                    
                    if(busqueda.getRol().toLowerCase().contains(lower)) return true;
                    
                    return false;
                });
                
                SortedList<Usuario> usuariosFiltrados = new SortedList(filtro);
                usuariosFiltrados.comparatorProperty().bind(tbUsuarios.comparatorProperty());
                tbUsuarios.setItems(usuariosFiltrados);
            });
        }
    }
    
    public void inicializarInformacion(List<Usuario> usuarios){
        cargarUsuarios(usuarios);
        inicializarBusqueda();
    }
    
    @Override
    public void notificarGuardado(){
        cargarUsuarios(UsuarioDAO.obtenerUsuarios());
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
        
        if(Verificaciones.Datos.claseNoNula(usuario))
            irPantallaFormUsuario(usuario);
        else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar un usuario para su modificación", Alert.AlertType.WARNING);
    }

    @FXML
    private void eliminarUsuario(ActionEvent event){
        Usuario usuario = tbUsuarios.getSelectionModel().getSelectedItem();
        
        if(Verificaciones.Datos.claseNoNula(usuario)){
            if(Utilidades.mostrarAlertaConfirmacion(Constantes.Pantallas.CONFIRMAR_ELIMINACION, "¿Está seguro de eliminar al usuario " + usuario.getUsername() + "?")){
                Mensaje mensaje = UsuarioDAO.eliminarUsuario(usuario.getId());
                
                if(!mensaje.getError()){
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Usuario eliminado exitosamente", Alert.AlertType.INFORMATION);
                    notificarGuardado();
                }else
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.SIN_SELECCION, "Debe seleccionar un usuario para su eliminación", Alert.AlertType.WARNING);
    }
}