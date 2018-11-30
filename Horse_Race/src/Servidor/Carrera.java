package Servidor;

/**
 * Clase importante. Se encarga de generar la carrera. Permite definir la distancia de la carrera
 * , los minutos tanto de apuesta como de la carrera, el estado en que se encuentra
 */

import java.awt.Color;

public class Carrera {
	
	
	private  Caballo[] caballos;
	
	public static final int DISTANCIA_PARA_GANAR = 560;
	
	public static final int MINUTOS_APUESTA = 1;
	
	public static final int  MINUTOS_CARRERA = 2;
	
	public static final String  APOSTANDO = "Recibiendo Depositos!";
	
	public static final String  EN_MARCHA = "La carrera ha comenzado!";
	
	public static final String  CARRERA_FINALIZADA = "La carrera ha terminado!";
	
	
	private boolean isCorriendo;
	
	private Caballo ganador;
	
	private HiloCaballo[] hilos ;
	
	private String status;
	
	private boolean hayGanaador;
	
	private boolean carreraFinalizada ;
	
	public Carrera() {
		
		caballos = new Caballo[6];
		inicializarArregloCaballos();
		isCorriendo = false;
		ganador = null;
		status = APOSTANDO;
		carreraFinalizada = false;
	}

	private void inicializarArregloCaballos() {
		
		int y = 50;
		String[] nombres = new String[caballos.length];
		nombres[0] = "andromeda";
		nombres[1] = "luna";
		nombres[2] = "marte";
		nombres[3] = "saturno";
		nombres[4] = "tierra";
		nombres[5] = "venus";
		
		Color[] color = new Color[caballos.length];
		color[0] = Color.YELLOW;
		color[1] = Color.GREEN;
		color[2] = Color.BLUE;
		color[3] = Color.ORANGE;
		color[4] = Color.GRAY;
		color[5] = Color.MAGENTA;
		
		
		for (int i = 0; i < caballos.length; i++) {
			caballos[i] = new Caballo(0, y, nombres[i], color[i], false,false); 
			y += 50;
		}
		
		
	}

	public Caballo[] getCaballos() {
		return caballos;
	}

	public void setCaballos(Caballo[] caballos) {
		this.caballos = caballos;
	}

	public boolean isCorriendo() {
		return isCorriendo;
	}

	public void setCorriendo(boolean isCorriendo) {
		this.isCorriendo = isCorriendo;
	}
	
	public void iniciarCarrera() throws InterruptedException {
		
		isCorriendo = true;
		hilos = new HiloCaballo[6];
		status = EN_MARCHA;
		for (int i = 0; i < caballos.length; i++) {
			
			caballos[i].setCorriendo(true);
			HiloCaballo hilo = new HiloCaballo(caballos[i], this);
			hilos[i] = hilo;
			hilo.start();
	
		}
		
		
	}

	public void agregarParticipanteACaballo(String nombre, String caballo, String montoEnviado) {
		boolean flag = false;
		for (int i = 0; i < caballos.length && !flag; i++) {
			if (caballos[i].getIdentificador().compareToIgnoreCase(caballo)==0) {
				double monto = caballos[i].getMontoTotal();
				caballos[i].setMontoTotal(monto + Double.parseDouble(montoEnviado));
				caballos[i].getPersonasQueApostaron().add(nombre);
				flag = true;
			}
		}
		
	}

	public Caballo getGanador() {
		return ganador;
	}

	public void setGanador(Caballo ganador) {
		this.ganador = ganador;
	}

	public HiloCaballo[] getHilos() {
		return hilos;
	}

	public void setHilos(HiloCaballo[] hilos) {
		this.hilos = hilos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isCarreraFinalizada() {
		return carreraFinalizada;
	}

	public void setCarreraFinalizada(boolean carreraFinalizada) {
		this.carreraFinalizada = carreraFinalizada;
	}

	public boolean isHayGanaador() {
		return hayGanaador;
	}

	public void setHayGanaador(boolean hayGanaador) {
		this.hayGanaador = hayGanaador;
	}
	
	
	

}
