package com.pis.encriptor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class FsController {
	
	public static Collection<String> listFileTree(String path) {
		File dir = new File(path);
	    Set<String> fileTree = new HashSet<String>();
	    for (File entry : dir.listFiles()) {
	        if (entry.isFile()) fileTree.add(entry.getAbsolutePath());
	        else fileTree.addAll(listFileTree(entry.getAbsolutePath()));
	    }
	    return fileTree;
	}

    public static void main(String[] args) {
        //System.out.println(FsController.walk(".", new LinkedList<File>()).toString());
    	Collection<String> files= FsController.listFileTree(".");
    	for(String f : files){
    		try {
    			System.out.println(f);
				FileEncryptor.encrypt(f, "hols", "1234");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}
