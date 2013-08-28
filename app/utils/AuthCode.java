package utils;


import java.util.Random;

public class AuthCode {
    private String authCode;

    public AuthCode() {
        authCode = RandomString.generate(64);
    }

    @Override
    public String toString() {
        return authCode;
    }
}
