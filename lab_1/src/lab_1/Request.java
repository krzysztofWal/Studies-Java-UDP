package lab_1;

public class Request extends Packet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -714564125246870672L;
	protected Operation type; 
	protected long endingDate = 0;
	protected int ID;
	
	protected Request() {
		super();
	}
	
	protected Request(String device, String description, long date) {
		super(device, description, date);
	}
	
	protected Request(Operation type, String device, String description, long date, int ID) {
		super(device, description, date);
		this.type = type;
		this.ID = ID;
	}
	
	protected Request(Operation type, String device, String description, long beginningDate, long endingDate, int ID) {
		super(device, description, beginningDate);
		this.endingDate = endingDate;
		this.type = type;
		this.ID = ID;
	}
	
	public static Request newReadRequest(String device, String description, long date, int ID) {
		Request obj = new Request(Operation.READ, device, description, date, ID);
		return obj;
	}
	
	public static Request newReadRequest(String device, String description, long beginningDate, long endingDate, int ID) {
		Request obj = new Request(Operation.READ, device, description, beginningDate, endingDate, ID);
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
	
	public int getID() {
		return ID;
	}
	
	public String className() {
		return "Request";
	}
	
	public static enum Operation {
		READ, WRITE
	}
}
