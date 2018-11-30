package Servidor;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelBanner extends JPanel{

	private JLabel imagen;
	
	private Gui_Server interfaz;
	
	public PanelBanner(Gui_Server interfaz) {
		
		this.setLayout(new GridLayout(1, 1));
		this.interfaz = interfaz;
		imagen = new JLabel("");
		ImageIcon icono = new ImageIcon("IMServer/banner.jpg");
		imagen.setIcon(icono);
		this.add(imagen);
		
		
	}

	public JLabel getImagen() {
		return imagen;
	}

	public void setImagen(JLabel imagen) {
		this.imagen = imagen;
	}
	
	
	
	
	
}

