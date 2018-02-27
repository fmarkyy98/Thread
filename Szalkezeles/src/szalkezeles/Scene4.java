package szalkezeles;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Scene4 {

    volatile boolean shouldStop = false;

    // ahányat adunk neki értéknek, annyi szálat enged át egyszerre
    final Semaphore semaphore = new Semaphore(1, true);

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
                    semaphore.acquire();
                    list.addFirst(random.nextDouble());
                    list.removeLast();
                    semaphore.release();
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
                    semaphore.acquire();
                    if (!list.isEmpty()) {
                        list.removeLast();
                    }
                    semaphore.release();
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
        final MyThread1 myThread1 = new MyThread1(list);
        final MyThread2 myThread2 = new MyThread2(list);

        myThread2.start();
        myThread1.start();

        System.out.println("Press Enter to stop all threads.");
        new Scanner(System.in).nextLine();

        shouldStop = true;

        try {
            myThread1.join();
            myThread2.join();
        } catch (final InterruptedException ignore) {
        }
    }
}
