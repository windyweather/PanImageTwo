package net.windyweather.panimagetwo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PanImageTwo extends Application {

    PanImageTwoController pitController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PanImageTwo.class.getResource("panimagetwo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 800);
        stage.setTitle("Pan Image Test Two");
        stage.setScene(scene);
        stage.show();
        /*
            Call the controller to initialize things
         */
        pitController = fxmlLoader.getController();
        pitController.SetUpStuff();
    }

    public static void main(String[] args) {
        launch();
    }
}