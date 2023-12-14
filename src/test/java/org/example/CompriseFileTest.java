package org.example;

import org.junit.jupiter.api.Test;



class CompriseFileTest {
    @Test
    public void test(){
        CompriseFile compriseFile = new CompriseFile();
        compriseFile.takeInputFromExtractedFile("F:\\algorithm\\Huffman_Algorithm\\src\\main\\java\\org\\example\\gbbct10.seq",1);
        ExtractFile extractFile = new ExtractFile();
        extractFile.setHashMap(compriseFile.getHashedValues());
        extractFile.takeInputFromComprisedFile("F:\\algorithm\\Huffman_Algorithm\\src\\main\\java\\org\\example\\gbbct10.seq.hc");
    }
    @Test
    public void test2(){
        CompriseFile compriseFile = new CompriseFile();
        compriseFile.takeInputFromExtractedFile("F:\\algorithm\\Huffman_Algorithm\\src\\main\\java\\org\\example\\8. Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf",1);
        ExtractFile extractFile = new ExtractFile();
        extractFile.setHashMap(compriseFile.getHashedValues());
        extractFile.takeInputFromComprisedFile("F:\\algorithm\\Huffman_Algorithm\\src\\main\\java\\org\\example\\8. Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf.hc");
    }
    @Test
    public void test3(){
        CompriseFile compriseFile = new CompriseFile();
        compriseFile.takeInputFromExtractedFile("F:\\algorithm\\Huffman_Algorithm\\src\\main\\java\\org\\example\\IMG_2741.jpg",5);
        ExtractFile extractFile = new ExtractFile();
        extractFile.setHashMap(compriseFile.getHashedValues());
        extractFile.takeInputFromComprisedFile("F:\\algorithm\\Huffman_Algorithm\\src\\main\\java\\org\\example\\IMG_2741.jpg.hc");
    }

}