/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrimsavergui;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author LpMilleniumMikki
 */
public class FileStorage {
    private ObservableList<FileF> files = FXCollections.observableArrayList();
    private File file;
    private static FileStorage storageInstance = null;
    
    public static FileStorage getInstance(){
        if(storageInstance == null){
            storageInstance =  new FileStorage();
        }
            
        return storageInstance;
    }
       
    public ObservableList<FileF> getFiles() {
        return files;
    }
    
    public boolean checkFile(File f){
        if(files.indexOf(f)!=-1){
            return true;
        }
        return false;
    }

    public void setFiles(FileF f) {
        if(!this.checkFile(f)){
            this.files.add(f);
        }
    }
    
    public void removeFile(FileF f){
        this.files.remove(f);
    }
    
}
