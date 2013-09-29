package com.pis.encriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptor{

	private static final byte[] salt = {
		    (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
		    (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
		  };
	
	private static final String ALGORITHM = "PBEWithMD5AndDES";

	public static void encrypt(String input, String output, String password) throws Exception{	 
		//opening streams
		File filein, fileout;
		filein=new File(input);
		fileout=new File(output);

		FileInputStream fis =new FileInputStream(filein);
		FileOutputStream fos =new FileOutputStream(fileout);
		
		//generating key
		PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt,5000);
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
		
		SecretKeyFactory keyFac = SecretKeyFactory.getInstance(ALGORITHM);
		
		SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
		//creating and initialising cipher and cipher streams
		Cipher encrypt = Cipher.getInstance(ALGORITHM);  
		
		encrypt.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);  
		CipherOutputStream cout=new CipherOutputStream(fos, encrypt);

		byte[] buf = new byte[1024];
		int read = 0;
		while((read=fis.read(buf))!=-1){
			cout.write(buf,0,read);  //writing encrypted data
		}//reading data
		//closing streams
		
		fis.close();
        cout.flush();
        cout.close();
	}

	public static void decrypt(String input, String output, String password) throws Exception{
		//opening streams
		File filein, fileout;
		filein=new File(input);
		fileout=new File(output);

		FileInputStream fis =new FileInputStream(filein);
		FileOutputStream fos =new FileOutputStream(fileout);
		
		//generating key
		PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt,5000);
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
		
		SecretKeyFactory keyFac = SecretKeyFactory.getInstance(ALGORITHM);
		
		SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
		
		//creating and initialising cipher and cipher streams	
		Cipher decrypt = Cipher.getInstance(ALGORITHM);  
		decrypt.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);  
		CipherInputStream cin=new CipherInputStream(fis, decrypt);

		byte[] buf = new byte[1024];
		int read=0;
		while((read=cin.read(buf))!=-1){ //reading encrypted data
			System.out.println(read);
			fos.write(buf,0,read);  //writing decrypted data
		}
		//closing streams
		cin.close();
        fos.flush();
        fos.close();
	}

	public static void main (String[] args)throws Exception {
		FileEncryptor.encrypt("./prueba/uno.txt","./prueba/uno.enc.txt","12345678913");
		FileEncryptor.decrypt("./prueba/uno.enc.txt","./prueba/uno.dec.txt","12345678913");
	}
}