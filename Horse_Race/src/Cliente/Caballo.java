package Cliente;

/**
 * La clase que se encarga de darle manejo a los caballo. Guarda todos los atributos correspondientes a los caballos de la 
 * carrera
 */

import java.awt.Color;
import java.awt.Image;

public class Caballo {
	
	private int posX ;
	
	private int posY ;
	
	private Image imagen;

	private String identificador;
	
	private Double monto;
	
	private boolean apostoPorMi;
	
	private boolean isGanador ;

	public Caballo(int posX, int posY, Image imagen, String identificador) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.imagen = imagen;
		this.identificador = identificador;
		apostoPorMi = false;
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

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public boolean isApostoPorMi() {
		return apostoPorMi;
	}

	public void setApostoPorMi(boolean apostoPorMi) {
		this.apostoPorMi = apostoPorMi;
	}

	public boolean isGanador() {
		return isGanador;
	}

	public void setGanador(boolean isGanador) {
		this.isGanador = isGanador;
	}

	
	

	
	

}
