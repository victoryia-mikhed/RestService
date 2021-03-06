package by.bsu.library.util;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption {

    private static Logger LOG = Logger.getLogger(PasswordEncryption.class);

    public static String md5(String input) {
        String md5 = null;
        if (input == null) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("MD5 Exception: no such algorithm exception" + e);
        }
        return md5;
    }
}