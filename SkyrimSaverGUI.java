/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrimsavergui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.UIManager;

/**
 *
 * @author LpMilleniumMikki
 */
public class SkyrimSaverGUI extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
       
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SkyrimSaverGUI.class.getResource("stylesheet.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("SkyrimSaver 1.1");
        stage.setResizable(false);
        stage.getIcons().add(new Image(SkyrimSaverGUI.class.getResourceAsStream("icon.png")));
        stage.show();
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
