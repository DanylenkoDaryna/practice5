package ua.nure.danylenko.practice5;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Part5 {
    private static final int LINES =10;
    private static final int ELEMENTS =20;
    private static final int NTHREADS =10;
    private static final int REPEATS =20;
    private static final int PAUSE = 1;
    private Thread[] threads;
    private static File file;
    private static RandomAccessFile raf;

    static{
        createFile();
        try {
            raf = new RandomAccessFile(file.getPath(),"rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    Part5(){
        threads = new Thread[NTHREADS];
        for(int i=0; i<NTHREADS; i++){
            threads[i]=new Worker(i);
        }
    }


    private void start() {
        for(Thread th:threads){
            th.start();
        }
    }

    private static class Worker extends Thread {
        private int threadId;
        private int counter;

        Worker(int id){
            threadId=id;
            counter=1;
        }

        @Override
        public void run() {
            Worker w = (Worker)Thread.currentThread();
            while (w.counter<=REPEATS) {
                synchronized (file) {
                    writeNum();
                }
                w.counter=w.counter+1;
            }
        }

        private static void writeNum(){
            Worker w = (Worker)Thread.currentThread();
            try {
                int currentPos = ((w.threadId)*(REPEATS+System.lineSeparator().length()))+(w.counter-1);

                raf.seek(currentPos);
                raf.write('0'+ w.threadId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(PAUSE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Part5 p = new Part5();
        p.start();
        waitThreadsEnd(p.threads);

        System.out.println(readFullFile());
    }

    private static String readFullFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        raf.seek(0);
        sb.append(raf.readLine());
        sb.append(System.lineSeparator());
        int count = 1;
        while(count!=NTHREADS-1) {
            raf.seek(raf.getFilePointer());
            sb.append(raf.readLine());
            sb.append(System.lineSeparator());
            count=count+1;
        }
        sb.append(raf.readLine());
        return sb.toString();
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

    private static void createFile() {
        File f = new File("part5.txt");
        Path path = f.toPath();
        if (f.exists()) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            file = new File("part5.txt");
            try (BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(file.getName()))) {
                    for(int i = 1; i < LINES; i++){
                        for (int j = 0; j < ELEMENTS; j++) {
                            out.write("0".getBytes());
                        }
                        out.write(System.lineSeparator().getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
            }
        }
        file=f;
    }
}
