package main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;

public class MainVerify {
	
	private static Scanner scan = new Scanner(System.in);
	private static final String PUB_FILE = "pub";
	private static final String SIGNATURE_FILE = "signature";
	private static final String DSA = "DSA";
	private static final String SHA_DSA = "SHA1withDSA";

	public static void main(String[] args) {
		byte [] sigBytes = null;
		PublicKey pub = null;
		Signature sig = null;
		try (FileInputStream fin = new FileInputStream(PUB_FILE)) {
			// Convert encoded public key into actual public key
			byte [] encKey = new byte[fin.available()];
			fin.read(encKey);
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
			KeyFactory keyFactory = KeyFactory.getInstance(DSA);
			pub = keyFactory.generatePublic(pubKeySpec);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		try (FileInputStream fin = new FileInputStream(SIGNATURE_FILE)) {
			// Input signature bytes
			sigBytes = new byte[fin.available()];
			fin.read(sigBytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Verify signature
		System.out.print("Enter file name: ");
		String fileName = scan.nextLine();
		try (BufferedInputStream buf = new BufferedInputStream(new FileInputStream(fileName))) {
			sig = Signature.getInstance(SHA_DSA);
			sig.initVerify(pub);
			byte [] buffer = new byte[1024];
			int len;
			while (buf.available() != 0) {
				len = buf.read(buffer);
				sig.update(buffer, 0, len);
			}
			// Verify and report result
			System.out.print("Signature verifies: " + sig.verify(sigBytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
	}
}
