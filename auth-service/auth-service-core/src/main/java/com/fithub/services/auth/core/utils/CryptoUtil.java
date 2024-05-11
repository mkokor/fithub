package com.fithub.services.auth.core.utils;

import org.mindrot.jbcrypt.BCrypt;

public class CryptoUtil {

    public static String hash(String plaintext) {
        final String salt = BCrypt.gensalt();

        return BCrypt.hashpw(plaintext, salt);
    }

    public static Boolean compare(String plaintext, String hash) {
        return BCrypt.checkpw(plaintext, hash);
    }

}