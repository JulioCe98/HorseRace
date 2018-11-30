package Servidor;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelStatus extends JPanel{
	
	
	private JLabel etiquetaStatus ;
	
	private JTextField textStatus ;
	
	private Gui_Server interfaz;
	
	public PanelStatus(Gui_Server interfaz) {
		
		this.setLayout(new GridLayout(1, 2));
		etiquetaStatus = new JLabel("Status");
		this.add(etiquetaStatus);
		textStatus = new JTextField();
		textStatus.setEditable(false);
		this.add(textStatus);
		
	}

	public JLabel getEtiquetaStatus() {
		return etiquetaStatus;
	}

	public void setEtiquetaStatus(JLabel etiquetaStatus) {
		this.etiquetaStatus = etiquetaStatus;
	}

	public JTextField getTextStatus() {
		return textStatus;
	}

	public void setTextStatus(JTextField textStatus) {
		this.textStatus = textStatus;
	}

	public Gui_Server getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(Gui_Server interfaz) {
		this.interfaz = interfaz;
	}
	
	
	

}
