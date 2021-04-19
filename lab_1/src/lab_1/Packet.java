package lab_1;

import java.io.Serializable;

public abstract class Packet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8264090306751907019L;
	protected String device;
	protected String description;
	protected long date;
	
	public Packet() {
		device = "not given";
		description = "not given";
		date = 0;
	}
	
	public Packet(String device, String description, long date) {
		this.device = device;
		this.description = description;
		this.date = date;
	}
	
	public String getDevice() {
		return device;
	}
	
	public String getDescription() {
		return description;
	}
	
	public long getDate() {
		return date;
	}
	
	
	@Override
	public String toString() {
		return "Device : " + this.device +
				"\nDescription : " + this.description +
				"\nDate : " + this.date +
				"\n";
	}
	
	
	
}
