package Cliente;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Gui_Cliente extends JFrame {
	
	private PanelBanner banner;
	
	private  PanelEleccionCaballo eleccion;
	
	private Cliente cliente;
	
	private PanelCarrera carrera;
	

	public Gui_Cliente() {
		
		
		cliente = new Cliente();
		this.setLayout(new BorderLayout());
		this.setTitle(":::Cliente:::");
		banner = new PanelBanner(this);
		this.add(banner, BorderLayout.NORTH);
		eleccion = new PanelEleccionCaballo(this);
		this.add(eleccion, BorderLayout.CENTER);
		this.setResizable(false);
		
		HiloCambioPanel cambio = new HiloCambioPanel(this);
		cambio.start();
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	/**
	public void agregarPanelEleccion() {
		
		boolean ingresoBien = false;
		while(!ingresoBien) {
			String cedula = JOptionPane.showInputDialog("Ingrese su Numero de Cedula");
			if(cedula.length() > 9) {
				cliente = new Cliente();
				cliente.setCedula(cedula);
				cliente.setYaEscogioCaballo(false);
				ingresoBien = true;
				eleccion = new PanelEleccionCaballo(this);
				this.add(eleccion, BorderLayout.CENTER);
			}
			else {
				JOptionPane.showMessageDialog(this, "Ingrese una cedula VALIDA, debe contener mas de 10 caracteres!!");
			}
		}
		this.pack();

	}
*/






	public static void main(String[] args) {
		
		Gui_Cliente ventana = new Gui_Cliente();
		ventana.setVisible(true);
	}







	public boolean escogioCaballo() {
		
		boolean escogio = cliente.isYaEscogioCaballo();
		return escogio;
	}







	public Caballo[] getListaCaballos() {
		
		Caballo[] lista = cliente.getCaballos();

		return lista;
	}
	public void escogerCaballoEIniciarCarrera(int x , int y) {
		
		String caballoSeleccionado = verificarCaballo(x,y);
		if(caballoSeleccionado != "") {
			JOptionPane.showMessageDialog(this, "Su caballo selecionado fue : \n ::"+ caballoSeleccionado + "::");
			String cedula = JOptionPane.showInputDialog("Ingrese su Cedula , por favor!");
			if (cedula != null && cedula.length() >= 9) {
				cliente.setCedula(cedula);
				
				try {
					double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a Apostar!"));
					cliente.setMontoApostado(monto);
					cliente.setEnEspera(true);
					cliente.setYaEscogioCaballo(true);
					cliente.setCaballoPorElQueAposto(caballoSeleccionado);
					cliente.buscarCaballoYAsignarQApostoPorEl(caballoSeleccionado);
					cliente.iniciarComunicacionConServidor();
					eleccion.updateUI();
					eleccion.repaint();
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "Monto no valido");
				}
				
				
			}
			else {
				JOptionPane.showMessageDialog(this, "Cedula Incorrecta");
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Por favor seleccione un caballo valido!");
		}
		
		
		
		
		
	}
	private String verificarCaballo(int x, int y) {
		
		String caballo = "";
		if(x >= 0 && x <= 145 && y >= 102 && y <= 252) {
			caballo = "andromeda";
		}
		else if(x >= 160 && x <= 309 && y >= 101 && y <= 201) {
			caballo = "luna";
		}
		else if(x >= 320 && x <= 470 && y >= 103 && y <= 210) {
			caballo = "marte";
		}
		else if(x >= 477 && x <= 597 && y >= 102 && y <= 249) {
			caballo = "saturno";
		}
		else if(x >= 160 && x <= 311 && y >= 250 && y <= 359) {
			caballo = "tierra";
		}
		else if(x >= 320 && x <= 471 && y >= 250 && y <= 349) {
			caballo = "venus";
		}
		
		
		return caballo;
	}
	
	
	public boolean estaEnEspera() {
		
		return cliente.isEnEspera();
	}
	public boolean estaEnCarrera() {
		
		return cliente.isComenzoCarrera();
	}
	public PanelBanner getBanner() {
		return banner;
	}
	public void setBanner(PanelBanner banner) {
		this.banner = banner;
	}
	public PanelEleccionCaballo getEleccion() {
		return eleccion;
	}
	public void setEleccion(PanelEleccionCaballo eleccion) {
		this.eleccion = eleccion;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public void removerYAgregarPanelCarrera() {
	
//		System.out.println("ENTRO A REMOVER EL PANEL ELECCION");
		this.remove(eleccion);
		carrera = new PanelCarrera(this);
		this.add(carrera, BorderLayout.CENTER);
		carrera.repaint();
		carrera.updateUI();
		this.repaint();
		HiloActualizarPanelCarrera actualizarCarrera = new HiloActualizarPanelCarrera(this);
		actualizarCarrera.start();
		
		
	}
	public PanelCarrera getCarrera() {
		return carrera;
	}
	public void setCarrera(PanelCarrera carrera) {
		this.carrera = carrera;
	}
	
	
	

}
