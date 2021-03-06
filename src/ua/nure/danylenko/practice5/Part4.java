package ua.nure.danylenko.practice5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part4 {
    private int nStrings;
    private int maxValue;
    private static final int PAUSE = 1;
    private Thread[] threads;
    private int[][] originalMatrix;
    private int[] maxStrValues;
    private File file;

    private class Worker extends Thread {
        private int threadId;

        Worker(int id){
            threadId=id;
        }

        @Override
        public void run() {
            int id = ((Worker)Thread.currentThread()).threadId;
            try {
                findMaxByThreads(originalMatrix[id]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void findMaxByThreads( int[]line) throws InterruptedException {
            int max = line[0];
            for(int i=0; i<line.length-1; i++){
                Thread.sleep(PAUSE);
                if(line[i]<line[i+1]&&max<line[i+1]&&maxValue<line[i+1]) {
                    maxValue = line[i + 1];
                }
            }
        }
    }

    Part4(String fileName) throws IOException {
        file=new File(fileName);
        originalMatrix=matrixFromFile();
        threads = new Thread[nStrings];
        for(int i=0; i<nStrings; i++){
            threads[i]=new Part4.Worker(i);
        }
        maxStrValues = new int[nStrings];
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        Part4 p4 = new Part4("part4.txt");
        p4.start();
        long startTime = System.currentTimeMillis();
        waitThreadsEnd(p4.threads);
        System.out.println(p4.maxValue);
        System.out.println(System.currentTimeMillis() - startTime);

        startTime = System.currentTimeMillis();
        p4.findMaxValueNotParallel();
        System.out.println(p4.maxValue);
        System.out.println(System.currentTimeMillis() - startTime);

    }

    private static int findMax(int[]line) throws InterruptedException {
        int max = line[0];
        for(int i=0; i<line.length-1; i++){
            Thread.sleep(PAUSE);
            if(line[i]<line[i+1]&&max<line[i+1]) {
                max = line[i + 1];
            }
        }
        return max;
    }

    private void findMaxValueNotParallel() throws InterruptedException {
        for(int i=0; i<nStrings; i++){
            maxStrValues[i]=findMax(originalMatrix[i]);
        }
        maxValue =findMax(maxStrValues);
    }

    private void start() {
        for(Thread th:threads){
            th.start();
        }
    }

    private static void waitThreadsEnd(Thread[] threads){
        for(Thread t:threads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int[][] matrixFromFile() throws IOException {
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        try(RandomAccessFile raf = new RandomAccessFile(this.file.getPath(), "r")) {
            raf.seek(0);
            while (raf.getFilePointer() != raf.length()) {
                sb.append(raf.readLine()).append(System.lineSeparator());
                counter = counter + 1;
            }
        }

        ArrayList al = new ArrayList();
        this.nStrings=counter;

        Matcher m = Pattern.compile("\\b(\\d+)\\b(\\s|\\r\\n)").matcher(sb);
                while(m.find()){
                    al.add(Integer.parseInt(m.group(1)));
                }
        int[][] matrix = new int[counter][al.size()];
                for(int n=0; n<nStrings; n++){
                    for(int j=0; j<al.size(); j++) {
                        matrix[n][j] = (int)al.get(j);
                    }
                }
        return matrix;
    }
}


