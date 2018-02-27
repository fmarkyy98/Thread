package szalkezeles;

import java.util.Scanner;

public class Scene2 {

    private class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("Thread started.");
            while (!isInterrupted())
				;
            System.out.println("Thread stopped.");
        }
    }

    public void run() {
        final MyThread thred = new MyThread();
        thred.start();

        System.out.println("Press Enter to stop the thread.");

        new Scanner(System.in).nextLine();

        thred.interrupt();

        try {
            thred.join();
        } catch (final InterruptedException ignore) {
        }
    }

}
