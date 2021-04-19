package ua.nure.danylenko.practice5;


import java.io.IOException;

public class Demo {

    public static void main(String[] args) throws IOException, InterruptedException {
        Part1.main(args);
        Part2.main(args);
        Part3.main(args);
        FileMaker.createFillFile();
        Part4.main(args);
        Part5.main(args);
        Part6.main(args);
    }
}
