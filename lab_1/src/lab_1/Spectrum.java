package lab_1;

public class Spectrum<T> extends Sequence<T> {
	private String scaling;

	public Spectrum(String device,
					String description, 
					long date,
					int channelNr, 
					String unit, 
					double resolution, 
					T[] buffer) {
		super(device,
				description, 
				date,
				channelNr, 
				unit, 
				resolution, 
				buffer);
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

}