package lab_1;

public class Spectrum<T> extends Sequence<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7536016929298163009L;
	private String scaling;

	public Spectrum() {
		super();
		this.scaling = "linear";
	}
	
	public Spectrum(String device,
					String description, 
					long date,
					int channelNr, 
					String unit, 
					double resolution, 
					T[] buffer,
					String scaling) {
		super(device,
				description, 
				date,
				channelNr, 
				unit, 
				resolution, 
				buffer);
		this.scaling = scaling;
	}
	
	@Override 
	public String toString() { return super.toString() + "Scaling: " + scaling + "\n" + dataToString();}
	
	@Override
	public String className() {
		return "Spectrum";
	}
}