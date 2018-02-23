package szalkezeles;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

public class Scene3 {

    volatile boolean shouldStop = false;

    private class MyThread1 extends Thread {

        private final Random random = new Random();

        private final LinkedList<Double> list;

        public MyThread1(final LinkedList<Double> l) {
            list = l;
        }

        @Override
        public void run() {
            try {
                while (!shouldStop) {
                    list.addFirst(random.nextDouble());
                    //try { sleep (200); }
                    //catch (final InterruptedException ignore) {}
                    list.removeLast();
                }
            } catch (final Exception ex) {
                System.err.println("Thread1: " + ex);
                shouldStop = true;
            }
        }
    }

    private class MyThread2 extends Thread {

        private final Random random = new Random();

        private final LinkedList<Double> list;

        public MyThread2(final LinkedList<Double> l) {
            list = l;
        }

        @Override
        public void run() {
            try {
                while (!shouldStop) {
                    if (!list.isEmpty()) {
                        list.removeLast();
                    }
                }
            } catch (final NoSuchElementException ex) {
                System.err.println("Thread2: " + ex);
                shouldStop = true;
            }
        }
    }

    public void futtat() {
        final LinkedList<Double> list = new LinkedList<>();
        final MyThread1 myThread1 = new MyThread1(list);
        final MyThread2 myThread2 = new MyThread2(list);

        myThread2.start();
        myThread1.start();

        try {
            myThread1.join();
            myThread2.join();
        } catch (final InterruptedException ignore) {
        }
    }
}
