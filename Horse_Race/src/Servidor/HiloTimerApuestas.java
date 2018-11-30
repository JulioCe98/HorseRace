package Servidor;

/**
 * Hilo encargado de asignar el tiempo de las apuestas al servidor, una vez culminado lo cierra
 *  
 */

import java.io.IOException;
import java.util.Calendar;

public class HiloTimerApuestas extends Thread{
	
	private  Calendar calendario;
	private  int minutosInicio;
	private  Server server;
	
	public HiloTimerApuestas(Server server) {
		calendario = Calendar.getInstance();
		minutosInicio = calendario.get(Calendar.MINUTE);
		this.server = server;
	}

	public void run() {
		boolean flag = true;
		while(flag) {
			 Calendar calendario1 = Calendar.getInstance();
			 int minutosFinal = calendario1.get(Calendar.MINUTE);
			 if(minutosFinal - minutosInicio == Carrera.MINUTOS_APUESTA) {
				 flag = false;
				 try {
					 server.getCarrera().iniciarCarrera();
					 server.enviarMulticastDeInicioALosClientes();
					 HiloVerificarSiHayGanador verificar = new HiloVerificarSiHayGanador(server.getCarrera());
					 verificar.start();
					 HiloTimerCarrera timerCarrera = new HiloTimerCarrera(server);
					 timerCarrera.start();
					 HiloMulticastClientes multicast = new HiloMulticastClientes(server);
					 multicast.start();
				} catch (InterruptedException e) {
				} catch (IOException e) {
				}
				
			 }		
			 else {
				 try {
					this.sleep(1);
				} catch (InterruptedException e) {
				}
			} 
		}
		 
	}

}
