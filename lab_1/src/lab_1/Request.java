package lab_1;

public class Request extends Packet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -714564125246870672L;
	protected Operation type; 
	protected long endingDate = 0;
	
	protected Request() {
		super();
	}
	
	protected Request(String device, String description, long date) {
		super(device, description, date);
	}
	
	protected Request(Operation type, String device, String description, long date) {
		super(device, description, date);
		this.type = type;
	}
	
	protected Request(Operation type, String device, String description, long beginningDate, long endingDate) {
		super(device, description, beginningDate);
		this.endingDate = endingDate;
		this.type = type;
	}
	
	public static Request newReadRequest(String device, String description, long date) {
		Request obj = new Request(Operation.READ, device, description, date);
		return obj;
	}
	
	public static Request newReadRequest(String device, String description, long beginningDate, long endingDate) {
		Request obj = new Request(Operation.READ, device, description, beginningDate, endingDate);
		return obj;
	}
	
	public static Class<Request> getTypeOfClass() {
		//Packet.class;
		return Request.class;
	}
	
	/*
	public void setTypeOfRequest(Operation type) {
		this.type = type;
	}
	
	public void setEndingDateOfRequest(long endingDate) {
		this.endingDate = endingDate;
	}
	*/
	public Operation getType() {
		return type;
	}
	
	public long getEndingDate() {
		return endingDate;
	}
	
	public String className() {
		return "Request";
	}
	
	public static enum Operation {
		READ, WRITE
	}
}
