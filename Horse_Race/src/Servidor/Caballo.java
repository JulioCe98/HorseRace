package Servidor;

/**
 * La clase que se encarga de darle manejo a los caballos. Guarda todos los atributos correspondientes a los caballos de la 
 * carrera. Ademas permite saber si el caballo gano, el numero de personas que apostaron por el
 * y el monto total recaudado por este.
 */

import java.awt.Color;
import java.util.ArrayList;

public class Caballo {
	
	private int posX ;
	
	private int posY ;

	private String identificador;
	
	private double montoTotal;
	
	private Color color;
	
	private ArrayList<String> personasQueApostaron;
	
	private boolean isGanador;
	
	private boolean isCorriendo;

	public Caballo(int posX, int posY, String identificador, Color color, boolean isCorriendo, boolean isGanador) {
		super();
		this.color = color;
		this.posX = posX;
		this.posY = posY;
		this.identificador = identificador;
		montoTotal = 0;
		personasQueApostaron = new ArrayList<>();
		this.isCorriendo = isCorriendo;
		isGanador = false;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ArrayList<String> getPersonasQueApostaron() {
		return personasQueApostaron;
	}

	public void setPersonasQueApostaron(ArrayList<String> personasQueApostaron) {
		this.personasQueApostaron = personasQueApostaron;
	}

	public boolean isCorriendo() {
		return isCorriendo;
	}

	public void setCorriendo(boolean isCorriendo) {
		this.isCorriendo = isCorriendo;
	}

	public boolean isGanador() {
		return isGanador;
	}

	public void setGanador(boolean isGanador) {
		this.isGanador = isGanador;
	}

	
	

	
	

}
