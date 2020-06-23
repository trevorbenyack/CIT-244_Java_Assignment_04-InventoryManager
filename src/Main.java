import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Trevor Benyack
 * CIT-244
 * Assignment 04
 * Due 2020-06-27
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // the Inventory class contains all the file I/O logic for the program. It is created once and passed
        // throughout the program.
        Inventory inventory = new Inventory();

        // setup for program's UI
        GUI_01_Main mainPane = new GUI_01_Main(inventory);
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add("stylesheet.css");
        primaryStage.setTitle("Inventory Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
