package Java.Main;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;




public class App extends Application
{
    public static final String Directory    = System.getProperty("user.dir");
    public static final String FileFXML = Directory + "\\src\\fxml\\ApplicationPageController.fxml"
    public static void main(String[] args) throws Exception {
        System.out.println("Running...");
        launch(args);
    }

    @Override
    public void start(Stage state) throws Exception {
        // TODO Auto-generated method stub
        
    }
}
