package org.example;

import org.junit.jupiter.api.Test;



class CompriseFileTest {
    @Test
    public void testC() {
        new CompriseFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\gbbct10.seq", 5);
    }

    @Test
    public void testD() {
        new ExtractFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\gbbct10.seq.hc");
        new HashingFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\gbbct10.seq", "F:\\ZipMaster\\src\\main\\java\\org\\example\\extracted.gbbct10.seq");
    }

    @Test
    public void test2() {
        new CompriseFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf", 1);
        new ExtractFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf.hc");
        new HashingFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf", "F:\\ZipMaster\\src\\main\\java\\org\\example\\extracted.Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf");
    }

    @Test
    public void test3() {
        new CompriseFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\IMG_2741.jpg", 5);
        new ExtractFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\IMG_2741.jpg.hc");
        new HashingFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\IMG_2741.jpg", "F:\\ZipMaster\\src\\main\\java\\org\\example\\extracted.IMG_2741.jpg");
    }

    @Test
    public void test4() {
        new CompriseFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\test1.in.txt", 1);
        ExtractFile extractFile = new ExtractFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\test1.in.txt.hc");
        new HashingFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\test1.in.txt", "F:\\ZipMaster\\src\\main\\java\\org\\example\\extracted.test1.in.txt");
    }

}