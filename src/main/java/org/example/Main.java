package org.example;

public class Main {
    public static void main(String[] args) {
        /*
        “I acknowledge that I am aware of the academic integrity guidelines of this course,
         and that I worked on this assignment independently without any unauthorized help”.
         */
        if (args.length != 2 && args.length != 3) {
            System.out.println("Invalid number of arguments!");
            return;
        }
        if (args.length == 2) {
            if (args[0].equals("d")) {
                new ExtractFile(args[1]);
            } else {
                System.out.println("First argument should be d!");
            }
        } else {
            if (args[0].equals("c")) {
                new CompriseFile(args[1], Integer.parseInt(args[2]));
            } else {
                System.out.println("First argument should be c!");
            }
        }

    }
}
