/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrimsavergui;


import java.io.File;
import java.io.IOException;
import javafx.scene.paint.Color;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javax.swing.JFileChooser;

/**
 *
 * @author LpMilleniumMikki
 */
public class FXMLDocumentController implements Initializable {
    private int interval;
    public static boolean running = false;
    private Label label;
    public static int counter = 0;
    private Timer timer;
    private String preLocation = ""; 
    
    @FXML
    private TextField fileToBackField;
    @FXML
    private TextField backUpFolderField;
    @FXML
    private TextField minutesField;
    @FXML
    private TextField secondsField;
    @FXML
    private Button startSavingButton;
    @FXML
    private Label statusLabel;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private Font x1;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private Label fileCountLabel;
    @FXML
    private ListView<FileF> fileList;
    @FXML
    private Pane underPane;
    @FXML
    private Font x2;
    @FXML
    private TextField archiveFileCountField;
    @FXML
    private Tooltip presetTooltip;

    
    private String getFileExtension(FileF file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }
    
    public Label getStatusLabel() {
        return statusLabel;
    }

    public void setFileCount(String txt) {
        this.fileCountLabel.setText(txt);
    }
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileToBackField.setText(System.getProperty("user.home"));
        backUpFolderField.setText(System.getProperty("user.home"));
    }    

    @FXML
    private void startSavingAction(ActionEvent event) throws InterruptedException, IOException {
        if(running){
            startSavingButton.setText("Start Saving!");
            statusLabel.setText("DISABLED");
            statusLabel.setTextFill(Color.RED);
            running = false;
            timer.cancel();
            timer.purge();
            
       }else{
            if(FileStorage.getInstance().getFiles().isEmpty()){
                statusLabel.setTextFill(Color.ORANGE);
                statusLabel.setText("Add files first.");
                
            }else{
                if(!backUpFolderField.getText().isEmpty()){
                    try{
                        interval = Integer.parseInt(minutesField.getText())*60+Integer.parseInt(secondsField.getText());
                        
                        if(interval > 0){
                            
                            String pathList = "";
                            timer = new Timer(true);
                            ObservableList<FileF> fList = FileStorage.getInstance().getFiles();

                            for(FileF f :fList){

                                timer.schedule(new checkFile(backUpFolderField.getText(),f.getPath(),this, Integer.parseInt(archiveFileCountField.getText())),
                                        0, interval*1000);
                                pathList = pathList+f.getPath()+"\n";
                            }
                            FileIO.write(backUpFolderField.getText()+"\\Presets\\preset.lpk",pathList);

                            startSavingButton.setText("STOP");
                            statusLabel.setTextFill(Color.GREENYELLOW);
                            statusLabel.setText("ENABLED");
                            running = true;
                        }else{
                            statusLabel.setTextFill(Color.RED);
                            statusLabel.setText("Invalid interval.");
                        }

                    }catch(IOException | NumberFormatException e){
                        statusLabel.setTextFill(Color.RED);
                        statusLabel.setText("Invalid values.");
                    }
                    
                }else{
                    statusLabel.setTextFill(Color.RED);
                    statusLabel.setText("Set a backup folder.");
                }
                
                
            }
            
 
        }
    }

    @FXML
    private void addFileAction(ActionEvent event) {
        FileF essFile = new FileF(fileToBackField.getText());
        
        if(!essFile.exists()){
            statusLabel.setText("File not found.");
            statusLabel.setTextFill(Color.ORANGE);
            System.out.println("not found");
            
        }else{
            statusLabel.setTextFill(Color.AQUAMARINE);
            statusLabel.setText("File added.");
            String ext = getFileExtension(essFile);
            System.out.println(ext);
            
            FileStorage st = FileStorage.getInstance();
            st.setFiles(essFile);
            
            fileList.setItems(st.getFiles());
        }

    }

    @FXML
    private void removeListFile(MouseEvent event) {
        
        if(!FileStorage.getInstance().getFiles().isEmpty()){
             
             statusLabel.setText(fileList.getSelectionModel().getSelectedItem().getName()
                     +" removed.");
             FileStorage.getInstance().removeFile(fileList.getSelectionModel()
                .getSelectedItem());
             statusLabel.setTextFill(Color.AQUAMARINE);
        }
        
    }

    @FXML
    private void loadPresetAction(ActionEvent event) throws IOException {
        
        if(new FileF(backUpFolderField.getText()+"\\Presets\\preset.lpk").exists()
                &&new FileF(backUpFolderField.getText()+"\\Presets\\preset.lpk").length() != 0){
            String[] pathList = FileIO.read(backUpFolderField.getText()+"\\Presets\\preset.lpk").split("\\n");
            System.out.println(pathList.length);
        
            for(String s : pathList){
                FileStorage.getInstance().setFiles(new FileF(s));
            }
            fileList.setItems(FileStorage.getInstance().getFiles());
            statusLabel.setTextFill(Color.AQUAMARINE);
            statusLabel.setText("Files loaded.");
        }else{
            statusLabel.setTextFill(Color.AQUAMARINE);
            statusLabel.setText("preset.lpk not found, or it's empty.");
        }
        
    }


    @FXML
    private void openFilePickerf1(MouseEvent event) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        int result = fileChooser.showOpenDialog(fileChooser);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File sFile = fileChooser.getSelectedFile();
            backUpFolderField.setText(sFile.getPath());
        }
    }

    @FXML
    private void openFilePickerf2(MouseEvent event) {
        String path;
        
        if(!preLocation.isEmpty()){
            path = preLocation;
        }else{
            path = System.getProperty("user.home");
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(path));
        int result = fileChooser.showOpenDialog(fileChooser);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File sFile = fileChooser.getSelectedFile();
            fileToBackField.setText(sFile.getPath());
            preLocation = sFile.getPath();
        }
    }
    
}
