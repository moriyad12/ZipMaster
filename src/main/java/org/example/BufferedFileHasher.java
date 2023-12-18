package org.example;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BufferedFileHasher {

    public static String calculateHash(String filePath) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
             DigestInputStream dis = new DigestInputStream(bis, digest)) {
            // Use a buffer to read the file in chunks
            byte[] buffer = new byte[8192];
            while (dis.read(buffer) != -1) {
                // Reading the file updates the digest
            }
        }

        // Get the hash value
        byte[] hashBytes = digest.digest();
        StringBuilder hashStringBuilder = new StringBuilder();
        for (byte b : hashBytes) {
            hashStringBuilder.append(String.format("%02x", b));
        }
        return hashStringBuilder.toString();
    }

    public static void main(String[] args) {
        try {
            String file1Path = "F:\\ZipMaster\\src\\main\\java\\org\\example\\IMG_2741.jpg";
            String file2Path = "F:\\ZipMaster\\src\\main\\java\\org\\example\\extracted.IMG_2741.jpg";

            String hash1 = calculateHash(file1Path);
            String hash2 = calculateHash(file2Path);

            System.out.println("Hash of file1: " + hash1);
            System.out.println("Hash of file2: " + hash2);

            if (hash1.equals(hash2)) {
                System.out.println("The files are equivalent.");
            } else {
                System.out.println("The files are not equivalent.");
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }
}