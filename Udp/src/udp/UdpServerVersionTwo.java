package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.util.ArrayList;
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
	        byte[] buffer = new byte[1024];

	        while(true) {
	        	DatagramPacket request = new DatagramPacket(buffer, buffer.length);
	        //	System.out.println("Waiting for request...");
	        	// czekaj na ��danie od klienta
	        	aSocket.receive(request);
	        	try {
	        		// sprawdz jakiego rodzaju ��danie zosta�o odebrane
	        		Request read = (Request)Tools.deserialize(request.getData());
	        		if (read.getType() == Request.Operation.READ) {
	        			// ��danie poszukiwania danych
	        			// wektor na znalezione dane
	        			Vector<Request> objVec = new Vector<Request>();
	        			// stw�rz list� z plikami spe�niaj�cymi szukane wymagania
	        			List<String> foundList = new ArrayList<String>();
	        			//pliki i katalogi w przeszukiwanym folderze
	        			File[] listOfFiles = new File(new File("").getAbsolutePath()).listFiles();
	        			//pliki (tylko) spe�niaj�ce kryteria wyszukiwania
	        			Vector<File> foundFiles = Tools.scanThroughFiles(listOfFiles, read);
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
	        			} 
	        			// wy�lij list� jako pakiet danych do klienta (pust� je�li nic nie znaleziono)
	        			byte[] pack = Tools.serialize(foundList);
	        			DatagramPacket reply = new DatagramPacket(pack, pack.length, 
		  	          			request.getAddress(), request.getPort());
		  	          	aSocket.send(reply); 
		  	          	if (!objVec.isEmpty()) {
		  	          	// je�li znaleziono szukane dane - wy�lij je
	        				byte[] data = Tools.serialize(objVec);
	        				DatagramPacket dataPack = new DatagramPacket(data, data.length, 
			  	          			request.getAddress(), request.getPort());
	        				aSocket.send(dataPack); 
		  	          	}
		  	          
	        		} else {
	        		// ��danie zapisu przes�anych danych
		        	// zapisz do pliku odebrane dane
	        			String name = read.getDevice().replaceAll("\\s","") + read.getDescription().replaceAll("\\s","") + read.getDate();
	        			System.out.println(name);
	        			Files.write(new File(name).toPath(), request.getData());
		          }
	         // nast�pi� b��d przy odbiorze danych, serializacji lub deserializacji
		      } catch (ClassNotFoundException ex) {
					Logger.getLogger(UdpClient.class.getName()).log(Level.FINEST, null, ex);
					System.out.println("Server: Wrong data format");
					ex.printStackTrace();
		      }
	        }
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
	protected static byte[] list = new byte[1024];
	protected static byte[] data = new byte[2048];
	protected static DatagramSocket aSocket;
	protected static Request.Operation readOrWrite;
	
	protected ServerRoutine(String threadId) {
		super(threadId);
	}
	
	protected static void waitAndReceive() throws IOException, ClassNotFoundException {
		byte[] temp = new byte[1024];
		DatagramPacket tempPack = new DatagramPacket(temp, temp.length);
		System.out.println("Nowe wywo�anie waitAndReceive()");
		
		
	}
	
	protected static void setSocket(DatagramSocket ds) throws SocketException{
		aSocket = ds;
	}
	
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
		
	}
 }
