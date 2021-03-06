package ua.nure.danylenko.practice5;

public class Part3 {
    private int counter;
    private int counter2;
    private Thread[] threads;
    private int nThreads;
    private int repeats;
    private int mills;

    private class Worker extends Thread {
        @Override
        public void run() {
            nonSynchroCount();
        }

        private void nonSynchroCount() {

            int repeater=0;
            while (repeater!=repeats) {
                Part3.this.simpleCount();
                repeater = repeater + 1;
            }
            Thread.currentThread().interrupt();
        }
    }

    private class Worker2 extends Thread {
        @Override
        public void run() {
                synchroCount();
        }

        private void synchroCount() {
            int repeater=0;
            while (repeater!=repeats) {
                synchronized (Part3.this) {
                    Part3.this.simpleCount();
                }
                repeater = repeater + 1;
            }
            Thread.currentThread().interrupt();
        }
    }

    public Part3(int n, int k, int t) {
        nThreads=n;
        repeats=k;
        mills=t;
        threads = new Thread[nThreads];
        for(int i=0; i<nThreads; i++){
            threads[i]=new Worker();
        }
    }

    public void reset() {
        this.counter=0;
        this.counter2=0;
    }

    public void test() {
        start();
        waitThreadsEnd(threads);
    }

    public void testSync() {
        threads = new Thread[nThreads];
        for(int i=0; i<nThreads; i++){
            threads[i]=new Worker2();
        }
        start();
        waitThreadsEnd(threads);
    }

    public static void main(String[] args) {
        Part3 p = new Part3(3, 3, 50);
        p.test();
        p.reset();
        p.testSync();
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

    private void simpleCount(){
        System.out.println(counter + " " + counter2);
        counter = counter + 1;
        try {
            Thread.sleep(this.mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        counter2 = counter2 + 1;
        System.out.println(counter + " " + counter2);
    }
}
