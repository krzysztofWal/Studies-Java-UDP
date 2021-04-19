package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab_1.Request;
import java.io.File;
import java.util.List;

public class UdpServerVersionTwo {
	
	 public static void main(String[] args) {
	    	DatagramSocket aSocket = null;
	      try {
	    	aSocket = new DatagramSocket(9876);
	        System.out.println(aSocket.getLocalAddress());
	        
	        ServerRoutine thr1 = new ServerRoutine("Main routine");
	        ServerRoutine.setSocket(aSocket);
	        ServerReadRoutine thr2 = new ServerReadRoutine("Read data thread");
	        ServerReceiveRoutine thr3 = new ServerReceiveRoutine("Receive thread");
	        
	        thr1.start();
	        thr2.start();
	        thr3.start();
	        
	        /*byte[] buffer = new byte[1024];

	        while(true) {
	        	DatagramPacket request = new DatagramPacket(buffer, buffer.length);
	        //	System.out.println("Waiting for request...");
	        	// czekaj na ¿¹danie od klienta
	        	aSocket.receive(request);
	        	try {
	        		// sprawdz jakiego rodzaju ¿¹danie zosta³o odebrane
	        		Request read = (Request)Tools.deserialize(request.getData());
	        		if (read.getType() == Request.Operation.READ) {
	        			
		  	          
	        		} else {
	        		// ¿¹danie zapisu przes³anych danych
		        	// zapisz do pliku odebrane dane
	        			String name = read.getDevice().replaceAll("\\s","") + read.getDescription().replaceAll("\\s","") + read.getDate();
	        			System.out.println(name);
	        			Files.write(new File(name).toPath(), request.getData());
		          }
	         // nast¹pi³ b³¹d przy odbiorze danych, serializacji lub deserializacji
		      } catch (ClassNotFoundException ex) {
					Logger.getLogger(UdpClient.class.getName()).log(Level.FINEST, null, ex);
					System.out.println("Server: Wrong data format");
					ex.printStackTrace();
		      }
	        }
	        */
	    } catch (SocketException ex) {
	        Logger.getLogger(UdpServer.class.getName()).log(Level.SEVERE, null, ex);
	    } catch (IOException ex) {
	        Logger.getLogger(UdpServer.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	    	aSocket.close();
	    	
		}
	      
	    }
}

class ServerRoutine extends BasicThread {
	protected static DatagramSocket aSocket;
	protected static Vector<Request> reqReadVec = new Vector<Request>();
	protected static Vector<Request> reqWriteVec = new Vector<Request>();
	protected static Vector<List<String>> sendListVec = new Vector<List<String>>();
	protected static Vector<Vector<Request>> sendDataVec = new Vector<Vector<Request>>();
	
	
	protected ServerRoutine(String threadId) {
		super(threadId);
	}
	
	protected static void setSocket(DatagramSocket ds) throws SocketException{
		aSocket = ds;
		aSocket.setSoTimeout(100);
	}
	
	protected static void waitAndReceive() throws IOException, ClassNotFoundException, SocketTimeoutException {
		byte[] temp = new byte[1024];
		DatagramPacket tempPack = new DatagramPacket(temp, temp.length);
//			System.out.println("Nowe wywo³anie waitAndReceive()");
		aSocket.receive(tempPack);
		Request req = (Request) Tools.deserialize(tempPack.getData());
		
		if(req.getType() == Request.Operation.READ) {
			reqReadVec.add(req);
		} else {
			reqWriteVec.add(req);
		}
	}
	
}

class ServerReceiveRoutine extends ServerRoutine {
	
	public ServerReceiveRoutine(String threadId) {
		super(threadId);
	}
	
