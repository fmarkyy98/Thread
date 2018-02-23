package szalkezeles;

import java.util.concurrent.TimeUnit;

public class Scene1 {

	class MyThread extends Thread {

		private final long waitTime;

		public MyThread (final long wt) {
			waitTime = wt;
		}

		@Override
		public void run () {
			System.out.println ("" + waitTime + " seconds thread started |--...");
			try {
				// 1s = 1000ms
				Thread.sleep (waitTime * 1000);
			}
			catch (final InterruptedException ignore) {}
			System.out.println ("" + waitTime + " seconds thread stopped ...--|");
		}
	}


	public void run () {
		final long startTime = System.nanoTime ();
		
		final MyThread thread10 = new MyThread (10);
		final MyThread thread5 = new MyThread (5);
		final MyThread thread1 = new MyThread (1);
		
		thread10.start ();
		thread5.start ();
		thread1.start ();

		try {
			thread5.join ();
			thread10.join ();
			thread1.join ();
		}
		catch (final InterruptedException ignore) {}

		final long endTime = System.nanoTime ();

		System.out.println ("Spent time: " + TimeUnit.NANOSECONDS.toSeconds (endTime - startTime) + "sec");
	}
}
