package udp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab_1.Request;
import lab_1.Packet;

public class UdpClientVersionTwo {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		DatagramSocket aSocket = null;
		
		try {

			if (args.length < 1) {
				System.out.println("Usage: UDPClient <server host name>");
				System.exit(-1);
			}
			
			byte[] buffer = new byte[1024];
			byte[] buffer2 = new byte[2048];
 			InetAddress aHost = InetAddress.getByName(args[0]);
			int serverPort = 9876;
			aSocket = new DatagramSocket();
			
			//wys³anie ¿¹dania
			Request rq = Request.newReadRequest("First device", "First description", Tools.toTimestamp(0,0,0,1,Calendar.JUNE, 2019));
		 	byte[] data = Tools.serialize(rq);
			DatagramPacket toSend = new DatagramPacket(data, data.length, aHost, serverPort);
			aSocket.send(toSend);
			System.out.println("Klient: Wys³ano pakiet");
			try {
<<<<<<< HEAD
				//wyslano zapytanie o odczytanie danych
=======
				// wys³ano ¿¹danie odczytu
>>>>>>> master
				if(rq.getType() == Request.Operation.READ) {
					
					ClientReceiveListSubroutine threadOne = new ClientReceiveListSubroutine("ListThread");
					ClientReceiveDataSubroutine threadTwo = new ClientReceiveDataSubroutine("DataThread");
					ClientSubroutine.setSocket(aSocket, 100);
					threadOne.start();
					threadTwo.start();
		
					threadOne.join();
					threadTwo.join();
					
				} else {
				// wys³ano zapytanie o zapisanie do pliku
					Boolean confirmation = (Boolean) Tools.deserialize(buffer);
					if (confirmation) {
						System.out.println("Klient: Odebrano potwierdzenie zapisu danych");
					}
				}
			} catch (ClassNotFoundException ex) {
				//nast¹pi³ b³¹d przy deserializacji danych otrzymanych z serwera
				Logger.getLogger(UdpClient.class.getName()).log(Level.FINEST, null, ex);
				System.out.println("Klient: B³¹d przy odbiorze danych z serwera");
			} 
		} catch (SocketException ex) {
			Logger.getLogger(UdpClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnknownHostException ex) {
			Logger.getLogger(UdpClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(UdpClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(UdpClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			aSocket.close();
		}
	}
}

class ClientSubroutine extends BasicThread {

	protected static byte[] list = new byte[1024];
	protected static byte[] data = new byte[2048];
	protected static boolean listSet, dataSet;
	protected static DatagramSocket aSocket;
<<<<<<< HEAD
=======
	
>>>>>>> master
	protected ClientSubroutine(String descr) {
		super(descr);
	}

	// czekaj na pakiet od servera (z timeoutem) i jeœli nadejdzie zapisz go do odpowiedniego pola - tablicy danych
	protected static void waitAndReceive() throws IOException, ClassNotFoundException, SocketTimeoutException  {
		byte[] temp = new byte[2048];
<<<<<<< HEAD
		DatagramPacket tempPack = new DatagramPacket(new byte[2048], 2048);
		tempPack.setData(temp);
//		System.out.println("Nowe wywo³anie");
=======
		DatagramPacket tempPack = new DatagramPacket(temp, temp.length);
		System.out.println("Nowe wywo³anie");
		try {
>>>>>>> master
			aSocket.receive(tempPack);
				if (Tools.deserialize(temp).toString().contains("Device")) {
//					System.out.println("Dane odebrane");
					data = temp;
					dataSet = true;
				} else {
					for (int i = 0; i < 1024; i++) {
						list[i] = temp[i];
					}
					listSet = true;
//					System.out.println("Lista odebrana");
				}	
<<<<<<< HEAD

=======
		} catch (SocketTimeoutException ex) {
			throw new SocketTimeoutException();
		}
>>>>>>> master
	}
	
	//przypisz socket na którym operowaæ bêd¹ w¹tki podklas ClientSubroutine
	public static void setSocket(DatagramSocket ds, int timeout) throws SocketException, IllegalArgumentException{
		aSocket = ds;
		aSocket.setSoTimeout(timeout);
	}

}

final class ClientReceiveListSubroutine extends ClientSubroutine{
	public ClientReceiveListSubroutine(String descr) {
		super(descr);
	}
	
	@Override
	public void run() {
		//jeœli pakiet nie jest odebrany
		try {	
			while (!listSet) {
<<<<<<< HEAD
				//czekaj na nadchodzacy pakiet i jeœli jakiœ nadejdzie to wpisz go jako odpowiedni
				try {
					synchronized(this) {
						//System.out.println(this.getName());
						ClientSubroutine.waitAndReceive();
					}
				//jesli w tym czasie pakiet nie nadejdzie, zeby nie "zapychac watku", zrezygnuj
=======
				//czekaj na nadchodzacy pakiet i jeœli jakiœ nadejdzie to wpisz go jako odpowiedni				
				try {
					synchronized(this) {
						System.out.println(this.getName());
						ClientSubroutine.waitAndReceive();
					}
				//jeœli w tym czasie pakiet nie nadejdzie, ¿eby nie "zapychaæ w¹tku", zrezygnuj
>>>>>>> master
				} catch (SocketTimeoutException ex) {}
			}
			List<String> read = (List<String>)Tools.deserialize(ClientSubroutine.list);
			if (read.size() != 0) {
				//jeœli lista nie jest pusta, wypisz wiersze listy
				synchronized(this) {
					System.out.println("Klient: Znaleziono dane z pomiarów:");
					for (Object el : read.toArray()) {
						System.out.println(el);
					}
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(ClientReceiveListSubroutine.class.getName()).log(Level.FINEST, null, ex);
			System.out.println("Klient: " + this.getName() + ": B³¹d przy odbiorze danych z serwera");
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ClientReceiveListSubroutine.class.getName()).log(Level.FINEST, null, ex);
			System.out.println("Klient: " + this.getName() + ": B³¹d przy odbiorze danych z serwera");
		}
	}
}

final class ClientReceiveDataSubroutine extends ClientSubroutine{
	public ClientReceiveDataSubroutine(String descr) {
		super(descr);
	}
	
	@Override
	public void run() {
		//jesli pakiet nie jest odebrany
		try {

			while (!dataSet) {
				//czekaj na nadchodzacy pakiet i jesli jakiœ nadejdzie to wpisz go jako odpowiedni
				try {
					synchronized(this) {
<<<<<<< HEAD
					//System.out.println(this.getName());
					ClientSubroutine.waitAndReceive();
					}
				//jesli w tym czasie pakiet nie nadejdzie, ¿eby nie "zapychac watku", zrezygnuj
				} catch (SocketTimeoutException ex) {}
=======
					System.out.println(this.getName());
						ClientSubroutine.waitAndReceive();
					}
				//jeœli w tym czasie pakiet nie nadejdzie, ¿eby nie "zapychaæ w¹tku", zrezygnuj
				} catch (SocketTimeoutException ex) {
				}
>>>>>>> master
			}
			Vector<Request> dataVec = (Vector<Request>) Tools.deserialize(ClientSubroutine.data);
			synchronized (this) {
				if (dataVec.size() != 0) {
					System.out.println("Klient: Iloœæ pomiarów, z których otrzymano dane: " + dataVec.size());
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(ClientReceiveDataSubroutine.class.getName()).log(Level.FINEST, null, ex);
			System.out.println("Klient: " + this.getName() + ": B³¹d przy odbiorze danych z serwera");
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ClientReceiveDataSubroutine.class.getName()).log(Level.FINEST, null, ex);
			System.out.println("Klient: " + this.getName() + ": B³¹d przy odbiorze danych z serwera");
		}
	}
}
<<<<<<< HEAD
=======

>>>>>>> master
