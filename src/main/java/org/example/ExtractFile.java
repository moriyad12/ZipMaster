package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExtractFile {

    private final ArrayList<Byte> bytes;
    private final HashMap<String, String> hashedValues;
    private long noOfBytes;

    public ExtractFile(String fileName) {
        this.bytes = new ArrayList<>();
        this.hashedValues = new HashMap<>();
        extractFile(fileName);
    }

    private void extractFile(String fileName) {
        int index = fileName.lastIndexOf("\\");
        String outputFileName = fileName.substring(0, index) + "\\extracted." + fileName.substring(index + 1);
        outputFileName = outputFileName.replace(".hc", "");
        try (BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(outputFileName, true))) {
            readInput(fileName, fileOutputStream);
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    private void readInput(String inputFileName, BufferedOutputStream outputFileName) {
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFileName))) {
            readNoOfBytes(input);
            readHashMapFromFile(input);
            int byteOfInput;
            String temp = "";
            while ((byteOfInput = input.read()) != -1) {
                temp = decode(byteOfInput, temp, outputFileName);
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    private void readNoOfBytes(BufferedInputStream input) throws IOException {
        StringBuilder temp = new StringBuilder();
        int byteOfInput;
        while (true) {
            byteOfInput = input.read();
            if ((char) byteOfInput == '\r')
                break;
            temp.append((char) byteOfInput);
        }
        noOfBytes = Long.parseLong(temp.toString());
    }

    private void readHashMapFromFile(BufferedInputStream input) throws IOException {
        int byteOfInput;
        StringBuilder temp = new StringBuilder();
        while (true) {
            byteOfInput = input.read();
            if ((char) byteOfInput == '\r')
                break;
            if ((char) byteOfInput != '0' && (char) byteOfInput != '1') {
                String value = temp.toString();
                temp = new StringBuilder();
                for (int i = 0; i < byteOfInput; i++) {
                    temp.append((char) input.read());
                }
                hashedValues.put(value, temp.toString());
                temp = new StringBuilder();
            } else {
                temp.append((char) byteOfInput);
            }
        }
    }

    private String decode(int byteOfInput, String temp, BufferedOutputStream outPutFileName) throws IOException {
        if (byteOfInput > 127)
            byteOfInput -= 256;
        for (int i = 0; i < 8; i++) {
            if ((byteOfInput & (1 << (7 - i))) != 0) {
                temp += "1";
            } else {
                temp += "0";
            }
            if (hashedValues.containsKey(temp)) {
                String value = hashedValues.get(temp);
                for (int j = 0; j < value.length(); j++)
                    printByte(outPutFileName, (byte) value.charAt(j));
                temp = "";
            }
        }
        return temp;
    }

    private void printByte(BufferedOutputStream fileOutputStream, byte aByte) throws IOException {
        if (noOfBytes != 0) {
            fileOutputStream.write(aByte);
            noOfBytes--;
        }
    }

}