package Servidor;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JFrame;

public class Gui_Server extends JFrame {
	
	
	private PanelBanner banner;
	
	private PanelStatus status;
	
	private PanelCarrera carrera;
	
	private Server server;
	
	//private HiloActualizarPanelCarrera hilo;
	
	public Gui_Server() {
		
		try {
			server = new Server();
		} catch (InterruptedException e) {
		} catch (SocketException e) {
		} catch (IOException e) {
		}
		
		this.setLayout(new BorderLayout());
		banner = new PanelBanner(this);
		status = new PanelStatus(this);
		carrera = new PanelCarrera(this);
		this.add(banner, BorderLayout.NORTH);
		this.add(status, BorderLayout.SOUTH);
		this.add(carrera, BorderLayout.CENTER);
		
		this.pack();
		this.setTitle(":::SERVER:::");
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//hilo = new  HiloActualizarPanelCarrera(this);
		//hilo.start();
	}
	
	public static void main(String[] args) {
		
		Gui_Server ventana = new Gui_Server();
		ventana.setVisible(true);
	}

	public boolean isCarreraCorriendo() {
		return server.getCarrera().isCorriendo();
	}

	public Caballo[] getCaballosCarrera() {
		
		return server.getCarrera().getCaballos();
	}

	public PanelBanner getBanner() {
		return banner;
	}

	public void setBanner(PanelBanner banner) {
		this.banner = banner;
	}

	public PanelStatus getStatus() {
		return status;
	}

	public void setStatus(PanelStatus status) {
		this.status = status;
	}

	public PanelCarrera getCarrera() {
		return carrera;
	}

	public void setCarrera(PanelCarrera carrera) {
		this.carrera = carrera;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public boolean carreraFinalizado() {
	
		return server.getCarrera().isCarreraFinalizada();
		}
	
	

}
