package de.hfu.cnc.hasher;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    MessageDigest md;

    public Hasher() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("SHA-256");
    }

    @PostMapping("/")
    public String hash(@RequestBody byte[] data) {
        byte[] encodedHash = md.digest(data);
        return bytesToHex(encodedHash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}

