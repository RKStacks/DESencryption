
package com.des.encrypit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESAlgorithm {

	static String[]			str	= { "Rizwan", "Afreen", "Fathema", "Khan" };
	private static byte[]	encoded;

	public static void main(String[] args) {
		for (int i = 0; i < str.length; i++)
		{
			System.out.println("Converting : " + str[i]);
			new DESAlgorithm(str[i]);
		}
		//new DESAlgorithm(str[1]);

	}

	private void generateKey() {
		try
		{
			KeyGenerator generator;
			generator = KeyGenerator.getInstance("DES");
			// generator.init(new SecureRandom());
			// key = generator.generateKey();

			if (encoded == null)
			{
				SecretKey key = KeyGenerator.getInstance("DES").generateKey();

				encoded = key.getEncoded();
				System.err.println("New Unique Key : " + encoded + " length : " + encoded.length);
			}
			else
			{
				System.err.println("New Unique Key : " + encoded + " length : " + encoded.length);
			}
		}
		catch (NoSuchAlgorithmException e)
		{
			System.err.println("Error in generating Key :  " + e.getMessage());
		}
		catch (NullPointerException e)
		{
			System.err.println("Error in generating Key :  " + e.getMessage());
		}
		catch (Exception e)
		{
			System.err.println("Error in generating Key :  " + e.getMessage());
		}

	}

	/**
	 * Encrypt The Given Message
	 *
	 * @param message
	 * @return
	 */
	private String encrypt(String message) {
		String base64 = "";
		try
		{

			// Get a cipher object.
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			SecretKey uniqueKey = new SecretKeySpec(encoded, "DES");
			cipher.init(Cipher.ENCRYPT_MODE, uniqueKey);

			// Gets the raw bytes to encrypt, UTF8 is needed for
			// having a standard character set
			byte[] stringBytes = message.getBytes("UTF8");

			// encrypt using the cypher
			byte[] raw = cipher.doFinal(stringBytes);

			// converts to base64 for easier display.
			BASE64Encoder encoder = new BASE64Encoder();
			base64 = encoder.encode(raw);
		}
		catch (IllegalBlockSizeException e)
		{
			System.err.println("Error in encrypting message :  " + e.getMessage());
		}
		catch (BadPaddingException e)
		{
			System.err.println("Error in encrypting message :  " + e.getMessage());
		}
		catch (NoSuchAlgorithmException e)
		{
			System.err.println("Error in encrypting message :  " + e.getMessage());
		}
		catch (NoSuchPaddingException e)
		{
			System.err.println("Error in encrypting message :  " + e.getMessage());
		}
		catch (InvalidKeyException e)
		{
			System.err.println("Error in encrypting message :  " + e.getMessage());
		}
		catch (UnsupportedEncodingException e)
		{
			System.err.println("Error in encrypting message :  " + e.getMessage());
		}

		return base64;
	}

	private String decrypt(String encrypted) {
		String message = "";
		try
		{
			// Get a cipher object.
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			SecretKey uniqueKey = new SecretKeySpec(encoded, "DES");
			cipher.init(Cipher.DECRYPT_MODE, uniqueKey);

			// decode the BASE64 coded message
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] raw = decoder.decodeBuffer(encrypted);

			// decode the message
			byte[] stringBytes = cipher.doFinal(raw);

			// converts the decoded message to a String
			message = new String(stringBytes, "UTF8");
		}
		catch (IllegalBlockSizeException e)
		{
			System.err.println("Error in decrypting message :  " + e.getMessage());
		}
		catch (BadPaddingException e)
		{
			System.err.println("Error in decrypting message :  " + e.getMessage());
		}
		catch (NoSuchAlgorithmException e)
		{
			System.err.println("Error in decrypting message :  " + e.getMessage());
		}
		catch (NoSuchPaddingException e)
		{
			System.err.println("Error in decrypting message :  " + e.getMessage());
		}
		catch (InvalidKeyException e)
		{
			System.err.println("Error in decrypting message :  " + e.getMessage());
		}
		catch (UnsupportedEncodingException e)
		{
			System.err.println("Error in decrypting message :  " + e.getMessage());
		}
		catch (IOException e)
		{
			System.err.println("Error in decrypting message :  " + e.getMessage());
		}
		return message;
	}

	public DESAlgorithm(String message) {
		try
		{
			System.out.println("clear message: " + message);

			System.out.println("Key Generation Started");
			// generateKey();

			// encoded = "[B@1786b2ca".getBytes();
			SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
			KeySpec ks = new DESKeySpec("[B@33b44e59".getBytes());
			SecretKey secretKey = kf.generateSecret(ks);
			encoded = secretKey.getEncoded();

			System.out.println("My Key :" + encoded + " " + encoded.length);

			String encrypted = encrypt(message);
			System.out.println("encrypted message: " + encrypted);

			String decrypted = decrypt("7DYaryuyRh0=".trim());
			System.out.println("decrypted message: " + decrypted);

		}
		catch (Exception e)
		{
			System.err.println("Error in main :  " + e.getMessage());
		}

	}

}
