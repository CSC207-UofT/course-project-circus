package utils;

import java.security.SecureRandom;

/**
 * Random number generation and assorted helper functionality.
 */
public class RandomUtils {
    private static final String ALPHA_NUMERIC_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom random = new SecureRandom();

    /**
     * Generate a random id.
     * @param n The number of characters in the id.
     */
    public static String randomId(int n) {
        StringBuilder sb = new StringBuilder(n);
        for(int i = 0; i < n; i++) {
            int k = random.nextInt(ALPHA_NUMERIC_CHARACTERS.length());
            char c = ALPHA_NUMERIC_CHARACTERS.charAt(k);
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Generate a random 8-character id.
     */
    public static String randomId() {
        return randomId(8);
    }
}
