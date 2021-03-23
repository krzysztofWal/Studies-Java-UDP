package lab_1;

public abstract class Sequence<T> extends Pakcet{
	protected int channelNr;
	protected String unit;
	protected double resolution;
	protected T[] buffer;

	public Sequence() {
		super();
		this.channelNr = 0;
		this.unit = "not given";
		this.resolution = 0.0;
		this.buffer = (T[]) new Object[10];
	}
	
	public Sequence(String device, String description, long date, int channelNr, String unit, double resolution, T[] buffer) {
		super(device, description, date);
		this.channelNr = 0;
		this.unit = "not given";
		this.resolution = 0.0;
		this.buffer = buffer;
	}
	
	@Override
	public String toString() {
		return super.toString() + 
				"\nChannel Number : " + this.channelNr +
				"\nUnit :" + this.unit +
				"\nResolution : " + this.resolution +
				"\n";
	}

}
