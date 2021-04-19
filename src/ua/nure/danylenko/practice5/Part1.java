package ua.nure.danylenko.practice5;

public class Part1 extends Thread {
    @Override
    public void run() {
        outName();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Part1();
        t.start();
        t.join();
        Thread t2 = new Thread(new Child());
        t2.start();
        t2.join();
        Thread t3 = new Thread(Part1::outName);
        t3.start();
        t3.join();
    }

    public static void outName(){
       long startTime = System.currentTimeMillis();
       while((System.currentTimeMillis() - startTime)<900) {
            try {
                Thread.sleep(333);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
                }
            }
    }
}

