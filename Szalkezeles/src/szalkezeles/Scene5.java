package szalkezeles;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Scene5 {

    volatile boolean shouldStop = false;

    private class MyThread1 extends Thread {

        private final Random random = new Random();

        private final LinkedList<Double> list;

        public MyThread1(final LinkedList<Double> l) {
            list = l;
        }

        @Override
        public void run() {
            long counter = 0;
            try {
                while (!shouldStop) {
                    synchronized (list) {
                        list.addFirst(random.nextDouble());
                        list.removeLast();
                    }
                    ++counter;
                }
            } catch (final Exception ex) {
                System.err.println("Thread1: " + ex);
                shouldStop = true;
            }
            System.out.println("Thread1: " + counter + " peaces");
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
            long counter = 0;
            try {
                while (!shouldStop) {
                    synchronized (list) {
                        if (!list.isEmpty()) {
                            list.removeLast();
                        }
                    }
                    ++counter;
                }
            } catch (final Exception ex) {
                System.err.println("Thread2: " + ex);
                shouldStop = true;
            }
            System.out.println("Thread2: " + counter + " peaces");
        }
    }

    public void run() {
        final LinkedList<Double> list = new LinkedList<>();
        final MyThread1 thread1 = new MyThread1(list);
        final MyThread2 Thread2 = new MyThread2(list);

        Thread2.start();
        thread1.start();

        System.out.println("Press Enter to stop all threads.");
        new Scanner(System.in).nextLine();

        shouldStop = true;

        try {
            thread1.join();
            Thread2.join();
        } catch (final InterruptedException ignore) {
        }
    }
}
