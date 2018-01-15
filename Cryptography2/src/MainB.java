import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MainB {
	
	private static final String SHA_512 = "SHA-512";
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.print("Enter text file name: ");
		String fileName = scan.nextLine();
		byte [] digesta = null;
		byte [] digestb = null;
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
			String text = (String) in.readObject();
			System.out.println(text);
			byte [] textArray = text.getBytes();
			MessageDigest md512 = MessageDigest.getInstance(SHA_512);
			md512.update(textArray);
			digestb = md512.digest();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		fileName += "Digest";
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
			digesta =  (byte[]) in.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		for (byte theByte : digesta) {
			sb.append(String.format("%02x", theByte & 0xff));
		}
		System.out.println("Digest A: " + sb);
		sb = new StringBuilder();
		for (byte theByte : digestb) {
			sb.append(String.format("%02x", theByte & 0xff));
		}
		System.out.println("Digest B: " + sb);
		if (digesta != null && MessageDigest.isEqual(digesta, digestb))
			System.out.println("Data is OK");
		else
			System.out.println("Data not OK");
	}
}
