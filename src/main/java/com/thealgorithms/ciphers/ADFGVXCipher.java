package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * It is a fractionating transposition cipher that combines a 
 * Polybius square substitution with a columnar transposition. 
 * It's named after the six letters (A, D, F, G, V, X) that it 
 * uses in its substitution process.
 * https://en.wikipedia.org/wiki/ADFGVX_cipher
 * 
 * @author bennybebo
 */
public class ADFGVXCipher {

    private static final char[] POLYBIUS_LETTERS = {'A', 'D', 'F', 'G', 'V', 'X'};
    private static final char[][] POLYBIUS_SQUARE = {
            {'P', 'H', '0', 'Q', 'G', '6'},
            {'4', 'M', 'E', 'A', '1', 'Y'},
            {'L', '2', 'N', 'O', 'F', 'D'},
            {'X', 'K', 'R', '3', 'C', 'V'},
            {'S', '5', 'Z', 'W', '7', 'B'},
            {'J', '9', 'U', 'T', 'I', '8'}
    };
    private static final Map<Character, String> POLYBIUS_MAP = new HashMap<>();

    static {
        for (int i = 0; i < POLYBIUS_SQUARE.length; i++) {
            for (int j = 0; j < POLYBIUS_SQUARE[i].length; j++) {
                POLYBIUS_MAP.put(POLYBIUS_SQUARE[i][j], "" + POLYBIUS_LETTERS[i] + POLYBIUS_LETTERS[j]);
            }
        }
    }

    public String encrypt(String plaintext, String key) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
        StringBuilder fractionatedText = new StringBuilder();

        // Step 1: Polybius square substitution
        for (char c : plaintext.toCharArray()) {
            fractionatedText.append(POLYBIUS_MAP.get(c));
        }

        // Step 2: Columnar transposition
        return columnarTransposition(fractionatedText.toString(), key);
    }

    private String columnarTransposition(String text, String key) {
        int numRows = (int) Math.ceil((double) text.length() / key.length());
        char[][] table = new char[numRows][key.length()];
        for (char[] row : table) {
            Arrays.fill(row, '_'); // Fill with underscores to handle empty cells
        }

        // Fill the table row by row
        for (int i = 0; i < text.length(); i++) {
            table[i / key.length()][i % key.length()] = text.charAt(i);
        }

        // Read columns based on the alphabetical order of the key
        StringBuilder ciphertext = new StringBuilder();
        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        for (char keyChar : sortedKey) {
            int column = key.indexOf(keyChar);
            for (char[] row : table) {
                if (row[column] != '_') {
                    ciphertext.append(row[column]);
                }
            }
        }

        return ciphertext.toString();
    }
}
