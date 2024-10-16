package fes.aragon.inicio;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

import java.util.Objects;

/*Elaborado por:
Bautista Solis Juan Pedro
Calderon Almanza Marvin Daniel
Villanueva Ricardo
González Amezquita Héctor Rogelio
*/

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Pane root = (Pane)FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fes/aragon/xml/Principal.fxml")));

            Scene scene = new Scene(root);

            scene.getStylesheets().add(getClass().getResource("/fes/aragon/css/application.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
