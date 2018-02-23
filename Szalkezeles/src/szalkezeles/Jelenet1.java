package szalkezeles;

import java.util.concurrent.TimeUnit;

public class Jelenet1 {

	class Szal extends Thread {

		private final long waitTime;

		public Szal (final long wt) {
			waitTime = wt;
		}

		@Override
		public void run () {
			System.out.println ("" + waitTime + " masodperces szal elindult");
			try {
				// 1s = 1000ms
				Thread.sleep (waitTime * 1000);
			}
			catch (final InterruptedException ignore) {}
			System.out.println ("" + waitTime + " masodperces szal befejezodott");
		}
	}


	public void futtat () {
		final long startTime = System.nanoTime ();
		
		final Szal sz10 = new Szal (10);
		final Szal sz5 = new Szal (5);
		final Szal sz1 = new Szal (1);
		
		sz10.start ();
		sz5.start ();
		sz1.start ();

		try {
			sz5.join ();
			sz10.join ();
			sz1.join ();
		}
		catch (final InterruptedException ignore) {}

		final long endTime = System.nanoTime ();

		System.out.println ("Eltelt ido: " + TimeUnit.NANOSECONDS.toSeconds (endTime - startTime) + "mp");
	}
}
