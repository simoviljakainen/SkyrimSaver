/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrimsavergui;

import java.io.File;

/**
 *
 * @author LpMilleniumMikki
 */
public class FileF extends File{
    
    public FileF(String pathname) {
        super(pathname);
    }
    
    @Override
    public String toString(){
        return this.getName()+ ", "+this.getPath();
    }
    public String toString(boolean f){
        return this.getName();
    }
}
