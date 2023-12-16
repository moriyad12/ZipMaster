package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ExtractFile {

    private final ArrayList<Byte> bytes;
    private final HashMap<String, String> hashedValues;

    public ExtractFile() {
        this.bytes = new ArrayList<>();
        this.hashedValues = new HashMap<>();
    }

    private void readHashMapFromFile( BufferedInputStream input) throws IOException {
        int byteOfInput;
        int noOfBytes = input.read();
        String temp = "";
        while (true) {
            byteOfInput = input.read();
            if ((char) byteOfInput == '\r')
                break;
            if ((char) byteOfInput == ' ') {
                String value = temp;
                temp = "";
                for (int i = 0; i < noOfBytes; i++) {
                    temp += (char) input.read();
                }
                hashedValues.put(value, temp);
                temp = "";
            } else {
                temp += (char) byteOfInput;
            }
        }
    }

    private void printExtractedFile(String fileName) {
        int n = bytes.size();
        try (BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(fileName, true))) {
            for (int i = 0; i < n; i++) {
                fileOutputStream.write(bytes.get(i));
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    private String decode(int byteOfInput, String temp, String outPutFileName) {
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
                for( int j=0;j<value.length();j++)
                   bytes.add((byte)value.charAt(j));
                temp = "";
                if (bytes.size() >= 100000) {
                    printExtractedFile(outPutFileName);
                    bytes.clear();
                }
            }
        }
        return temp;
    }

    public void takeInputFromComprisedFile(String fileName) {
        int index = fileName.lastIndexOf("\\");
        String outputFileName= fileName.substring(0, index)+"\\extracted."+fileName.substring(index+1);
        outputFileName=outputFileName.replace(".hc", "");
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName))) {
            readHashMapFromFile(input);
            int byteOfInput;
            String temp = "";
            while ((byteOfInput = input.read()) != -1) {
                temp=decode(byteOfInput, temp, outputFileName);
            }
            printExtractedFile(outputFileName);
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }
}