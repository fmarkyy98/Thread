package szalkezeles;

import java.util.Scanner;

public class Jelenet2 {

	private class Szal extends Thread {

		@Override
		public void run () {
			System.out.println("Szal elindult");
			while (!isInterrupted ())
				;
			System.out.println("Szal befejezodott");
		}
	}


	public void futtat () {
		final Szal sz = new Szal ();
		sz.start ();

		System.out.println("Uss entert hogy lealljon a szal.");

		new Scanner (System.in).nextLine ();

		sz.interrupt ();

		try { sz.join (); }
		catch (final InterruptedException ignore) {}
	}
	
}
