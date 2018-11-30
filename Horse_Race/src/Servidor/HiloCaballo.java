package Servidor;

/**
 * Hilo que se encarga de mover al caballo como tal
 *
 */

public class HiloCaballo extends Thread {
	
	private Caballo caballo ;
	
	private Carrera carrera;
	
	private boolean llegoALaMeta;

	//8 ideal para 3 minutos
	
	//15 ideal para 2 minutos 
	
	public static final int MOVIMIENTO_X = 15;
	
	public HiloCaballo(Caballo caballo, Carrera carrera) {
		
		this.caballo = caballo;
		this.carrera = carrera;
		llegoALaMeta = false;
	}
	
	
	public void  run() {
		
		while(!llegoALaMeta && caballo.isCorriendo() && !caballo.isGanador()) {
			int nuevaPosicionCaballo = caballo.getPosX() + MOVIMIENTO_X;
			if (nuevaPosicionCaballo >= Carrera.DISTANCIA_PARA_GANAR && !carrera.isHayGanaador()) {
				caballo.setGanador(true);
				caballo.setCorriendo(false);
				carrera.setHayGanaador(true);
				carrera.setGanador(caballo);
			}
			else if (nuevaPosicionCaballo >= Carrera.DISTANCIA_PARA_GANAR) {
				llegoALaMeta = true;
				caballo.setCorriendo(false);
			}
			else {
				//System.out.println("caballo : " + caballo.getIdentificador() + " su pos en x " + nuevaPosicionCaballo);
				caballo.setPosX(nuevaPosicionCaballo);
				try {
					Thread.sleep(generarMilisAleatorio());
				} catch (InterruptedException e) {
					
				}
			}
			
		}

		System.out.println("TERMINO CABALLO : "+ caballo.getIdentificador());
		
	}

	
	private long generarMilisAleatorio() {
		long[] milisAleatorios = new long[6];
		milisAleatorios[0] = 2700;
		milisAleatorios[1] = 2500;
		milisAleatorios[2] = 2000;
		milisAleatorios[3] = 1500;
		milisAleatorios[4] = 1000;
		milisAleatorios[5] = 500;
		int numero = (int) (Math.random() * 5);
		//System.out.println(numero);
		return milisAleatorios[numero];
	}
}
