package szalkezeles;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Scene4 {

	volatile boolean shouldStop = false;

	// ahányat adunk neki értéknek, annyi szálat enged át egyszerre
	final Semaphore semaphore = new Semaphore (1, true);

	private class Szal1 extends Thread {

		private final Random random = new Random ();

		private final LinkedList<Double> list;

		public Szal1 (final LinkedList<Double> l) {
			list = l;
		}

		@Override
		public void run () {
			long counter = 0;
			try {
				while (!shouldStop) {
					semaphore.acquire ();
					list.addFirst (random.nextDouble ());
					list.removeLast ();
					semaphore.release ();
					++counter;
				}
			}
			catch (final Exception ex) {
				System.err.println("Szal1: " + ex);
				shouldStop = true;
			}
			System.out.println("Szal1: " + counter + " db");
		}
	}


	private class Szal2 extends Thread {

		private final Random random = new Random ();

		private final LinkedList<Double> list;

		public Szal2 (final LinkedList<Double> l) {
			list = l;
		}

		@Override
		public void run () {
			long counter = 0;
			try {
				while (!shouldStop) {
					semaphore.acquire ();
					if (!list.isEmpty ()) {
						list.removeLast ();
					}
					semaphore.release ();
					++counter;
				}
			}
			catch (final Exception ex) {
				System.err.println("Szal2: " + ex);
				shouldStop = true;
			}
			System.out.println("Szal2: " + counter + " db");
		}
	}


	public void futtat () {
		final LinkedList<Double> list = new LinkedList<> ();
		final Szal1 sz1 = new Szal1 (list);
		final Szal2 sz2 = new Szal2 (list);

		sz2.start ();
		sz1.start ();

		System.out.println("Uss entert amikor szeretned hogy lealljanak a szalak.");
		new Scanner (System.in).nextLine ();

		shouldStop = true;

		try {
			sz1.join ();
			sz2.join ();
		}
		catch (final InterruptedException ignore) {}
	}
}
