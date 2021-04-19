package ua.nure.danylenko.practice5;


import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Spam {
    private Thread[] threads;

    public Spam(String[] container, int[] mills) {
        threads = new Thread[container.length];
      for(int i=0; i<container.length; i++){
          threads[i]=new Worker(container[i], mills[i]);
      }

    }
    public void start() {

        for(Thread th:threads){
            th.start();
        }
    }
    public void stop() {
        for(Thread th:threads){
            th.interrupt();
        }
    }
    private static class Worker extends Thread {
        private String str;
        private int mills;

        Worker(String line, int ms){
        str=line;
        mills=ms;
        }
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                System.out.println(str);
                try {
                    Thread.sleep(mills);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String[] messages = new String[] { "@@@", "bbbbbbb" };
        int[] times = new int[] { 333, 222 };
        Spam sp=new Spam(messages,times);
        sp.start();
        BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
        final String s = in.readLine();
        sp.stop();
        if(s!=null){
            in.close();
        }

    }
}
