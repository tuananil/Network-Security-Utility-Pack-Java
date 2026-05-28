package com.tuana.vault;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Base64;

public class SecureVault {

    // Şifreyi doğrudan string vermek yerine, Java 26'nın istediği güvenli formata (byte dizisine) çeviriyoruz
    private static final byte[] KEY_BYTES = "TuanaSiberGuvenl".getBytes(); // Tam 16 karakter olmalı
    private static final String FILE_PATH = "gizli_not.txt";

    public static void main(String[] args) {
        try {
            String orijinalMetin = "Bu cok gizli bir siber guvenlik verisidir. Kimse okumamali!";
            System.out.println("1️⃣ Orijinal Metin: " + orijinalMetin);

            // 1. Adım: Orijinal metnin SHA-256 parmak izini (Hash) alalım
            String ilkHash = calculateSHA256(orijinalMetin);
            System.out.println("🔑 Orijinal Dosya SHA-256 Hash: " + ilkHash);

            // 2. Adım: Metni AES ile şifreleyelim
            String sifreliMetin = encrypt(orijinalMetin, KEY_BYTES);
            System.out.println("🔒 Sifrelenmis Metin (Dosyaya Yazilacak): " + sifreliMetin);

            // Şifreli hali dosyaya kaydediyoruz
            Files.write(Paths.get(FILE_PATH), sifreliMetin.getBytes());
            System.out.println("💾 Sifreli dosya diske kaydedildi: " + FILE_PATH);

            System.out.println("\n--- [DOSYA OKUMA VE COZME ASAMASI] ---\n");

            // 3. Adım: Dosyayı diskten geri okuyalım
            String dosyadanOkunan = new String(Files.readAllBytes(Paths.get(FILE_PATH)));

            // 4. Adım: Şifreyi çözelim
            String cozulenMetin = decrypt(dosyadanOkunan, KEY_BYTES);
            System.out.println("🔓 Sifresi Cozulen Metin: " + cozulenMetin);

            // 5. Adım: Bütünlük Kontrolü
            String yeniHash = calculateSHA256(cozulenMetin);
            if (ilkHash.equals(yeniHash)) {
                System.out.println("✅ BASARILI: Dosya butunlugu korundu. Veri degistirilmemis!");
            } else {
                System.err.println("❌ TEHLIKE: Dosya butunlugu bozulmus! Veri kurcalanmis!");
            }

        } catch (Exception e) {
            System.out.println("Hata Oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Güncellenmiş AES Şifreleme Fonksiyonu
    public static String encrypt(String veri, byte[] anahtarBytes) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(anahtarBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // Java'nın en net anladığı alt modu ekledik
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] sifreliByte = cipher.doFinal(veri.getBytes());
        return Base64.getEncoder().encodeToString(sifreliByte);
    }

    // Güncellenmiş AES Şifre Çözme Fonksiyonu
    public static String decrypt(String sifreliVeri, byte[] anahtarBytes) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(anahtarBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] hamSifre = Base64.getDecoder().decode(sifreliVeri);
        byte[] cozulenByte = cipher.doFinal(hamSifre);
        return new String(cozulenByte);
    }

    // SHA-256 Hash Hesaplama Fonksiyonu
    public static String calculateSHA256(String veri) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(veri.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}