package com.pis.encriptor;

import java.io.File;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Cipher {

    public static int checkFlags(boolean encrypt,
            String path) {
    	
    	int fencrypt = 1;
    	int fdir = 2;
    			
    	if(!encrypt) fencrypt = 0;
    	if(!esDirectorio(path)) fdir = 0;
    	
    	return fencrypt | fdir;
        
    }

    public static boolean esDirectorio(String path) {
        File fRuta = new File(path);
        if (fRuta.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }
    
	public static void recursiveEncryptor (String input, String password, boolean keep) throws Exception {
		File source = new File(input);
		String output = input;
		Collection<String> files;
		
		if(keep){
			File destiny = new File (source.getAbsolutePath() + ".enc");
			FsController.copyDirectory(source, destiny);
			files = FsController.listFileTree(destiny.getAbsolutePath());
		}
		else{
			files = FsController.listFileTree(output);
		}
		for (String f: files) {
			FileEncryptor.encrypt(f,password, false);
		}
	}
	
	public static void recursiveDecryptor (String path, String password) throws Exception {
		Collection<String> files = FsController.listFileTree(path);
		for (String f: files) {
			FileEncryptor.decrypt(f, password);
		}
	}

    public static void main(String[] args) {
        CommandLineParser parser;
        CommandLine cmdLine;
        boolean flagEncrypt = true; //Por defecto encripta
        boolean keep = false;   //Por defecto está desactivado

        //Opciones de validacion de entrada
        Options options = new Options();
        options.addOption("k", false, "Mantiene una copia original del"
                + " fichero o directorio a encriptar");
        options.addOption("h", false, "Muestra la ayuda");

        //No pueden aparecer simultanemente las siguientes ops
        OptionGroup group = new OptionGroup();
        group.addOption(new Option("e", "Encripta el fichero o directorio"));
        group.addOption(new Option("d", "Desencripta el fichero o directorio"));
        options.addOptionGroup(group);

        try {
            //Parseamos la entrada estandar
            parser = new BasicParser();
            cmdLine = parser.parse(options, args);

            //Analiza resultados y realiza operaciones 
            if (cmdLine.hasOption("h")) {
                new HelpFormatter().printHelp(Cipher.class.getCanonicalName(),
                        options);
                return;
            }

            if (cmdLine.hasOption("d")) {
                flagEncrypt = true;
            } else if (cmdLine.hasOption("e")) {
                flagEncrypt = false;
            }

            if (cmdLine.hasOption("k")) {
                keep = true;
            }

            final String[] remainingArgs = cmdLine.getArgs();

            //Compruebas  -e -d y -k
            //Comprueba si es directorio
            //Comprueba si es -e o -d
            //Si es -e:
            //4 posibles caminos:
            //Es dir y Keep activo
            //Es fich y Keep activo
            //Dir y Keep des
            //Fich y Keep des
            //Si es -d:
            //Es dir
            //Es fich
            //
            if (remainingArgs.length < 1) {
                System.err.println("Error en los parametros");
            } else {
                String path = remainingArgs[0];
                String password = remainingArgs[1];
                //Ejecuta el algoritmo
                int flags = checkFlags(flagEncrypt, path);
                
                System.out.println("Realizando operacion...");
                
                switch(flags){
            	case 0 :
            		FileEncryptor.decrypt(path, password);
            		break;
            	case 1 :
            		FileEncryptor.encrypt(path, password, keep);
            		break;
            	case 2 :
            		recursiveDecryptor(path, password);
            		break;
            	case 3 :
            		recursiveEncryptor(path, password, keep);
            		break;
            	}
                System.out.println("Correctamente realizada");
            }

        } catch (Exception ex) {
        	 new HelpFormatter().printHelp(Cipher.class.getCanonicalName(), options);
        }

    }

}
