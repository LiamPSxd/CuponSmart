package utils;

import java.net.URL;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Utilidades{
    public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipo){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    public static Boolean mostrarAlertaConfirmacion(String titulo, String mensaje){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        
        return alerta.showAndWait().get() == ButtonType.OK;
    }
    
    public static void colocarImagenCircle(URL url, Circle circle){
        circle.setFill(new ImagePattern(new Image(url.toString())));
    }
    
    public static void colocarImagenBoton(URL url, Button boton){
        Image imagen = new Image(url.toString(), 32, 32, false, true);
        
        boton.setGraphic(new ImageView(imagen));
    }
}