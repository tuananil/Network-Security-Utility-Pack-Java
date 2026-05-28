# 🛠️ Network & Security Utility Pack (Java)

This repository contains **3 independent Java utility tools** focused on network analysis, data integrity, and cybersecurity. The projects are developed using modern Java standards and performance-oriented architectures.

## 🚀 Content and Technical Details

### 1. Multi-Threaded Port Scanner
* **Technologies Used:** `Socket`, `ExecutorService` (Thread Pool), `Runnable`, `TimeUnit`.
* **Description:** Scans the first 1024 critical ports on a specified target IP within seconds using a multi-threading architecture, effectively detecting open entry points on the network.

### 2. Secure File Vault (AES & SHA-256)
* **Technologies Used:** `javax.crypto.Cipher`, `MessageDigest`, `Java NIO (Files)`.
* **Description:** Encrypts sensitive data using military-grade AES encryption and saves it securely to the disk. Upon reading the file back, it performs a SHA-256 cryptographic hash check to verify data integrity and ensure it hasn't been tampered with.

### 3. Web URL Status & Protocol Checker
* **Technologies Used:** `HttpURLConnection`, `URL`.
* **Description:** Sends asynchronous HTTP requests to a predefined list of URLs to audit server availability (Status Codes) and analyzes SSL (HTTPS) protocol compliance from a cybersecurity perspective.
