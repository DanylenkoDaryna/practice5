package ua.nure.danylenko.practice5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;

class FileMaker {
    private static File file;
    private static final int STRINGS = 4;
    private static final int COLUMNS = 100;
    private static final String CHARSET = "Cp1251";

    FileMaker() {
        throw new IllegalStateException("Utility class");
    }

    public static void createFillFile() throws IOException {
        File f = new File("part4.txt");
        Path path = f.toPath() ;
        if (f.exists()) {
           Files.delete(path);
           file = new File("part4.txt");
        }
        file = f;
        fillFile();
    }

    private static void fillFile() throws IOException {
        try(RandomAccessFile raf = new RandomAccessFile(file.getPath(), "rw")) {
            for (int i = 0; i < STRINGS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    String str = String.valueOf(randomNums()) + " ";
                    raf.write(str.getBytes(CHARSET));

                }
                raf.write(System.lineSeparator().getBytes());
            }
        }
    }

    private static int randomNums() {
        final int rightBound = 1000;
        return new SecureRandom().nextInt(rightBound);
    }

}
