package com.tuana.scanner;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PortScanner {
    private static final String TARGET_IP = "127.0.0.1";

    private static final int THREAD_COUNT = 100;

    private static final int TIMEOUT = 200;

    public static void main(String[] args) {
        System.out.println("🚀 [Port Tarayıcı] Başlatılıyor... Hedef IP: " + TARGET_IP);
        long startTime = System.currentTimeMillis();


        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int port = 1; port <= 1024; port++) {
            final int currentPort = port;


            executor.execute(() -> {
                if (isPortOpen(TARGET_IP, currentPort, TIMEOUT)) {
                    System.out.println("✅ PORT AÇIK: " + currentPort);
                }
            });
        }


        executor.shutdown();
        try {

            if (executor.awaitTermination(10, TimeUnit.MINUTES)) {
                long endTime = System.currentTimeMillis();
                System.out.println("⏱️ Tarama tamamlandı! Toplam süre: " + (endTime - startTime) + " ms");
            }
        } catch (InterruptedException e) {
            System.err.println("Tarama yarıda kesildi!");
        }
    }

    // Bir portun açık olup olmadığını kontrol eden asıl fonksiyon
    public static boolean isPortOpen(String ip, int port, int timeout) {
        // Socket, bilgisayarlar arası bağlantıyı sağlayan sanal bir kablo gibidir
        try (Socket socket = new Socket()) {
            // O IP ve Porta bağlanmayı dene
            socket.connect(new InetSocketAddress(ip, port), timeout);
            return true; // Bağlantı kurulduysa port AÇIKTIR
        } catch (Exception e) {
            return false; // Hata verdi it it, bağlantı kurulamadıysa port KAPALIDIR
        }
    }
}