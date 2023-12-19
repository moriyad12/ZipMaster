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
    private final int maxSize = 50000000;


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
        byte[] bytes = new byte[maxSize + maxSize % noOfBytes];
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName))) {
            int byteOfInput;
            while ((byteOfInput = input.read(bytes)) != -1) {
                calculateFrequency(byteOfInput, bytes);
            }
            bytes = null;
        } catch (Exception e) {
            System.out.println("File not found");
        }
        System.out.println("File read successfully");
    }

    private void calculateFrequency(int size, byte[] bytes) {
        String temp = "";
        for (int i = 0; i < size; i += noOfBytes) {
            for (int j = i; j < i + noOfBytes && j < size; j++) {
                temp += (char) bytes[j];
                noOfBytesRead++;
            }
            frequencies.put(temp, frequencies.getOrDefault(temp, 0) + 1);
            temp = "";
        }
    }

    private void putInPriorityQueue() {
        System.out.println("Putting in priority queue");
        for (String key : frequencies.keySet()) {
            priorityQueue.add(new Pair(frequencies.get(key), key));
        }
    }

    private void huffmanCoding() {
        System.out.println("Huffman coding started");
        if (priorityQueue.size() == 1) {
            root = priorityQueue.poll();
            hashedValues.put(root.getSecond(), "0");
            return;
        }
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
            if (node != root)
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
                String value = entry.getValue();
                String key = entry.getKey();
                for (int i = 0; i < value.length(); i++) {
                    fileOutputStream.write(value.charAt(i));
                }
                fileOutputStream.write((char) key.length());
                for (int i = 0; i < key.length(); i++) {
                    fileOutputStream.write(key.charAt(i));
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
        byte[] bytes = new byte[maxSize + maxSize % noOfBytes];
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(inputFileName))) {
            int byteOfInput;
            while ((byteOfInput = input.read(bytes)) != -1) {
                writeByte(fileOutputStream, byteOfInput, bytes);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeByte(BufferedOutputStream fileOutputStream, int size, byte[] bytes) throws IOException {
        String temp = "";
        for (int i = 0; i < size; i += noOfBytes) {
            for (int j = i; j < i + noOfBytes && j < size; j++) {
                temp += (char) bytes[j];
            }
            String hashedValue = hashedValues.get(temp);
            temp = "";
            for (int j = 0; j < hashedValue.length(); j++) {
                if (hashedValue.charAt(j) == '1') {
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
}