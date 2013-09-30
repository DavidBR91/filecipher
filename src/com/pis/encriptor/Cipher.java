package com.pis.encriptor;

import java.io.File;
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

    public static void compruebaPath(String path) {
        File fRuta = new File(path);

        if (fRuta.isDirectory()) {
            recursiveEncrypt(String path);
        } else {
            //El output no debería estar si el flag keep no esta activo
            FileEncryptor.encrypt(path, path, path);
        }
    }

    public static void main(String[] args) {

        String path = "";
        CommandLineParser parser = null;
        CommandLine cmdLine = null;
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

            if (cmdLine.hasOption("e")) {
                flagEncrypt = true;
            } else if (cmdLine.hasOption("d")) {
                flagEncrypt = false;
            }

            if (cmdLine.hasOption("k")) {
                keep = true;
            }

            final String[] remainingArgs = cmdLine.getArgs();

            if (remainingArgs.length != 1) {
                System.err.println("Error en los parametros");
            } else {
                //Ejecuta el algoritmo  
                compruebaPath(remainingArgs[0]);
            }

        } catch (ParseException ex) {
            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.NumberFormatException ex) {
            // Error, imprimimos la ayuda  
            new HelpFormatter().printHelp(Cipher.class.getCanonicalName(), options);
        }

    }

}
