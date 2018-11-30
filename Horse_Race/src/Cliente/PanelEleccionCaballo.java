package Cliente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class PanelEleccionCaballo extends JPanel implements MouseListener{

	private Gui_Cliente interfaz;
	private JLabel gif;
	private boolean eliminioGif;
	
	public PanelEleccionCaballo(Gui_Cliente interfaz){
		
		this.interfaz = interfaz;
		this.setLayout(new GridLayout(1, 1));
		this.setBackground(Color.WHITE);
		setPreferredSize((new Dimension(350,350)));
		this.addMouseListener(this);
		eliminioGif = false;
		
	}
	
	public void paintComponent(Graphics g){
		
		Graphics2D grafico = (Graphics2D)g;
		if(!interfaz.escogioCaballo() ) {
			grafico.drawImage(new ImageIcon("IMCliente/fondo.jpg").getImage(), 0, 0, null);
			grafico.drawImage(new ImageIcon("IMCliente/mensaje.png").getImage(), 100, -50, null);
			Caballo[] listaCaballos = interfaz.getListaCaballos();
			for (int i = 0; i < listaCaballos.length; i++) {
				grafico.drawImage(listaCaballos[i].getImagen(), listaCaballos[i].getPosX(), listaCaballos[i].getPosY(), null);
			}
			
			
		}
		else if (interfaz.estaEnEspera() && !interfaz.estaEnCarrera()) {
			grafico.setBackground(Color.WHITE);
			insertarGifCargando();
		}

	}
		
	

	private void insertarGifCargando() {
		gif = new JLabel("");
		gif.setSize(600,350);
		gif.setIcon(new ImageIcon("IMCliente/loading.gif"));
		this.repaint();
		this.add(gif);
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		//System.out.println("posx : " + x + " posy : " +  y);
		if(!interfaz.getCliente().isYaEscogioCaballo()) {
			interfaz.escogerCaballoEIniciarCarrera(x,y);
		}
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
