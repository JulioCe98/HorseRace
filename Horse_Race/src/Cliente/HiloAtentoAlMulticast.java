package Cliente;

/**
 * Esta clase se encarga de proveer un servicio especializado en escuchar el multicast proveniente del server
 * es decir, se encarga de darle manejo a los datos de la carrera, de tal forma que puedan ser mostradas al usuario
 * en una interfaz amigable
 * 
 */
import java.io.DataInputStream;


public class HiloAtentoAlMulticast extends Thread {
	
	private Cliente cliente ;
	
	public HiloAtentoAlMulticast(Cliente cliente) {
		
		this.cliente = cliente;
		
	}
	
	public void run() {
		
		while(cliente.isEnEspera() || cliente.isComenzoCarrera()) {
			try {
//				System.out.println("CLIENTE ESPERANDO PARA ENTRAR A PINTAR LA CARRERA :::");
				DataInputStream in = new DataInputStream(cliente.getSocketCliente().getInputStream());
				String mensaje = in.readUTF();
				if (mensaje.compareToIgnoreCase("CARRERA")==0) {
					cliente.setComenzoCarrera(true);
					Thread.sleep(1);
				}
				else if (mensaje.compareToIgnoreCase("CLOSE")==0) {
					cliente.getSocketCliente().close();
				}
				else {
					String[] ndatos = mensaje.split(";");
					for (int i = 0; i < ndatos.length; i++) {
						//IDENTIFICADOR, NOMBRE, POSX, MONTO, GANO
//						System.out.println("Caballo : " + ndatos[i].split(",")[0] + " x : " + ndatos[i].split(",")[1] + " MONTO: " + ndatos[i].split(",")[2] + "GANO: " + ndatos[i].split(",")[3]);
						cliente.modificarCaballo(ndatos[i].split(",")[0],ndatos[i].split(",")[1],ndatos[i].split(",")[2],ndatos[i].split(",")[3]);
//						System.out.println("server esta enviando las coordenadas de los caballos");
						Thread.sleep(1);
					}
					
					
				
				}
			
				
			} catch (Exception e) {

			}	
		}
		
	}

}
