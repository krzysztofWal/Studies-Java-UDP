package lab_1;

public class Alarm<T> extends Pakcet {
	private int channelNr;
	private T threshold;
	private int direction; // 0 - dowolny, -1 - w d�, 1 - w g�r�
	
	public Alarm() {
		super();
		this.channelNr = 0;
		this.threshold = null;
		this.direction = 0;
	}
	
	public Alarm(String device, String description, long date, int channelNr, T threshold, int direction) {
		super(device, description, date);
		this.channelNr = channelNr;
		this.threshold = threshold;
		this.direction = direction;
	}
	
}
