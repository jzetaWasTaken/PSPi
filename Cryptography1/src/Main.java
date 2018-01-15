import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
	
	private static Scanner in = new Scanner(System.in);
	private static final String SHA_256 = "SHA-256";
	private static final String SHA_512 = "SHA-512";

	public static void main(String[] args) {
		System.out.println("Enter data: ");
		String text = in.nextLine();
		byte [] textArray = text.getBytes();
		System.out.println(SHA_256);
		try {
			MessageDigest md = MessageDigest.getInstance(SHA_256);
			MessageDigest md512 = MessageDigest.getInstance(SHA_512);
			md.update(textArray);
			md512.update(textArray);
			StringBuilder sb = new StringBuilder();
			for (byte theByte : md.digest()) {
				sb.append(String.format("%02x", theByte & 0xff));
			}
			System.out.println("Digest (hex) " + SHA_256);
			System.out.println(sb);
			sb = new StringBuilder();
			for (byte theByte : md512.digest()) {
				sb.append(String.format("%02x", theByte & 0xff));
			}
			System.out.println("Digest (hex) " + SHA_512);
			System.out.println(sb);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
