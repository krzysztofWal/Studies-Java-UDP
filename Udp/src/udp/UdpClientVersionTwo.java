package udp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab_1.Request;

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
			//oczekiwanie na odpowiedz
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
			//odczytanie odpowiedzi
			System.out.println("Klient: Odebrano odpowiedz od serwera");
			try {
				if(rq.getType() == Request.Operation.READ) {
				//wys³ano zapytanie poszukuj¹ce
					//odczytaj listê z odebranymi danymi
					List<String> read = (List<String>)Tools.deserialize(reply.getData());
					if (read.size() != 0) {
						//jeœli lista nie jest pusta
						// wypisz wiersze listy
						System.out.println("Klient: Znaleziono dane z pomiarów:");
						for (Object el : read.toArray()) {
							System.out.println(el);
						}
						// odbierz pakiet znalezionych danych
						DatagramPacket receivedPacket = new DatagramPacket(buffer2, buffer2.length);
						aSocket.receive(receivedPacket);
						Vector<Request> dataVec = (Vector<Request>) Tools.deserialize(buffer2);
						System.out.println("Klient: Iloœæ pomiarów, z których otrzymano dane: " + dataVec.size());
					} else {
						//lista jest pusta
						System.out.println("Klient: Nie znaleziono szukanych danych");
					}
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
		} finally {
			aSocket.close();
		}
	}
}