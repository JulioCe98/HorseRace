package Cliente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelCarrera extends JPanel{
	
	private Gui_Cliente interfaz;
	
	private JComponent[][] matrizComponentesVisualizacion;
	
	public PanelCarrera(Gui_Cliente interfaz){
		
		this.interfaz = interfaz;
		this.setBackground(Color.WHITE);
		setPreferredSize((new Dimension(600,450)));
		definirMatrizComponentes();
		
	}

	private void definirMatrizComponentes() {
		
		this.setLayout(new GridLayout(6, 5));
		matrizComponentesVisualizacion = new JComponent[6][5];
		for (int i = 0; i < matrizComponentesVisualizacion.length; i++) {
			for (int j = 0; j < matrizComponentesVisualizacion[0].length; j++) {
				
				switch (j) {
				case 0:
					JLabel label = new JLabel("");
					matrizComponentesVisualizacion[i][j] = label;
					this.add(matrizComponentesVisualizacion[i][j]);
					break;
					
				default :
					JTextField textField = new JTextField();
					textField.setEnabled(false);
					textField.setEditable(false);
					matrizComponentesVisualizacion[i][j] = textField;
					this.add(matrizComponentesVisualizacion[i][j]);
					break;

			
				}
				
			}
		}
		
		
		
	}
	
	public void actualizarMatriz() {
		
		Caballo[] caballos = interfaz.getCliente().getCaballos();
		for (int i = 0; i < matrizComponentesVisualizacion.length; i++) {
			for (int j = 0; j < matrizComponentesVisualizacion[0].length; j++) {
				Image imagen = caballos[i].getImagen();
				String identificador = caballos[i].getIdentificador();
				int posicion = caballos[i].getPosX();
				double monto = caballos[i].getMonto();
				boolean ganador = caballos[i].isGanador();
				
				switch (j) {
				case 0:
					JLabel label = (JLabel) matrizComponentesVisualizacion[i][j];
					label.setIcon(new ImageIcon(imagen));
					matrizComponentesVisualizacion[i][j] = label;
					break;
					
				case 1:
					JTextField textField = (JTextField) matrizComponentesVisualizacion[i][j];
					textField.setText(identificador);
					if (caballos[i].isApostoPorMi()) {
						textField.setBackground(Color.YELLOW);
					}
					matrizComponentesVisualizacion[i][j] = textField;
					break;
					
				case 2:
					JTextField textField1 = (JTextField) matrizComponentesVisualizacion[i][j];
					textField1.setText(posicion + " mts");
					if (caballos[i].isApostoPorMi()) {
						textField1.setBackground(Color.YELLOW);
					}
					matrizComponentesVisualizacion[i][j] = textField1;
					
					break;

				case 3:
					JTextField textField2 = (JTextField) matrizComponentesVisualizacion[i][j];
					textField2.setText(monto + "");
					if (caballos[i].isApostoPorMi()) {
						textField2.setBackground(Color.YELLOW);
					}
					matrizComponentesVisualizacion[i][j] = textField2;
					break;

			    case 4:
			    	JTextField textField5 = (JTextField) matrizComponentesVisualizacion[i][j];
			    	if (ganador == true) {
			    		textField5.setText("Winner!");
					}
			    	else {
			    		textField5.setText("-----");
			    	}
			    	if (caballos[i].isApostoPorMi()) {
			    		textField5.setBackground(Color.YELLOW);
			    	}
			    	matrizComponentesVisualizacion[i][j] = textField5;
			    	break;
			}
		}
	}
}
	
		
 }

