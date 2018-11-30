package Servidor;
/**
 * Es el hilo encargado de por cada cliente que acceda a localhost:port , generarle
 * un hiloClientHandler propio para su posterior manejo
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HiloDespliegueAppWeb extends Thread {
	
	Server server;
	
	public HiloDespliegueAppWeb(Server server) {
		
		this.server = server;
	}
	
	public void run() {
		
		while(server.webService) {
			System.out.println(":::Web Server Started:::");
			ServerSocket serverSocket = server.getServerSocketWebService();
			try {
				Socket cliente = serverSocket.accept();
				HiloClientHandler hilo = new HiloClientHandler(cliente, server);
				hilo.start();	
				
			} catch (IOException e) {
				System.out.println("Exception in HiloDespliegueAppWeb");
			}
			
		}
		
	}
	

}
