package udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
import lab_1.Request;


public class Tools {
	
	static class Pair {
		private String alphanumerals;
		private long number;
		
		public Pair(String an, long n) {
			this.alphanumerals = an;
			this.number = n;
		}
		
		public String getNames() {
			return this.alphanumerals;
		}
		
		public long getNumber() {
			return this.number;
		}
	}

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
			//System.out.println("Serialized data is retrieved from bytes array");
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
			return obj;
		}
	}
	
	public static long toTimestamp(int hour, int minutes, int seconds, int day, int month, int year) {
		
		/*
		 * 	returns given date as UNIX timestamp in long format
		 *  getInstance() assumes a default type to be gregorian calendar
		 */
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND , seconds);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();
		
	}
	
	public static long fromCharToLong(Vector<Character> vec) {
		long temp = 0;
		for (int i = 0; i < vec.size(); i++) {
			temp += (vec.get(i).charValue() - 48) * Math.pow( 10, vec.size() - i - 1);
		}
		return temp;
	}
	
	public static Pair getNumberAndName(char[] name) {
		Vector<Character> tempVec = new Vector<Character>();
		String tempStr = "";
		long tempL = 0;
		int splitIndex = 0; //ostatni znak nie bêd¹cy czêœci¹ timestampu
		for (int i = 0; i < name.length; i++) {
			  //jeœli jest cyfr¹ dodaj do wektora
			  if (name[i] > 47 && name[i] < 58) {
				  tempVec.add(name[i]);
			  } else {
			//jeœli nie oczyœæ wektor
				  if (!tempVec.isEmpty()) {
					  tempVec.clear();
				  }
				  splitIndex = i;
				  //System.out.println(splitIndex);
			  }
		  }
		if(splitIndex == name.length - 1) {
		// nie ma na koncu numeru - calosc nazwy do tempStr
			for (char el : name) {
				tempStr += String.valueOf(el);
			}
		}  else {
			// znaki nazwy do (i w³¹czaj¹c) splitIndex do tempStr
			for (int i = 0; i <= splitIndex; i++) {
				tempStr += String.valueOf(name[i]);
			}
			// konwersja pozostalych do formatu long
			tempL = fromCharToLong(tempVec);
		}
		 // Wektor zape³niony jest sekwencj¹ cyfr znajduj¹cych siê na koñcu nazwy pliku
		return new Pair(tempStr, tempL);
	}
	
	public static String createStringLog(Request req) {
		String ch = ",";
		String temp = req.className() + ch + req.getDevice() + ch + req.getDescription() + ch + req.getDate();
		return temp;
	}
	
	public static Vector<File> scanThroughFiles(File[] listOfFiles, Request req) {
		Vector<File> temp = new Vector<File>();
		String searchName = req.getDevice().replaceAll("\\s","").toLowerCase() + req.getDescription().replaceAll("\\s","").toLowerCase();
		
		if (listOfFiles != null) {
  		  for (File el : listOfFiles) {
  			  // tylko pliki
  			  if (el.isFile()) {
  				  //obiekt zawierajacy timestamp z nazwy pliku oraz pozostala jej czesc
  				  Tools.Pair dividedName = Tools.getNumberAndName(el.getName().toLowerCase().toCharArray());
  				  if(dividedName.getNames().equals(searchName)) {
  					  // jeœli czesc bez timestampu sie zgadza
  					  //System.out.println("Name is corect");
  					  if (req.getEndingDate() == 0) {
  						   //jesli nie podany jest przedzial
  						  if (dividedName.getNumber() == req.getDate()) {
  					  		  //System.out.println("Znaleziono poszukiwane dane");
  					  		  temp.add(el);
  					  	  } 
  					  } else {
  						  //jest podany przedzia³
  						  if (dividedName.getNumber() >= req.getDate() && dividedName.getNumber() <= req.getEndingDate()) {

  							  temp.add(el);
  						  } 
  					  }
  				  } 
  			  }
  		  }
  	  } 
		return temp;
		
	}

}
