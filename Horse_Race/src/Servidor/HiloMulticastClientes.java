package Servidor;

/**
 * Hilo encargado de generar el multicast (que no es multicast ) a todos los clientes
 * conectados, para que ellos vean el estado real y en vivo de la carrera
 */

import java.io.DataOutputStream;
import java.io.IOException;


public class HiloMulticastClientes extends Thread{
	
	private Server server;
	
	public HiloMulticastClientes(Server server) {
		
		this.server = server;
		
	}
	
	public void run() {
		
		//cuando ya pare deberia cerrar los sockets
		while(server.getCarrera().isCorriendo() && !server.getCarrera().isCarreraFinalizada() && server.getdSock().isClosed() == false) {
		
			String[] datos = new String[6];
			for (int i = 0; i < server.getCarrera().getCaballos().length; i++) {
				//NOMBRE, X, MONTON, SI GANO
				datos[i] = server.getCarrera().getCaballos()[i].getIdentificador()  + "," + server.getCarrera().getCaballos()[i].getPosX() + "," + server.getCarrera().getCaballos()[i].getMontoTotal() + "," + server.getCarrera().getCaballos()[i].isGanador() ;
				
			}
			String mensaje = "";
			for (int i = 0; i < datos.length; i++) {
				mensaje += datos[i] + ";";
			}
			try {
				for (int j = 0; j < server.getSocketsActivos().size(); j++) {
					DataOutputStream out = new DataOutputStream(server.getSocketsActivos().get(j).getOutputStream());
					out.writeUTF(mensaje);
					//System.out.println("El SERVER esta enviando datos a los clientes ::");
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		
	}

}
