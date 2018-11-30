package Servidor;

/**
 * Hilo encargado de prestar el servicio de escucha de las apuestas. Si un cliente se conecta, es
 * porque realizara una apuesta y el servidor le dara su manejo adecuado
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloEsperaRespuestasClientes extends Thread {
	
	private Server server;
	
	public HiloEsperaRespuestasClientes(Server server) {
		
		this.server = server;
		
	}

	public void run() {
		System.out.println("SERVER ESCUCHANDO APUESTAS DE CLIENTES :::");
		
		while(!server.getCarrera().isCorriendo() && !server.getCarrera().isCarreraFinalizada() && server.getdSock().isClosed() == false )
		 {
			try {
				//TCP COMMUNICATION WITH CLIENT
				if (!server.getCarrera().isCarreraFinalizada() && !server.getCarrera().isHayGanaador()) {
					if (!server.getdSock().isClosed()) {
						Socket socket = server.getdSock().accept();
						server.agregarSocketAActivos(socket);
						DataInputStream in = new DataInputStream(socket.getInputStream());
						DataOutputStream out = new DataOutputStream(socket.getOutputStream());
						String mensaje = in.readUTF();
						System.out.println("Cedula : " + mensaje.split(",")[0] + " Caballo : " +  mensaje.split(",")[1] + " Monto : $" + mensaje.split(",")[2] ) ;
						server.agregarClienteParaPersistir(mensaje);
						server.apostarPorCaballo(mensaje);
						out.writeUTF("CONFIRMADO");
					}
					
				}
				else {
					//System.out.println("hola");
				}
			
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}		
	}
		
 }

	
	


}
