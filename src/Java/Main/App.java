package Java.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;




public class App extends Application
{
    public static final String Directory    = System.getProperty("user.dir");
    public static final String DirectoryFiles_FXML = Directory + "\\src\\fxml";

    public static HashMap<String, String> FxmlFile_Paths = new HashMap<String, String>();


    public static void main(String[] args) throws Exception {
        System.out.println("Running...");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception 
    {
        try { 

            //************ Caricamento File FXML ************//
            File[] files = new File(DirectoryFiles_FXML).listFiles();

            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".fxml")) {
                    FxmlFile_Paths.put(file.getName().replace(".fxml", ""), "fxml/" + file.getName());
        
                }
            }

            //************ Impostazioni pagina ************//
            
            stage.setTitle("Circuit Simulator");
            SetStage(stage, "ApplicationPage");
            stage.show();

            //lambda function 
            stage.setOnCloseRequest(event -> {
                event.consume();
                System.exit(0);
                
            });

            

            System.out.println("starting stage"); 
        } catch(NullPointerException e) {
            System.out.println("File non trovato, errore nel percorso del file fxml ( URL = null ?)\nError: ");
            System.out.println(e);

        } catch(Exception e)  {
           e.printStackTrace();
        }
    }

    public void SetStage(Stage stage, String name) throws IOException, NullPointerException 
    {
        System.out.println("Loading: " + FxmlFile_Paths.get(name));
        URL fxmlLocation = getClass().getClassLoader().getResource(FxmlFile_Paths.get(name));
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    public FXMLLoader getStageFXMLLoader(String name) throws IOException  {
        return new FXMLLoader(getClass().getClassLoader().getResource(FxmlFile_Paths.get(name)));
    }
}
