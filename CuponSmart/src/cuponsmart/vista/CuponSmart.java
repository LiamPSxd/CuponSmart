package cuponsmart.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Constantes;

public class CuponSmart extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLInicioSesion.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(Constantes.Pantallas.INICIO_SESION);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}