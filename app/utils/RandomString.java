package utils;

import java.util.Random;

public class RandomString {
    static final String charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static Random random = new Random();
    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            sb.append(charSet.charAt(random.nextInt(charSet.length())));
        return sb.toString();
    }
}
