package Servidor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelCarrera extends JPanel {

	private Gui_Server interfaz;
	
	public PanelCarrera(Gui_Server interfaz) {
		
		this.setLayout(new GridLayout(1, 1));
		this.setBackground(Color.WHITE);
		setPreferredSize((new Dimension(350,350)));
		this.interfaz = interfaz;
	}
	
	public void paintComponent(Graphics g){
		
	Graphics2D grafico = (Graphics2D)g;
	
	if( !interfaz.carreraFinalizado() && interfaz.isCarreraCorriendo() == false) {
		
		grafico.drawImage(new ImageIcon("IMServer/apostando.jpg").getImage(), 0, 0, null);
	}
	else {
		
		Caballo[] caballos = interfaz.getCaballosCarrera();
		for (int i = 0; i < caballos.length; i++) {
	//		System.out.println("Caballo : " + caballos[i].getIdentificador() + " en x : " + caballos[i].getPosX() 
	//				+ " en y : " + caballos[i].getPosY());
			Color color = caballos[i].getColor();
			grafico.setColor(color);
			grafico.drawImage(new ImageIcon("IMServer/flag.png").getImage(), 0, caballos[i].getPosY()-35, null);
			grafico.drawImage(new ImageIcon("IMServer/finish.png").getImage(),Carrera.DISTANCIA_PARA_GANAR,caballos[i].getPosY()-35, null);
			grafico.drawLine(0, caballos[i].getPosY(), 600, caballos[i].getPosY());
			grafico.fillOval(caballos[i].getPosX(), caballos[i].getPosY(), 25, 25);
		}
	}
	
	this.repaint();
	this.updateUI();
	interfaz.getStatus().getTextStatus().setText(interfaz.getServer().getCarrera().getStatus());
	}
		
	
	
}
