package Cliente;

public class HiloActualizarPanelCarrera extends Thread{
	
	private Gui_Cliente cliente;
	
	public  HiloActualizarPanelCarrera(Gui_Cliente cliente) {

		this.cliente = cliente;
	
	}
	
	public void run() {
		
		while(cliente.estaEnEspera() && cliente.estaEnCarrera()) {
			
			cliente.getCarrera().actualizarMatriz();
			cliente.getCarrera().repaint();
			cliente.getCarrera().updateUI();
		}
		
	}
	
	
	

}
