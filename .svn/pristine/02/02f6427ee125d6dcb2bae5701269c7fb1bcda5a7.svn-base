package ua.nure.danylenko.practice5;

import java.io.IOException;
import java.io.InputStream;

public class Part2 {

    public static void main(String[] args) throws InterruptedException {

        InputStream temp = System.in;
        System.setIn(new EnterStream());
        Thread t = new Thread(() -> {
            try {
                Spam.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.start();
        t.join();
        System.setIn(temp);
    }
}


