package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UdpServer {
	 public static void main(String[] args) {
		 	/*
		 	 * Utworzenie pustego Socketa
		 	 */
	    	DatagramSocket aSocket = null;
	      try {
	    	/*
	    	 * aSocket = socket z numerem portu 9876
	    	 */
	    	aSocket = new DatagramSocket(9876);
	        System.out.println(aSocket.getLocalAddress());
	        /*
	         * pusty bufor
	         */
	        byte[] buffer = new byte[1024];
	        /*
	         * wchodzimy w pêtle
	         */
	        while(true) {
	        /*
	         *  nowy datagramPacket w które odebrano zostanie
	         */
	          DatagramPacket request = new DatagramPacket(buffer, buffer.length);
	          System.out.println("Waiting for request...");
	          /*
	           * czeka na komunikat od klienta
	           */
	          aSocket.receive(request);
	          String t = new String(request.getData(), 0, request.getLength());
	          /*
	           * wypisz otrzyman¹ wiadomoœæ
	           */
	          //System.out.println("Received: " + t);
	          /*
	           * wys³anie odpowiedzi na port z którego zosta³ odebrany
	           */
	          DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), 
	          		request.getAddress(), request.getPort());
	          aSocket.send(reply); 
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
