package szalkezeles;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

public class Scene3 {

	volatile boolean shouldStop = false;


	private class Szal1 extends Thread {

		private final Random random = new Random ();

		private final LinkedList<Double> list;

		public Szal1 (final LinkedList<Double> l) {
			list = l;
		}

		@Override
		public void run () {
			try {
				while (!shouldStop) {
					list.addFirst (random.nextDouble ());
					//try { sleep (200); }
					//catch (final InterruptedException ignore) {}
					list.removeLast ();
				}
			}
			catch (final Exception ex) {
				System.err.println("Szal1: " + ex);
				shouldStop = true;
			}
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
			try {
				while (!shouldStop) {
					if (!list.isEmpty ())
						list.removeLast ();
				}
			}
			catch (final NoSuchElementException ex) {
				System.err.println("Szal2: " + ex);
				shouldStop = true;
			}
		}
	}


	public void futtat () {
		final LinkedList<Double> list = new LinkedList<> ();
		final Szal1 sz1 = new Szal1 (list);
		final Szal2 sz2 = new Szal2 (list);

		sz2.start ();
		sz1.start ();

		try {
			sz1.join ();
			sz2.join ();
		}
		catch (final InterruptedException ignore) {}
	}
}
