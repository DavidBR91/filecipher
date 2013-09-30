package com.pis.encriptor;

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

    public static void main(String[] args) {

        String path = "";
        CommandLineParser parser = null;
        CommandLine cmdLine = null;

        //Opciones de validacion de entrada
        Options options = new Options();
        options.addOption("p", true, "Ruta del fichero o directorio");
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
                
            }
            
            if (cmdLine.hasOption("d")) {
                
            }
            
            if (cmdLine.hasOption("k")) {
                
            }
        } catch (ParseException ex) {
            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
