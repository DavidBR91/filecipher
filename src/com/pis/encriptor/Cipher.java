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

    public static int compruebaFlags(boolean encriptar, boolean keep,
            String path) {
        int resultado = 0;

        if (encriptar && keep) {
            if (esDirectorio(path)) {
                resultado = 0;
            } else {
                resultado = 1;
            }
        } else if (encriptar && !keep) {
            if (esDirectorio(path)) {
                resultado = 2;
            } else {
                resultado = 3;
            }
        } else if (!encriptar) {
            if (esDirectorio(path)) {
                resultado = 4;
            } else {
                resultado = 5;
            }
        }
        return resultado;
    }

    public static boolean esDirectorio(String path) {
        File fRuta = new File(path);
        if (fRuta.isDirectory()) {
            return true;
        } else {
            return false;
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

            if (cmdLine.hasOption("e")) {
                flagEncrypt = true;
            } else if (cmdLine.hasOption("d")) {
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
            if (false) {
                System.err.println("Error en los parametros");
            } else {
                String path = remainingArgs[0];
                String password = remainingArgs[1];
                int flags;
                //Ejecuta el algoritmo
                flags = compruebaFlags(flagEncrypt, keep, path);

                switch (flags) {
                    case (0):
                        //recursiveEncrypt(path, path+".enc", password;
                        break;
                    case (1):
                        FileEncryptor.encrypt(path, path + ".enc", password);
                        break;
                    case (2):
                        //recursiveEncrypt(path, path, password);
                        break;
                    case (3):
                        FileEncryptor.encrypt(path, path, password);
                    case (4):
                        //recursiveDecrypt(path, path, password);
                        break;
                    case (5):
                        FileEncryptor.decrypt(path, path, password);
                        break;
                    default:
                        System.err.println("Error de las opciones");
                        break;
                }

            }

        } catch (ParseException ex) {
            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.NumberFormatException ex) {
            // Error, imprimimos la ayuda  
            new HelpFormatter().printHelp(Cipher.class.getCanonicalName(), options);
        } catch (Exception ex) {
            Logger.getLogger(Cipher.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
