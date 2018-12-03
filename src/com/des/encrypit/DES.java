
package com.des.encrypit;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class DES {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
			NoSuchPaddingException {
		// Generate encryption keys with a KeyGenerator object
		KeyGenerator desGen = KeyGenerator.getInstance("DES"); // DES algorithm
		SecretKey desKey = desGen.generateKey(); // Generate a key
		KeyGenerator desEdeGen = KeyGenerator.getInstance("DESede"); // Triple
		// DES
		SecretKey desEdeKey = desEdeGen.generateKey(); // Generate a key

		// SecretKey is an opaque representation of a key. Use SecretKeyFactory
		// to
		// convert to a transparent representation that can be manipulated:
		// saved
		// to a file, securely transmitted to a receiving party, etc.
		SecretKeyFactory desFactory = SecretKeyFactory.getInstance("DES");
		DESKeySpec desSpec = (DESKeySpec) desFactory.getKeySpec(desKey, javax.crypto.spec.DESKeySpec.class);
		byte[] rawDesKey = desSpec.getKey();
		// Do the same for a DESede key
		SecretKeyFactory desEdeFactory = SecretKeyFactory.getInstance("DESede");
		DESedeKeySpec desEdeSpec = (DESedeKeySpec) desEdeFactory.getKeySpec(desEdeKey, javax.crypto.spec.DESedeKeySpec.class);
		byte[] rawDesEdeKey = desEdeSpec.getKey();

		// Convert the raw bytes of a key back to a SecretKey object
		DESedeKeySpec keyspec = new DESedeKeySpec(rawDesEdeKey);
		SecretKey k = desEdeFactory.generateSecret(keyspec);

		// For DES and DESede keys, there is an even easier way to create keys
		// SecretKeySpec implements SecretKey, so use it to represent these keys
		byte[] desKeyData = new byte[8]; // Read 8 bytes of data from a file
		byte[] tripleDesKeyData = new byte[24]; // Read 24 bytes of data from a
		// file
		SecretKey myDesKey = new SecretKeySpec(desKeyData, "DES");
		SecretKey myTripleDesKey = new SecretKeySpec(tripleDesKeyData, "DESede");

		SecretKey key = null; // Obtain a SecretKey as shown earlier
		byte[] plaintext = null; // The data to encrypt; initialized elsewhere

		// Obtain an object to perform encryption or decryption
		Cipher cipher = Cipher.getInstance("DESede"); // Triple-DES encryption
		// Initialize the cipher object for encryption
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// Now encrypt data
		byte[] ciphertext = cipher.doFinal(plaintext);

		// If we had multiple chunks of data to encrypt, we can do this
		cipher.update(ciphertext);
		//		cipher.update(message2);
		byte[] ciphertext1 = cipher.doFinal();

		// We simply reverse things to decrypt
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedMessage = cipher.doFinal(ciphertext1);

		// To decrypt multiple chunks of data
		byte[] decrypted1 = cipher.update(ciphertext);
		byte[] decrypted2 = cipher.update(ciphertext1);
		//		byte[] decrypted3 = cipher.doFinal(ciphertext3);
	}

}
