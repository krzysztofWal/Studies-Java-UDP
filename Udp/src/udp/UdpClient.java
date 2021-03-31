package udp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab_1.TimeHistory;
import lab_1.Packet;

public class UdpClient {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		/*
		 * Tworzenie pustego Socketa i skanera
		 */
		DatagramSocket aSocket = null;
		Scanner scanner = null;
		try {
			/*
			 * nie przekazano argumentu 
			 */
			
			if (args.length < 1) {
				System.out.println("Usage: UDPClient <server host name>");
				System.exit(-1);
			}
			
			/*
			 * pusty bufor
			 */
			byte[] buffer = new byte[1024];
			/*
			 * adres internetowy po nazwie hosta przekazanego w argumencie
			 */
			InetAddress aHost = InetAddress.getByName(args[0]);
			/*
			 * port serwera
			 */
			int serverPort = 9876;
			/*
			 * a socket to nowy DatagramSocket() u�ywaj�cy dowolnego portu
			 */
			aSocket = new DatagramSocket();
			//scanner = new Scanner(System.in);
			//String line = "";
			
			Double[] doubleData = {1.3, 1.4, 1.5, 1.4, 1.3};
			TimeHistory<Double[]> packet = new TimeHistory("First device",
					"Not yet devised",
					12032012,
					1,
					"Volts",
					0.3,
					doubleData,
					0.5
					);

			byte[] data = Tools.serialize(packet);
			
			//while (true) {
				/*
				 * wpisz swoj� wiadomo��
				 */
			//	System.out.println("Enter your message: ");
				/* 
				 * wpisano linijk�
				 */
			//	if (scanner.hasNextLine())
			//		line = scanner.nextLine();
				/*
				 * utworzenie nowego pakietu i wys�anie go do wcze�niej przypisanego hosta i nru portu (hardcoded)
				 */
			//	DatagramPacket request = new DatagramPacket(line.getBytes(), line.length(), aHost, serverPort);
			//	aSocket.send(request);
				
				DatagramPacket toSend = new DatagramPacket(data, data.length, aHost, serverPort);
				aSocket.send(toSend);
				/*
				 * odbierz odpowiedz i ja wyswietl
				 */
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(reply);
				try {
				Packet read = (Packet)Tools.deserialize(reply.getData());
				System.out.println(read);
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(UdpClient.class.getName()).log(Level.FINEST, null, ex);
				}
				
			//	System.out.println("Reply: " + new String(reply.getData(), 0, reply.getLength()));
			//}
		} catch (SocketException ex) {
			Logger.getLogger(UdpClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnknownHostException ex) {
			Logger.getLogger(UdpClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(UdpClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			aSocket.close();
		//	scanner.close();
		}
	}
}