/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skyrimsavergui;

/**
 *
 * @author LpMilleniumMikki
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class FileIO {
    
    public static void write(String outputFile, String input) throws IOException {
        
        // make directories if needed
        File file = new File(outputFile);
        file.getParentFile().mkdirs();
        
        
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write(input);
        }
        
    }
    
    public static String read(String file) throws IOException{
             
        String text = "";
        String line;
        BufferedReader rFile = new BufferedReader(new FileReader(file));

        while(true){
            line = rFile.readLine();
            if(line == null){
                rFile.close();
                return text;
            }
            else{
                text += line+'\n';
            }
        }
    }
    
    public static String read(File file) throws IOException{
             
        String text = "";
        String line;
        BufferedReader rFile = new BufferedReader(new FileReader(file));

        while(true){
            line = rFile.readLine();
            if(line == null){
                rFile.close();
                return text;
            }
            else{
                text += line+'\n';
            }
        }
    }
    
    public static ArrayList<File> getFilesInFolder(String folderName, String filterString) {
        ArrayList<File> files = new ArrayList<>();
        
        FilenameFilter filter = (File file, String name) -> name.toLowerCase().endsWith(filterString);
        File folder = new File(folderName);
        File[] filesArr = folder.listFiles(filter);
        for (File f : filesArr) {
            files.add(f);
        }
        
        return files;
    }
    
public static boolean zipFiles(String out, File[] in) {
		
		String zipFile = out;
		
		File[] srcFiles = in;
		
		try {
			
			// create byte buffer
			byte[] buffer = new byte[1024];

			FileOutputStream fos = new FileOutputStream(zipFile);

			ZipOutputStream zos = new ZipOutputStream(fos);
			
			for (int i=0; i < srcFiles.length; i++) {
                                System.out.println("zipping "+srcFiles[i].getName());

				FileInputStream fis = new FileInputStream(srcFiles[i].getPath());

				// begin writing a new ZIP entry, positions the stream to the start of the entry data
				zos.putNextEntry(new ZipEntry(srcFiles[i].getPath()));
				
				int length;

				while ((length = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, length);
				}

				zos.closeEntry();

				// close the InputStream
				fis.close();
				
			}

			// close the ZipOutputStream
			zos.close();
                        System.out.println("close output");
                        return true;
			
		}
		catch (IOException ioe) {
			System.out.println("Error creating zip file: " + ioe);
                        return false;
		}
		
	}
    
}
