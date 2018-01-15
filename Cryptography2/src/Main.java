import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

	private static final String SHA_512 = "SHA-512";
	private static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.print("Enter a text: ");
		String text = in.nextLine();
		System.out.print("Enter file name: ");
		String fileName = in.nextLine();
		try (ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(fileName))) {
			oo.writeObject(text);
			System.out.println("File saved");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fileName += "Digest";
		try (ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(fileName))) {
			byte [] textArray = text.getBytes();
			MessageDigest md512 = MessageDigest.getInstance(SHA_512);
			md512.update(textArray);
			oo.writeObject(md512.digest());
			System.out.println("Digest saved in file " + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
