package udp;

import java.net.DatagramSocket;

public abstract class BasicThread extends Thread{
	private static int threadCount = 0;

	public BasicThread(String descr) {
		super(descr);
		++threadCount;
	}
	
	@Override
	public String toString() {
		return "Thread: " + this.getName();
	}
}

