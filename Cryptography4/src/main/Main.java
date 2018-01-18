package main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Scanner;

public class Main {

	private static Scanner scan = new Scanner(System.in);
	private static final String DSA = "DSA";
	private static final String SHA = "SHA1PRNG";
	private static final String SHA_DSA = "SHA1withDSA";
	private static final String SIGNATURE_FILE = "signature";
	private static final String PUB_FILE = "pub";
	
	public static void main(String[] args) {
		// Ask for file
		System.out.print("Enter file name: ");
		String filename = scan.nextLine();
		try {
			// Generate public and private keys
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(DSA);
			SecureRandom random = SecureRandom.getInstance(SHA);
			keyGen.initialize(1024, random);
			KeyPair pair = keyGen.generateKeyPair();
			PrivateKey priv = pair.getPrivate();
			PublicKey pub = pair.getPublic();
			// Sign data
			Signature dsa = Signature.getInstance(SHA_DSA);
			dsa.initSign(priv);
			BufferedInputStream bufin = new BufferedInputStream(new FileInputStream(filename));
			byte [] buffer = new byte [1024];
			int len;
			while ((len = bufin.read(buffer)) >= 0) {
				dsa.update(buffer, 0, len);
			}
			bufin.close();
			byte [] realSig = dsa.sign();
			// Save signature into file
			FileOutputStream fout = new FileOutputStream(SIGNATURE_FILE);
			fout.write(realSig);
			fout.close();
			fout = new FileOutputStream(PUB_FILE);
			fout.write(pub.getEncoded());
			fout.close();
			System.out.println("THE END");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}
}
