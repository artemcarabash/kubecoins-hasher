package de.hfu.cnc.hasher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class Hasher {

    MessageDigest md;
    private static final Logger LOG = LoggerFactory.getLogger(HasherApplication.class);

    public Hasher() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("SHA-256");
    }

    @PostMapping("/")
    public String hash(@RequestBody byte[] data) {
        byte[] encodedHash = md.digest(data);
        LOG.info("Hasher ---- Received Request");
        return bytesToHex(encodedHash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        LOG.info("Source - " + new String(hash) + " Hash - " + hexString.toString());
        return hexString.toString();
    }

}

