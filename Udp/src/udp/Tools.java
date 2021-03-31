package udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;


public class Tools {

	/**
	 * serializing the object passed as the argument
	 * to a byte array which is returned as the result 
	 * @param obj - object to serialize
	 * @return - byte array
	 */
	public static byte[] serialize(Object obj) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
		  out = new ObjectOutputStream(bos);   
			out.writeObject(obj);
			out.flush();
		  return bos.toByteArray();
		}
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		finally {
		  try {
		    bos.close();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
	  }
	}
	
	/**
	 * deserializing the object from the byte array passed as the argument 
	 * 
	 * @param bytes - byte array that contains serialized object
	 * @return - the object on success, null on failure
	 */
	public static Object deserialize(byte[] bytes) throws ClassNotFoundException {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(bis);
			obj = in.readObject();
			in.close();
			System.out.println("Serialized data is retrieved from bytes array");
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
			return obj;
		}
	}
}
