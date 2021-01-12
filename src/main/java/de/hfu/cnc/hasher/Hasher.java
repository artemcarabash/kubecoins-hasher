package de.hfu.cnc.hasher;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class Hasher {
    // Eigene Prometheus Metrik  die mit z.B. Graphana abgerufen werden kann
    Counter requestCounter;
    MessageDigest md;
    private static final Logger LOG = LoggerFactory.getLogger(HasherApplication.class);

    // Konstruktor
    public Hasher(MeterRegistry registry) throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("SHA-256");
        // Initialisierung von Metrik
        this.requestCounter = registry.counter("hasher_requests");
    }

    // Eine HTTP Post Methode die bekommt random Bytes von Randomizer Service, erstellt ein Hashwert und gibt es mit dem Response zurück
    @PostMapping("/")
    public String hash(@RequestBody byte[] data) {
        byte[] encodedHash = md.digest(data);
        LOG.info("Hasher ---- Received Request");
        // Methode die wird mit jeder Anfrage unsere Metrik hochzählen
        requestCounter.increment();
        return bytesToHex(encodedHash);
    }

    // Methode um eine Hex Wert zu generieren
    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        LOG.info("Source - " + new String(hash) + " Hash - " + hexString.toString());
        return hexString.toString();
    }

}

