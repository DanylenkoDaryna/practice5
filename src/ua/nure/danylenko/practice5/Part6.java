package ua.nure.danylenko.practice5;

public class Part6 {

    private static final Object M = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (M) {
                        // start
                        M.wait();
                        // 2x wait
                        M.wait();
                        Thread.interrupted();
                        if(Thread.currentThread().isInterrupted()){
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
        synchronized (M) {
            M.wait(2000);
        }
        synchronized (M) {
           // System.out.println(t.getState()+" wait")
            M.notifyAll();
            // + " block")
            System.out.println(t.getState());
        }
        ///////////////////////////////
        synchronized (M) {
            M.wait(2000);
        }
        synchronized (M) {
            //"wait"
            System.out.println(t.getState());
            M.notifyAll();
            //System.out.println(t.getState() + " block")
        }
        ///////////////////////////
        synchronized (M) {
            M.wait(1000);
        }
        t.interrupt();
        System.out.println(t.getState());
    }
}

