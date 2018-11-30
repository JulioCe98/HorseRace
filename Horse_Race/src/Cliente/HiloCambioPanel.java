package Cliente;

import java.awt.BorderLayout;
import java.awt.Panel;

public class HiloCambioPanel extends Thread{
	
	
	private Gui_Cliente cliente;
	
	public HiloCambioPanel(Gui_Cliente cliente) {
		
		this.cliente = cliente;
	
	}
	
	
	public void run() {
		boolean flag = true;
		while(flag) {
			if (cliente.getCliente().isComenzoCarrera()) {
			//	System.out.println("verificando para cambiar de panel");
				cliente.removerYAgregarPanelCarrera();
				flag = false;
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	

}
