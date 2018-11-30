package Cliente;

/**
 * Clase Importante :: Cliente
 * 
 * Es el lado que va a usar cada unos de los clientes de HorseRace. Se encarga de definir los puertos y sockets encargados de
 * conectarse al servidor de carreras para el uso de ciertos servicios. Define tambien las ips y metodos importantes tales como :
 * 
 * 1)Asignar caballos a la lista : crea los 6 caballos en un arreglo para la carrera
 * 
 * 2)Iniciar comunicacion con servidor : Crea la conexion con el servidor de la carera
 * 
 *
 */


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.Socket;
import javax.swing.ImageIcon;

public class Cliente {
	
	public static final int PORT_TCP = 5650;
	
	public static final int PORT_AUDIO = 9786;
	
	public static final String IP = "localhost";
	
	private boolean yaEscogioCaballo;
	
	private boolean enEsperaParaCarrera;
	
	private boolean comenzoCarrera;
	
	private String cedula;

	private Caballo[] caballos;
	
	private String caballoPorElQueAposto;
	
	private double montoApostado;

	private Socket socketCliente;
	
	private MulticastSocket dtSocket;
	
	private HiloAudioUDPClient hiloAudioCliente;
	
	public static final String DIRECCION_MULTICAST = "229.5.6.7";

	public Cliente() {
		
		caballos = new Caballo[6];
		asignarCaballosALaLista();
		cedula = "";
		yaEscogioCaballo = false;
		caballoPorElQueAposto = "";
		montoApostado = 0;
		enEsperaParaCarrera = false;	
		comenzoCarrera = false;
		//posible punto ruptura
		/**
		 * 	hiloAudioCliente = new HiloAudioUDPClient(this);
		    hiloAudioCliente.start();
		 */
	
	}
	
	
	
	private void asignarCaballosALaLista() {
		//en un futuro el servidor le envia los identificadores a los clientes
		String[] nombres = new String[caballos.length];
		nombres[0] = "andromeda";
		nombres[1] = "luna";
		nombres[2] = "marte";
		nombres[3] = "saturno";
		nombres[4] = "tierra";
		nombres[5] = "venus";
		
		int indicadorX = 0;
		int indicadorY = 100;
		
		for (int i = 0; i < caballos.length; i++) {
			if(i==4) {
				indicadorX = 160;
				indicadorY = 250;
			}
			ImageIcon icono = new ImageIcon("IMCliente/" + nombres[i]+".jpg");
			caballos[i] = new Caballo(indicadorX, indicadorY, icono.getImage(), nombres[i]);
			indicadorX += 160;
			
		}
		
	}



	public boolean isYaEscogioCaballo() {
		return yaEscogioCaballo;
	}

	public void setYaEscogioCaballo(boolean yaEscogioCaballo) {
		this.yaEscogioCaballo = yaEscogioCaballo;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}



	public Caballo[] getCaballos() {
		return caballos;
	}



	public void setCaballos(Caballo[] caballos) {
		this.caballos = caballos;
	}



	public String getCaballoPorElQueAposto() {
		return caballoPorElQueAposto;
	}



	public void setCaballoPorElQueAposto(String caballoPorElQueAposto) {
		this.caballoPorElQueAposto = caballoPorElQueAposto;
	}



	public double getMontoApostado() {
		return montoApostado;
	}



	public void setMontoApostado(double montoApostado) {
		this.montoApostado = montoApostado;
	}



	public boolean isEnEspera() {
		return enEsperaParaCarrera;
	}



	public void setEnEspera(boolean enEspera) {
		this.enEsperaParaCarrera = enEspera;
	}



	public void iniciarComunicacionConServidor() throws IOException {

		//CONEXION TCP

		socketCliente = new Socket("localhost", PORT_TCP);
		DataInputStream in = new DataInputStream(socketCliente.getInputStream());
		DataOutputStream out = new DataOutputStream(socketCliente.getOutputStream());
		String apuestaAlServidor = this.cedula + "," + this.caballoPorElQueAposto + "," + this.montoApostado;
		out.writeUTF(apuestaAlServidor);
		String mensaje = in.readUTF();
		System.out.println("MENSAJE OBTENIDO DESDE EL SERVER . " +mensaje);
		if(mensaje.compareToIgnoreCase("CONFIRMADO")==0) {
			enEsperaParaCarrera = true;
		}
		
		HiloAtentoAlMulticast atento = new HiloAtentoAlMulticast(this);
		atento.start();
		
		//FUNCIONALIDAD INTRODUCIDA
		HiloAudioUDPClient hiloAudio = new HiloAudioUDPClient(this);
		hiloAudio.start();
		
	}

	public Socket getSocketCliente() {
		return socketCliente;
	}



	public void setSocketCliente(Socket socketCliente) {
		this.socketCliente = socketCliente;
	}



	public void modificarCaballo(String identificador, String x, String monto, String gano) {
		boolean flag = false;
		for (int i = 0; i < caballos.length && !flag; i++) {
			if (caballos[i].getIdentificador().compareToIgnoreCase(identificador)==0) {
				caballos[i].setPosX(Integer.parseInt(x));
				caballos[i].setMonto(Double.parseDouble(monto));
				if (gano.compareToIgnoreCase("true")==0) {
					caballos[i].setGanador(true);
				}
				flag = true;
			}
		}
		
	}



	public boolean isEnEsperaParaCarrera() {
		return enEsperaParaCarrera;
	}



	public void setEnEsperaParaCarrera(boolean enEsperaParaCarrera) {
		this.enEsperaParaCarrera = enEsperaParaCarrera;
	}



	public boolean isComenzoCarrera() {
		return comenzoCarrera;
	}



	public void setComenzoCarrera(boolean comenzoCarrera) {
		this.comenzoCarrera = comenzoCarrera;
	}



	public void buscarCaballoYAsignarQApostoPorEl(String caballoSeleccionado) {
	
		boolean flag = false;
		for (int i = 0; i < caballos.length && !flag; i++) {
			if (caballos[i].getIdentificador().compareToIgnoreCase(caballoSeleccionado)==0) {
				caballos[i].setApostoPorMi(true);
				flag = true;
			}
		}
	}



	public MulticastSocket getDtSocket() {
		return dtSocket;
	}



	public void setDtSocket(MulticastSocket dtSocket) {
		this.dtSocket = dtSocket;
	}
	
	
	
	
	

}
