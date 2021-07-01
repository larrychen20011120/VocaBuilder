package scene.home;

import appObject.Dictionary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("EZcard");
        primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        primaryStage.show();

        Dictionary.load();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
