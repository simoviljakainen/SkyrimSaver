/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrimsavergui;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author LpMilleniumMikki
 */
class checkFile extends TimerTask {
    
    private String timeStamp;
    
    private String saverPath;
    private String essQuickFilePath;
    private String skseQuickFilePath; 
    private String essPath;
    private String sksePath; 
    private File essFile;
    private File skseFile;
    private Long state;
    private int archiveInt;
    private FXMLDocumentController controller;
    
    
    public checkFile(String ssPath, String ep, FXMLDocumentController cont, int ai){
        
        saverPath = ssPath;
        essPath = ep;
        
        controller = cont;

        essFile = new File(essPath);
        archiveInt = ai;
        
        //Pointless
        if(!essFile.exists()){
            System.out.println("not found");
        }else{
             state =  essFile.lastModified();
        }
        
    }
    
    public void checkFileCount(){
        File sFolder = new File(saverPath+"\\Saves");
        int fileCount = sFolder.listFiles().length;
        File[] listOfFiles = sFolder.listFiles();
        
        if(fileCount > archiveInt && archiveInt > 0){
            new File(saverPath+"\\Archive").mkdir();
            String ts = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());    
            Boolean sc = FileIO.zipFiles(saverPath+"\\Archive\\archive -"+ts+".zip",listOfFiles);
            
            if(sc){
                for(File f : listOfFiles){
                    f.delete();
                }
            }
        }
    }
    
    private void copyFile(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {

            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
           is.close();
            os.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public void run() {

        if(essFile.lastModified()!=state){
            if(new File(saverPath+"\\Saves").exists()){
                try{
                    System.out.println(essFile.getName()+" changed!");
                    String[] parsedPath = essPath.split("\\.");
                    timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
                    copyFile(essFile, new File(saverPath+"\\Saves\\"+timeStamp+"."+parsedPath[parsedPath.length-1]));
                    checkFileCount();
                                        
                    Platform.runLater(new Runnable(){ 
                        @Override
                        public void run() {
                            FXMLDocumentController.counter++;
                            controller.setFileCount(FXMLDocumentController.counter+"");
                            
                        }
                    });
                    
                }catch(IOException e){
                    System.out.println(e);
                }
                
            }else{
                new File(saverPath+"\\Saves").mkdir();
                //new File(saverPath+"\\Presets").mkdir();
                run();
            }
            state =  essFile.lastModified();
            
        }else{
            System.out.println(essFile.getName()+" not changed!");
        }
       
    }
    
}

