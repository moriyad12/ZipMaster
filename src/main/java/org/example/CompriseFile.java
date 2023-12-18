package org.example;

import java.io.*;
import java.util.*;

public class CompriseFile {
    private final HashMap<String, Integer> frequencies;
    private final HashMap<String, String> hashedValues;
    private final PriorityQueue<Pair> priorityQueue;
    private final HashMap<Pair, Vector<Pair>> tree;
    private int noOfBytes;
    private Pair root;
    private byte byteOfOutput;
    private int sizeOfByte;
    private long noOfBytesRead;


    public CompriseFile(String fileName, int noOfBytes) {
        this.frequencies = new HashMap<>();
        this.hashedValues = new HashMap<>();
        this.priorityQueue = new PriorityQueue<>();
        this.tree = new HashMap<>();
        this.noOfBytesRead = 0;
        this.byteOfOutput = 0;
        this.sizeOfByte = 0;
        comprise(fileName, noOfBytes);
    }

    private void comprise(String fileName, int noOfBytes) {
        takeInputFromExtractedFile(fileName, noOfBytes);
        putInPriorityQueue();
        huffmanCoding();
        dfs(root, "");
        writeHashMapToFile(fileName + ".hc");
        writeDataToFile(fileName + ".hc", fileName);
    }

    private void takeInputFromExtractedFile(String fileName, int noOfBytes) {
        this.noOfBytes = noOfBytes;
        String bytes = "";
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName))) {
            int byteOfInput;
            while ((byteOfInput = input.read()) != -1) {
                bytes += (char) byteOfInput;
                noOfBytesRead++;
                if (bytes.length() == noOfBytes) {
                    frequencies.put(bytes, frequencies.getOrDefault(bytes, 0) + 1);
                    bytes = "";
                }
            }
            if (!bytes.isEmpty()) {
                frequencies.put(bytes, frequencies.getOrDefault(bytes, 0) + 1);
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
        System.out.println("File read successfully");
    }

    private void putInPriorityQueue() {
        System.out.println("Putting in priority queue");
        for (String key : frequencies.keySet()) {
            priorityQueue.add(new Pair(frequencies.get(key), key));
        }
    }

    private void huffmanCoding() {
        System.out.println("Huffman coding started");
        while (priorityQueue.size() > 1) {
            Pair pair1 = priorityQueue.poll();
            Pair pair2 = priorityQueue.poll();
            Pair newPair = new Pair(pair1.getFirst() + pair2.getFirst(), "");
            priorityQueue.add(newPair);
            Vector<Pair> newNode = new Vector<>();
            newNode.add(pair1);
            newNode.add(pair2);
            tree.put(newPair, newNode);
            root = newPair;
        }
    }

    private void dfs(Pair node, String code) {
        Vector<Pair> children = tree.get(node);
        if (children == null) {
            hashedValues.put(node.getSecond(), code);
            return;
        }
        dfs(children.get(0), code + "0");
        dfs(children.get(1), code + "1");
    }

    public void writeHashMapToFile(String fileName) {
        try (BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(fileName, true))) {
            fileOutputStream.write(String.valueOf(noOfBytesRead).getBytes());
            fileOutputStream.write('\r');
            for (Map.Entry<String, String> entry : hashedValues.entrySet()) {
                for (char aByte : entry.getValue().toCharArray()) {
                    fileOutputStream.write(aByte);
                }
                fileOutputStream.write((char) entry.getKey().length());
                for (char aByte : entry.getKey().toCharArray()) {
                    fileOutputStream.write(aByte);
                }
            }
            fileOutputStream.write('\r');
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeDataToFile(String outputFileName, String inputFileName) {
        try (BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(outputFileName, true))) {
            System.out.println("Converting to binary");
            readByte(fileOutputStream, inputFileName);
            if (sizeOfByte != 0) {
                fileOutputStream.write(byteOfOutput);
            }
            System.out.println("File compressed successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readByte(BufferedOutputStream fileOutputStream, String inputFileName) throws IOException {
        String bytes = "";
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFileName))) {
            int byteOfInput;
            while ((byteOfInput = input.read()) != -1) {
                bytes += (char) byteOfInput;
                if (bytes.length() == noOfBytes) {
                    writeByte(fileOutputStream, bytes);
                    bytes = "";
                }
            }
            if (!bytes.isEmpty()) {
                writeByte(fileOutputStream, bytes);
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    private void writeByte(BufferedOutputStream fileOutputStream, String bytes) throws IOException {
        String temp = hashedValues.get(bytes);
        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == '1') {
                byteOfOutput |= (byte) (1 << (7 - sizeOfByte));
            }
            sizeOfByte++;
            if (sizeOfByte == 8) {
                fileOutputStream.write(byteOfOutput);
                sizeOfByte = 0;
                byteOfOutput = 0;
            }
        }
    }
}
