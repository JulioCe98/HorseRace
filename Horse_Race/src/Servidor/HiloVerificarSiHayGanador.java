package Servidor;

/**
 * Hilo que se encarga de estar verificando si un caballo llego a la meta
 * @author juliocesargaviriajaramillo
 *
 */

public class HiloVerificarSiHayGanador extends Thread{
	
	private Carrera carrera;
	
	public HiloVerificarSiHayGanador(Carrera carrera) {
		
		this.carrera = carrera;
	}
	
	
	public void run() {
		boolean flag = true;	
//		System.out.println("ENTRO AL VERIFICAR GANADOR");
		while (flag) {
			if (carrera.isCorriendo()) {
				Caballo[] caballos = carrera.getCaballos();
				for (int i = 0; i < caballos.length; i++) {
					if(caballos[i].isGanador()) {
						System.out.println(":::::::GANADOR::::::::");
						flag = false;
					}
				}
				
			}
			else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
		}
		
		
	}


	

}
