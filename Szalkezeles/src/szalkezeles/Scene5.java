package szalkezeles;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Scene5 {

    volatile boolean shouldStop = false;

    private class Szal1 extends Thread {

        private final Random random = new Random();

        private final LinkedList<Double> list;

        public Szal1(final LinkedList<Double> l) {
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

    private class Szal2 extends Thread {

        private final Random random = new Random();

        private final LinkedList<Double> list;

        public Szal2(final LinkedList<Double> l) {
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

    public void futtat() {
        final LinkedList<Double> list = new LinkedList<>();
        final Szal1 sz1 = new Szal1(list);
        final Szal2 sz2 = new Szal2(list);

        sz2.start();
        sz1.start();

        System.out.println("Press Enter to stop all threads.");
        new Scanner(System.in).nextLine();

        shouldStop = true;

        try {
            sz1.join();
            sz2.join();
        } catch (final InterruptedException ignore) {
        }
    }
}
