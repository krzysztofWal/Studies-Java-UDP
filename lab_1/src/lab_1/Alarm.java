package lab_1;

public class Alarm<T> extends Request{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7603134955851482488L;
	private int channelNr;
	private T threshold;
	private int direction; // 0 - dowolny, -1 - w dó³, 1 - w górê
	
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
	
	@Override 
	public String toString() { 
		String temp = super.toString() + "Channel Nr: " + channelNr + "\nThreshold: " + threshold + "\nDirection: ";
		if (direction == 0) {
			temp += "Up or down";
		} else if (direction == 1) {
			temp += "Up";
		} else if (direction == -1) {
			temp += "Down";
		} else {
			temp += "Unknown direction";
		}
		return temp + "\n";
		}
	
	public void makeWriteRequest() {
		this.type = Operation.WRITE;
	}
	
	@Override
	public String className() {
		return "Alarm";
	}
	
}
