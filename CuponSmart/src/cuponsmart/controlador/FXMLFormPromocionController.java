package cuponsmart.controlador;

import cuponsmart.modelo.dao.CatalogoDAO;
import cuponsmart.modelo.dao.CategoriaDAO;
import cuponsmart.modelo.dao.DireccionDAO;
import cuponsmart.modelo.dao.EmpresaDAO;
import cuponsmart.modelo.dao.MediaDAO;
import cuponsmart.modelo.dao.PromocionDAO;
import cuponsmart.modelo.dao.PromocionSucursalDAO;
import cuponsmart.modelo.dao.SucursalDAO;
import cuponsmart.modelo.pojo.entidad.Categoria;
import cuponsmart.modelo.pojo.entidad.Direccion;
import cuponsmart.modelo.pojo.entidad.Empresa;
import cuponsmart.modelo.pojo.entidad.Estatus;
import cuponsmart.modelo.pojo.entidad.Promocion;
import cuponsmart.modelo.pojo.entidad.PromocionSucursal;
import cuponsmart.modelo.pojo.entidad.Sucursal;
import cuponsmart.modelo.pojo.entidad.TipoPromocion;
import cuponsmart.modelo.pojo.respuesta.Mensaje;
import cuponsmart.modelo.pojo.respuesta.interfaz.IRespuesta;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import utils.Constantes;
import utils.Utilidades;
import utils.Verificaciones;

public class FXMLFormPromocionController implements Initializable{
    private Promocion promocion;
    private ObservableList<Sucursal> sucursales;
    private IRespuesta observador;
    private Integer idTipo;
    private Integer idEstatus;
    private Integer idCategoria;
    private Integer idEmpresa;
    private File imagenSeleccionada;
    
    @FXML
    private Label txtTitulo;
    @FXML
    private Circle imagenFoto;
    @FXML
    private Button btnSeleccionarFoto;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtNumeroCupones;
    @FXML
    private TextField txtCodigo;
    @FXML
    private DatePicker pickerFechaInicio;
    @FXML
    private DatePicker pickerFechaTermino;
    @FXML
    private ComboBox<TipoPromocion> comboTipo;
    @FXML
    private TextField txtValor;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private TextArea txtRestricciones;
    @FXML
    private ComboBox<Estatus> comboEstatus;
    @FXML
    private ComboBox<Categoria> comboCategoria;
    @FXML
    private ComboBox<Empresa> comboEmpresa;
    @FXML
    private TableView<Sucursal> tbSucursales;
    @FXML
    private TableColumn<Sucursal, Boolean> clmSeleccion;
    @FXML
    private TableColumn clmNombre;
    @FXML
    private TableColumn clmEncargado;
    @FXML
    private TableColumn clmDireccion;
    @FXML
    private Button btnFinalizar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        this.sucursales = FXCollections.observableArrayList();
        configurarTabla();
        
        Utilidades.colocarImagenCircle(getClass().getResource("/img/noMedia.png"), imagenFoto);
        Utilidades.colocarImagenBoton(getClass().getResource("/img/foto.png"), btnSeleccionarFoto);
        
        descargarTiposPromocion();
        descargarEstatus();
        descargarCategorias();
        
