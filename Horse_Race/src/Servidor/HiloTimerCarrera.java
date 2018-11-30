package Servidor;

/**
 * hilo encargado de asignar el tiempo de la carrera al server. Una vez cerrado
 * la carrera termina y muestra las apuestas en el servidor
 */

import java.io.IOException;
import java.util.Calendar;

public class HiloTimerCarrera extends Thread{
	
	private  Calendar calendario;
	private  int minutosInicio;
	private  Server server;
	
	public HiloTimerCarrera(Server server) {
		calendario = Calendar.getInstance();
		minutosInicio = calendario.get(Calendar.MINUTE);
		this.server = server;
	}

	public void run() {
		boolean flag = true;
//		System.out.println("ENTRO AL TIMER CARRERA");
		while(flag) {
			 Calendar calendario1 = Calendar.getInstance();
			 int minutosFinal = calendario1.get(Calendar.MINUTE);
			 if(minutosFinal - minutosInicio == Carrera.MINUTOS_CARRERA) {
				 server.getCarrera().setCarreraFinalizada(true);
				 flag = false;
				 try {
						server.cerrarSockets();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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

