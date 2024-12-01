package model.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Classe per gestionar el procés de xifrar i desxifrar dades amb l'algoritme AES.
 * Aquesta classe permet generar claus AES, xifrar dades i desxifrar dades.
 */
public class EncryptionCipher {

    /**
     * Constructor per defecte.
     */
    public EncryptionCipher() {}

    /**
     * Genera una clau simètrica per a l'algoritme AES.
     *
     * @return La clau AES generada.
     * @throws Exception Si hi ha un error durant la generació de la clau.
     */
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // Mida de la clau AES en bits (128).
        return keyGen.generateKey();
    }

    /**
     * Xifra una cadena de text utilitzant una clau AES proporcionada.
     *
     * @param data El text que es vol xifrar.
     * @param secretKey La clau AES utilitzada per al procés de xifrar.
     * @return La cadena xifrada.
     * @throws Exception Si hi ha un error durant el procés de xifrar.
     */
    public static String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes); // Convertir a Base64 per a transport.
    }

    /**
     * Desxifra una cadena de text xifrada.
     *
     * @param encryptedData La cadena xifrada.
     * @param secretKey La clau utilitzada per al procés de desxifrat.
     * @return La cadena desxifrada.
     * @throws Exception Si hi ha un error durant el procés de desxifrat.
     */
    public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData); // Decodificar de Base64.
        return new String(cipher.doFinal(decodedBytes), StandardCharsets.UTF_8);
    }
}