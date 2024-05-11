package com.fithub.services.auth.core.utils;

import java.util.Random;

public class TokenUtil {

    public static String generateConfirmationCode() {
        Random randomNumberGenerator = new Random();

        Integer confirmationCode = 100000 + randomNumberGenerator.nextInt(900000);
        return String.valueOf(confirmationCode);
    }

}