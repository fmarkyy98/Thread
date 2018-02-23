package szalkezeles;

import java.util.Scanner;

public class Scene2 {

	private class Szal extends Thread {

		@Override
		public void run () {
			System.out.println("Thread started.");
			while (!isInterrupted ())
				;
			System.out.println("Thread stopped.");
		}
	}


	public void futtat () {
		final Szal sz = new Szal ();
		sz.start ();

		System.out.println("Press Enter to stop the thread.");

		new Scanner (System.in).nextLine ();

		sz.interrupt ();

		try { sz.join (); }
		catch (final InterruptedException ignore) {}
	}
	
}
