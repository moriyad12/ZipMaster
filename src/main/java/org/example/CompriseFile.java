package org.example;

import java.io.*;
import java.util.*;

public class CompriseFile {
    private final StringBuilder bytes;
    private final HashMap<String, Integer> frequencies;
    private final HashMap<String, String> hashedValues;
    private final PriorityQueue<Pair> priorityQueue;
    private final HashMap<Pair, Vector<Pair>> tree;
    private  int noOfBytes;
    private Pair root;


    public CompriseFile() {
        this.bytes = new StringBuilder();
        this.frequencies = new HashMap<>();
        this.hashedValues = new HashMap<>();
        this.priorityQueue = new PriorityQueue<>();
        this.tree = new HashMap<>();
    }

    private void calculateFrequencies() {
        System.out.println("Calculating frequencies");
        int n=bytes.length();
        for (int i = 0; i < n; i+=noOfBytes) {
            int endIndex = Math.min(i + noOfBytes, n);
            String temp=  bytes.substring(i, endIndex);
            frequencies.put(temp, frequencies.getOrDefault(temp, 0) + 1);
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
    private void writingBytes(BufferedOutputStream fileOutputStream) throws IOException {
        byte byteOfOutput = 0;
        int size = 0;
        int n=bytes.length();
        for (int i = 0; i < n; i+=noOfBytes) {
            int endIndex = Math.min(i + noOfBytes, n);
            String unitOfHashing=  bytes.substring(i, endIndex);
            String temp = hashedValues.get(unitOfHashing);
            for (Character aBit : temp.toCharArray()) {
                if (aBit == '1') {
                    byteOfOutput |= (byte) (1 << (7 - size));
                }
                size++;
                if (size == 8) {
                    fileOutputStream.write(byteOfOutput);
                    size = 0;
                    byteOfOutput = 0;
                }
            }
        }
    }
    private void printComprisedFile(String fileName) {
        try (BufferedOutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(fileName, true))) {
            System.out.println("Converting to binary");
            writingBytes(fileOutputStream);
            System.out.println("File compressed successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void comprise( String fileName) {
        calculateFrequencies();
        putInPriorityQueue();
        huffmanCoding();
        dfs(root, "");
        printComprisedFile(fileName+".hc");
    }

    public void takeInputFromExtractedFile(String fileName,int noOfBytes) {
        this.noOfBytes=noOfBytes;
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName))) {
            int byteOfInput;
            while ((byteOfInput = input.read()) != -1) {
                bytes.append((char) byteOfInput);
            }
        } catch (Exception e) {
            System.out.println("File not found");
        }
        System.out.println("File read successfully");
        comprise(fileName);
    }

    public HashMap<String,String> getHashedValues() {
        return hashedValues;
    }
}