	@Override
	public void run() {
		System.out.println("Started receive");
		try {
			while (true) {
				try {
					synchronized(this) {
					System.out.println(this.getName());
						ServerRoutine.waitAndReceive();
					}
				//jeœli w tym czasie pakiet nie nadejdzie, ¿eby nie "zapychaæ w¹tku", zrezygnuj
				} catch (SocketTimeoutException ex) {
				}
			}
		} catch (Exception ex) {
			System.out.println("Something is fucked up");
		}
	}
		/*
		while (true) {
			try {
				synchronized(this) {
				ServerRoutine.waitAndReceive();
				}
			} catch (IOException ex) {
				Logger.getLogger(ServerReceiveRoutine.class.getName()).log(Level.FINEST, null, ex);
				System.out.println("Server: " + this.getName() + ": Error reading request");
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ServerReceiveRoutine.class.getName()).log(Level.FINEST, null, ex);
				System.out.println("Server: " + this.getName() + ": Error reading request");
			}
		}
		*/

}

 class ServerSaveRoutine extends ServerRoutine {
	public ServerSaveRoutine(String threadId) {
		super(threadId);
	}
	
	@Override
	public void run() {
	}

}
 
 class ServerReadRoutine extends ServerRoutine {
	 public ServerReadRoutine(String threadId) {
		 super(threadId);
	 }
	 
	@Override
	public void run() {
		System.out.println("Started read routine");
		while (true) {
			try {
				if (!ServerRoutine.reqReadVec.isEmpty()) {
		
					// wektor na znalezione dane
					Vector<Request> objVec = new Vector<Request>();
					// stwórz listê z plikami spe³niaj¹cymi szukane wymagania
					List<String> foundList = new ArrayList<String>();
					
					//pliki i katalogi w przeszukiwanym folderze
					File[] listOfFiles = new File(new File("").getAbsolutePath()).listFiles();
					//pliki (tylko) spe³niaj¹ce kryteria wyszukiwania
					Vector<File> foundFiles = Tools.scanThroughFiles(listOfFiles, reqReadVec.lastElement());
					for (File el : foundFiles) {
						File in = new File(el.getName());
						//odczytaj dane w znalezionych plikach
						byte[] buf = Files.readAllBytes(in.toPath());
						Request objTemp = (Request)Tools.deserialize(buf);
						//dodaj wpis do listy a dane do wektora
						foundList.add(Tools.createStringLog(objTemp));
						objVec.add(objTemp);
					}
					if (objVec.isEmpty()) {
						System.out.println("Server: Didn't find what you were looking for");
						ServerRoutine.sendListVec.add(foundList);
					}  else {
						ServerRoutine.sendDataVec.add(objVec);
						ServerRoutine.sendListVec.add(foundList);
					}
				}
			} catch (IOException ex) {
				Logger.getLogger(ServerRoutine.class.getName()).log(Level.FINEST, null, ex);
				System.out.println("Server: " + this.getName() + ": Error while reading data");
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(ServerRoutine.class.getName()).log(Level.FINEST, null, ex);
				System.out.println("Server: " + this.getName() + ": Error while reading data");
			}
		}
	}
}

 
 class ServerSendRoutine extends ServerRoutine {
	 
	 private SendType type; 
	 
	 public static enum SendType {
		 list, data
	 }
	 
	 public ServerSendRoutine(String threadId, SendType type) {
		 super(threadId);
		 this.type = type;
	 }
	 
	 
	 public void run() {
		while (true) {
			try {
				if ((type == SendType.list & !sendListVec.isEmpty()) ||
						(type == SendType.data & !sendDataVec.isEmpty())) {
					
					DatagramPacket tempPack = new DatagramPacket(new byte[2048], 2048);
					
					if (type == SendType.list) {
						byte[] temp = Tools.serialize(sendListVec.lastElement());
						tempPack.setData(temp);
					} else {
						byte[] temp = Tools.serialize(sendDataVec.lastElement());
						tempPack.setData(temp);
					}
					
					ServerRoutine.aSocket.send(tempPack);
				}
			} catch (IOException ex) {
				Logger.getLogger(ServerRoutine.class.getName()).log(Level.FINEST, null, ex);
				System.out.println("Server: " + this.getName() + ": Error while sending " + type.toString());
			}
		}
	 }
 }
