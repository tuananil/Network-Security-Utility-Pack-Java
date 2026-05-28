package com.tuana.checker;

import java.net.HttpURLConnection;
import java.net.URL;

public class UrlChecker {

    public static void main(String[] args) {
        // Kontrol etmek istediğimiz web sitelerinin listesi
        String[] urls = {
                "https://www.google.com",
                "https://www.github.com",
                "http://example.com",
                "https://bu-site-kesinlikle-yoktur-patlak.com" // Hata durumunu test etmek için uydurma site
        };

        System.out.println("🔍 [Web Monitörü] Siteler analiz ediliyor...\n");

        for (String urlString : urls) {
            checkWebsite(urlString);
            System.out.println("---------------------------------------------");
        }
    }

    public static void checkWebsite(String urlString) {
        try {
            URL url = new URL(urlString);
            // Web sitesine sanal bir kapı açıyoruz (Bağlantı isteği)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Sadece sayfayı görüntülemek istediğimizi söylüyoruz
            connection.setConnectTimeout(3000); // Site 3 saniye içinde cevap vermezse pes et
            connection.connect();

            // Siteden dönen cevap kodu (200 = Başarılı, 404 = Bulunamadı, 500 = Sunucu Hatalı)
            int responseCode = connection.getResponseCode();

            System.out.println("🌐 URL: " + urlString);
            System.out.println("📊 Durum Kodu: " + responseCode);

            // HTTP Durum koduna göre yorum yapıyoruz
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("🟢 Durum: AKTİF (Site sapasağlam çalışıyor)");
            } else {
                System.out.println("🟡 Durum: SORUNLU (Cevap kodu normal değil)");
            }

            // Güvenlik Kontrolü: Protokol HTTPS mi yoksa güvensiz HTTP mi?
            if (urlString.startsWith("https")) {
                System.out.println("🔒 Güvenlik: GÜVENLİ (HTTPS Protokolü aktif)");
            } else {
                System.out.println("⚠️ Güvenlik: GÜVENSİZ! (HTTP Protokolü kullanılıyor, SSL eksik)");
            }

        } catch (Exception e) {
            // Eğer siteye hiç ulaşılamazsa (Domain süresi bitmiş veya IP yanlışsa) buraya düşer
            System.out.println("🌐 URL: " + urlString);
            System.err.println("🔴 Durum: ÇEVRİMDIŞI! (Siteye veya sunucuya ulaşılamıyor)");
        }
    }
}
