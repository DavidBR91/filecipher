package com.pis.encriptor;

import java.io.File;
import java.util.ArrayList;

public class FsController {
	
	
	public static ArrayList<String> walk(String path, ArrayList<String> paths) {
		
		ArrayList<String> acf = new ArrayList<String>();
		acf.addAll(paths);

        File root = new File( path );
        File[] list = root.listFiles();

        if (list != null){
            for(File f : list) {
                if (f.isDirectory()) {
                	System.out.println("dir");
                    acf.addAll(walk(f.getAbsolutePath(), acf));
                }
                else {
                    acf.add(f.getAbsoluteFile().getPath());
                }
            }
        }  
        return acf;

    }

    public static void main(String[] args) {
        System.out.println(FsController.walk(".", new ArrayList<String>()).toString());
    }
}