        comboTipo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.idTipo = observable.getValue().getId();
        });
        
        comboEstatus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.idEstatus = observable.getValue().getId();
        });
        
        comboCategoria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.idCategoria = observable.getValue().getId();
        });
        
        comboEmpresa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.idEmpresa = observable.getValue().getId();
            
            rellenarTabla(this.idEmpresa, 0);
        });
    }
    
    private void configurarTabla(){
        clmSeleccion.setCellValueFactory(s -> s.getValue().promocionProperty());
        clmSeleccion.setCellFactory((s) -> new CheckBoxTableCell());
        clmNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        clmEncargado.setCellValueFactory(new PropertyValueFactory("nombreEncargado"));
        clmDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
    }
    
    private void descargarTiposPromocion(){
        List<TipoPromocion> tipos = CatalogoDAO.obtenerTiposPromocion();
        
        if(Verificaciones.listaNoVacia(tipos)){
            comboTipo.getItems().clear();
            comboTipo.getItems().addAll(tipos);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_TIPO, Alert.AlertType.ERROR);
    }
    
    private void descargarEstatus(){
        List<Estatus> estatus = CatalogoDAO.obtenerEstatus();
        
        if(Verificaciones.listaNoVacia(estatus)){
            comboEstatus.getItems().clear();
            comboEstatus.getItems().addAll(estatus);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_ESTATUS, Alert.AlertType.ERROR);
    }
    
    private void descargarCategorias(){
        List<Categoria> categorias = CategoriaDAO.obtenerCategorias();
        
        if(Verificaciones.listaNoVacia(categorias)){
            comboCategoria.getItems().clear();
            comboCategoria.getItems().addAll(categorias);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_CATEGORIA, Alert.AlertType.ERROR);
    }
    
    private void descargarEmpresas(Integer idEmpresa){
        List<Empresa> empresas = EmpresaDAO.obtenerEmpresas();
        
        if(Verificaciones.numerico(idEmpresa)){
            empresas.clear();
            empresas.add(EmpresaDAO.obtenerEmpresaPorId(idEmpresa));
        }
        
        if(Verificaciones.listaNoVacia(empresas)){
            comboEmpresa.getItems().clear();
            comboEmpresa.getItems().addAll(empresas);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.COMBO_EMPRESA, Alert.AlertType.ERROR);
    }
    
    private void rellenarTabla(Integer idEmpresa, Integer idPromocion){
        List<Sucursal> scrs = SucursalDAO.obtenerSucursalesPorIdEmpresa(idEmpresa);
        List<PromocionSucursal> promocionsucursales = PromocionSucursalDAO.obtenerPromocionesSucursalesPorIdPromocion(idPromocion);
        
        if(Verificaciones.listaNoVacia(scrs)){
            scrs.forEach((sucursal) -> {
                Direccion direccion = DireccionDAO.obtenerDireccionPorId(sucursal.getIdDireccion());
                sucursal.setDireccion(direccion.getCalle() + " " + direccion.getColonia() + " " + direccion.getNumero());
                
                if(Verificaciones.listaNoVacia(promocionsucursales)){
                    promocionsucursales.forEach((ps) -> {
                        if(sucursal.getId().equals(ps.getIdSucursal())) sucursal.setPromocion(true);
                    });
                }
            });
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "La empresa no cuenta con sucursales a las que aplicar", Alert.AlertType.WARNING);
        
        this.sucursales.clear();
        this.sucursales.addAll(scrs);

        tbSucursales.setItems(this.sucursales);
    }
    
    private void mostrarFoto(String fotoBase64){
        imagenFoto.setFill(new ImagePattern(
            new Image(new ByteArrayInputStream(
                Base64.getDecoder().decode(fotoBase64.replaceAll("\\n", ""))
            ))
        ));
    }
    
    private void obtenerFoto(){
        Promocion mensaje = MediaDAO.obtenerImagenPromocion(this.promocion.getId());
        
        if(Verificaciones.claseNoNula(mensaje) && Verificaciones.cadena(mensaje.getImagenBase64()))
            mostrarFoto(mensaje.getImagenBase64());
    }
    
    private void rellenarForm(){
        txtNombre.setText(this.promocion.getNombre());
        txtNumeroCupones.setText(this.promocion.getNumeroCupones().toString());
        txtCodigo.setText(this.promocion.getCodigo());
        pickerFechaInicio.setValue(LocalDate.parse(this.promocion.getFechaInicio()));
        pickerFechaTermino.setValue(LocalDate.parse(this.promocion.getFechaTermino()));
        comboTipo.getSelectionModel().select(CatalogoDAO.obtenerTipoPromocionPorId(this.promocion.getIdTipoPromocion()));
        txtValor.setText(this.promocion.getValor().toString());
        
        txtDescripcion.setText(this.promocion.getDescripcion());
        txtRestricciones.setText(this.promocion.getRestricciones());
        comboEstatus.getSelectionModel().select(CatalogoDAO.obtenerEstatusPorId(this.promocion.getIdEstatus()));
        comboCategoria.getSelectionModel().select(CategoriaDAO.obtenerCategoriaPorId(this.promocion.getIdCategoria()));
        comboEmpresa.getSelectionModel().select(EmpresaDAO.obtenerEmpresaPorId(this.promocion.getIdEmpresa()));
        
        obtenerFoto();
        rellenarTabla(this.promocion.getIdEmpresa(), this.promocion.getId());
    }
    
    public void inicializarInformacion(Promocion promocion, Integer idEmpresa, IRespuesta observador){
        this.promocion = promocion;
        this.idEmpresa = idEmpresa;
        this.observador = observador;
        
        CatalogoDAO.obtenerEstatus().forEach((estatus) -> {
            if(estatus.getEstado().equals("Activo")) comboEstatus.getSelectionModel().select(estatus);
        });
        
        descargarEmpresas(this.idEmpresa);
        
        comboEstatus.setDisable(true);
        
        if(Verificaciones.numerico(this.idEmpresa))
            comboEmpresa.getSelectionModel().select(0);
        
        if(Verificaciones.claseNoNula(this.promocion) && Verificaciones.numerico(this.promocion.getId())){
            txtTitulo.setText("Modificar Promoción");
            btnFinalizar.setText("Modificar");
            
            rellenarForm();
            
            comboEstatus.setDisable(false);
        }
    }
    
    private void mostrarFoto(File foto){
        try{
            imagenFoto.setFill(new ImagePattern(
                SwingFXUtils.toFXImage(ImageIO.read(foto), null)
            ));
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Excepciones.IO, Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void seleccionarFoto(ActionEvent event){
        FileChooser seleccionarFoto = new FileChooser();
        seleccionarFoto.setTitle(Constantes.Pantallas.SELECCION_IMAGEN);
        seleccionarFoto.getExtensionFilters().add(
            new FileChooser.ExtensionFilter(Constantes.Pantallas.ARCHIVOS_IMAGEN, "*.PNG", "*.JPG", "*.JPEG")
        );
        
        imagenSeleccionada = seleccionarFoto.showOpenDialog(txtNombre.getScene().getWindow());
        
        if(imagenSeleccionada != null) mostrarFoto(imagenSeleccionada);
    }
    
    private void cerrarVentana(){
        Stage escenario = (Stage) txtNombre.getScene().getWindow();
        escenario.close();
    }

    @FXML
    private void cancelar(ActionEvent event){
        cerrarVentana();
    }
    
    private Boolean cargarFoto(File foto, Integer idPromocion){
        try{
            byte[] fotoBytes = Files.readAllBytes(foto.toPath());
            Mensaje mensaje = MediaDAO.registrarImagenPromocion(idPromocion, fotoBytes);
            
            return !mensaje.getError();
        }catch(IOException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, "No se pudo cargar la foto, por favor inténtelo más tarde", Alert.AlertType.ERROR);
        }
        
        return false;
    }
    
    private void registrarPromocion(Promocion promocion){
        if(Verificaciones.claseNula(PromocionDAO.obtenerPromocionPorCodigo(promocion.getCodigo()))){
            Mensaje mensaje = PromocionDAO.registrarPromocion(promocion);
        
            if(Verificaciones.claseNoNula(mensaje) && !mensaje.getError()){
                Integer idPromocion = PromocionDAO.obtenerPromocionesPorIdEmpresa(promocion.getIdEmpresa()).stream().filter((p) -> (
                    promocion.getCodigo().equals(p.getCodigo())
                )).findFirst().get().getId();
                
                for(Sucursal sucursal : tbSucursales.getItems()){
                    if(sucursal.getPromocion()){
                        mensaje = PromocionSucursalDAO.registrarPromocionSucursal(new PromocionSucursal(
                            idPromocion,
                            sucursal.getId()
                        ));

                        if(Verificaciones.claseNula(mensaje) && mensaje.getError())
                            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
                    }
                }
                
                if(imagenSeleccionada != null)
                    if(!cargarFoto(imagenSeleccionada, idPromocion))
                        Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.MEDIA, Alert.AlertType.ERROR);

                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Promoción registrada exitosamente", Alert.AlertType.INFORMATION);
                observador.notificarGuardado();
                cerrarVentana();
            }else
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, "El código ya se encuentra registrado", Alert.AlertType.ERROR);
    }
    
    private void modificarPromocion(){
        Mensaje mensaje = PromocionDAO.modificarPromocion(this.promocion);
        
        if(Verificaciones.claseNoNula(mensaje) && !mensaje.getError()){
            for(Sucursal sucursal : tbSucursales.getItems()){
                PromocionSucursal ps = new PromocionSucursal(this.promocion.getId(), sucursal.getId());
                
                if(sucursal.getPromocion())
                    mensaje = PromocionSucursalDAO.registrarPromocionSucursal(ps);
                
                if(!sucursal.getPromocion())
                    mensaje = PromocionSucursalDAO.eliminarPromocionSucursal(ps);
                
                if(Verificaciones.claseNula(mensaje) && mensaje.getError())
                        Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
            }
            
            if(imagenSeleccionada != null)
                if(!cargarFoto(imagenSeleccionada, this.promocion.getId()))
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Errores.REGISTRO, Alert.AlertType.ERROR);
            
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.EXITO, "Promoción modificada exitosamente", Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarVentana();
        }else
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, mensaje.getMensaje(), Alert.AlertType.ERROR);
    }

    @FXML
    private void finalizar(ActionEvent event){
        try{
            String nombre = txtNombre.getText();
            Integer numeroCupones = !txtNumeroCupones.getText().isEmpty() ? Integer.parseInt(txtNumeroCupones.getText()) : 0;
            String codigo = txtCodigo.getText();
            String fechaInicio = pickerFechaInicio.getValue().toString();
            String fechaTermino = pickerFechaTermino.getValue().toString();
            TipoPromocion tipo = comboTipo.getSelectionModel().getSelectedItem();
            Float valor = !txtValor.getText().isEmpty() ? Float.parseFloat(txtValor.getText()) : 0;

            String descripcion = txtDescripcion.getText();
            String restricciones = !txtRestricciones.getText().isEmpty() ? txtRestricciones.getText() : "No aplica";
            Estatus estatus = comboEstatus.getSelectionModel().getSelectedItem();
            Categoria categoria = comboCategoria.getSelectionModel().getSelectedItem();
            Empresa empresa = comboEmpresa.getSelectionModel().getSelectedItem();
            Boolean sucursales = false;
            
            for(Sucursal sucursal : tbSucursales.getItems()){
                if(sucursal.getPromocion())
                    sucursales = true;
            }

            if(Verificaciones.cadena(nombre) && Verificaciones.numerico(numeroCupones) && Verificaciones.cadena(codigo) && Verificaciones.cadena(fechaInicio) &&
                Verificaciones.cadena(fechaTermino) && Verificaciones.claseNoNula(tipo) && Verificaciones.numerico(valor) && Verificaciones.cadena(descripcion) &&
                Verificaciones.cadena(restricciones) && Verificaciones.claseNoNula(estatus) && Verificaciones.claseNoNula(categoria) && Verificaciones.claseNoNula(empresa) &&
                Verificaciones.listaNoVacia(tbSucursales.getItems()) && sucursales){
                if(codigo.length() == 8){
                    switch(btnFinalizar.getText()){
                        case "Registrar":
                            registrarPromocion(new Promocion(0, nombre, descripcion, "", fechaInicio, fechaTermino, restricciones, numeroCupones, codigo, valor, this.idEstatus, this.idCategoria, this.idEmpresa, this.idTipo));
                            break;
                        case "Modificar":
                            if(!this.promocion.getIdEmpresa().equals(this.idEmpresa))
                                PromocionSucursalDAO.eliminarPromocionSucursales(this.promocion.getId());

                            this.promocion.setNombre(nombre);
                            this.promocion.setNumeroCupones(numeroCupones);
                            this.promocion.setCodigo(codigo);
                            this.promocion.setFechaInicio(fechaInicio);
                            this.promocion.setFechaTermino(fechaTermino);
                            this.promocion.setIdTipoPromocion(this.idTipo);
                            this.promocion.setValor(valor);
                            this.promocion.setDescripcion(descripcion);
                            this.promocion.setRestricciones(restricciones);
                            this.promocion.setIdEstatus(this.idEstatus);
                            this.promocion.setIdCategoria(this.idCategoria);
                            this.promocion.setIdEmpresa(this.idEmpresa);

                            modificarPromocion();
                            break;
                        default:
                            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ERROR, Constantes.Retornos.SELECCION, Alert.AlertType.ERROR);
                    }
                }else
                    Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, "El código debe de ser de 8 caracteres, favor de verificarlo", Alert.AlertType.WARNING);
            }else if(!Verificaciones.numerico(numeroCupones) || !Verificaciones.numerico(valor))
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Errores.CAMPOS_NUMERICOS, Alert.AlertType.WARNING);
            else if(!Verificaciones.listaNoVacia(tbSucursales.getItems()))
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Errores.SIN_SUCURSALES, Alert.AlertType.WARNING);
            else if(!sucursales)
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.ALERTA, Constantes.Errores.SIN_SELECCION_SUCURSAL, Alert.AlertType.WARNING);
            else
                Utilidades.mostrarAlertaSimple(Constantes.Pantallas.CAMPOS_VACIOS, Constantes.Errores.CAMPOS_VACIOS, Alert.AlertType.WARNING);
        }catch(NullPointerException e){
            Utilidades.mostrarAlertaSimple(Constantes.Pantallas.CAMPOS_VACIOS, Constantes.Errores.CAMPOS_VACIOS, Alert.AlertType.WARNING);
        }
    }
}