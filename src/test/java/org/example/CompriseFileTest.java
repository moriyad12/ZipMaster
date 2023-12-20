package org.example;

import org.junit.jupiter.api.Test;



class CompriseFileTest {
    @Test
    public void testC() {
        new CompriseFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\gbbct10.seq", 1);
    }

    @Test
    public void testD() {
        new ExtractFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\20011457.1.gbbct10.seq.hc");
    }

    @Test
    public void test2() {
        new CompriseFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf", 1);
        new ExtractFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\20011457.1.Algorithms - Lectures 7 and 8 (Greedy algorithms).pdf.hc");
    }

    @Test
    public void test3() {
        new CompriseFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\IMG_2741.jpg", 5);
        new ExtractFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\20011457.5.IMG_2741.jpg.hc");
    }

    @Test
    public void test4() {
        new CompriseFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\test1.in.txt", 1);
        ExtractFile extractFile = new ExtractFile("F:\\ZipMaster\\src\\main\\java\\org\\example\\20011457.1.test1.in.txt.hc");
    }

}