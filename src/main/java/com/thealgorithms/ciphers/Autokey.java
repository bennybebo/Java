package com.thealgorithms.ciphers;

/**
 * A Java implementation of Vigenere Cipher.
 *
 * @author bennybebo
 */
public class Autokey {

	public String encrypt(String plaintext, String keyword) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", ""); // Sanitize input
        keyword = keyword.toUpperCase();
        
        StringBuilder extendedKey = new StringBuilder(keyword);
        extendedKey.append(plaintext); // Extend key with plaintext
        
        StringBuilder ciphertext = new StringBuilder();
        
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            
            int encryptedChar = ((plainChar - 'A') + (keyChar - 'A')) % 26 + 'A';
            ciphertext.append((char) encryptedChar);
        }
        
        return ciphertext.toString();
    }

	// Decrypts the ciphertext using the Autokey cipher
    public String decrypt(String ciphertext, String keyword) {
        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", ""); // Sanitize input
        keyword = keyword.toUpperCase();
        
        StringBuilder plaintext = new StringBuilder();
        StringBuilder extendedKey = new StringBuilder(keyword);
        
        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            
            int decryptedChar = ((cipherChar - 'A') - (keyChar - 'A') + 26) % 26 + 'A';
            plaintext.append((char) decryptedChar);
            
            extendedKey.append((char) decryptedChar); // Extend key with each decrypted char
        }
        
        return plaintext.toString();
    }
}
