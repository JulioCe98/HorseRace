package Servidor;

/**
 * Clase super importante. Es la encargada de manejar todo lo de la carrera , los sockets
 * ,server sockets y puertos que provee como servicios a los clientes. A su vez define las 
 * direcciones ip primordiales para que los clientes puedan acceder a los servicios provistos 
 * por el servidor.
 * 
 * Tambien se encarga de realizar las conexiones al server BD para pedirle los datos persistidos
 * de los clientes.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	private Carrera carrera;
	private ServerSocket dSock;
	public static final int PORT_TCP = 5650;
	public static final int PORT_AUDIO = 9786;
	private HiloEsperaRespuestasClientes hiloEsperarApuestas;
	private HiloTimerApuestas timer;
	private ArrayList<Socket> socketsActivos;
	private MulticastSocket dtSocketAudio ;
	private HiloAudioUDPServer hiloAudioServer;
	public static final String DIRECCION_MULTICAST = "229.5.6.7";
	public ArrayList<String> clientesAPersistir;
	public static final String LOCAL_HOST = "localhost";
	public static final int PORT_BD = 6500;
	public boolean webService ;
	public ServerSocket serverSocketWebService;
	public static final int PORT_WEB_SERVICE = 7000;
	private HiloDespliegueAppWeb hiloDespliegueAppWeb;
	
	public Server() throws InterruptedException, IOException {
		
		carrera = new Carrera();
		dSock = new ServerSocket(PORT_TCP);
		hiloEsperarApuestas = new HiloEsperaRespuestasClientes(this);
		hiloEsperarApuestas.start();
		timer = new HiloTimerApuestas(this);
		timer.start();
		socketsActivos = new ArrayList<>();
		clientesAPersistir = new ArrayList<>();
		webService = true;
		try {
			serverSocketWebService = new ServerSocket(PORT_WEB_SERVICE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
		hiloDespliegueAppWeb = new HiloDespliegueAppWeb(this);
		hiloDespliegueAppWeb.start();
		//posible punto ruptura
		/**
		 * 	hiloAudioServer = new HiloAudioUDPServer(this);
		    hiloAudioServer.start();
		 */	
	}

	

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}


	public HiloEsperaRespuestasClientes getHiloEsperarApuestas() {
		return hiloEsperarApuestas;
	}



	public void setHiloEsperarApuestas(HiloEsperaRespuestasClientes hiloEsperarApuestas) {
		this.hiloEsperarApuestas = hiloEsperarApuestas;
	}



	public ServerSocket getdSock() {
		return dSock;
	}



	public void setdSock(ServerSocket dSock) {
		this.dSock = dSock;
	}



	public void agregarSocketAActivos(Socket dtSocket) {
		if (dtSocket != null) {
			socketsActivos.add(dtSocket);	
		}
		
	}



	public void enviarMulticastDeInicioALosClientes() throws IOException {
		String mensaje = "CARRERA";
		for (int i = 0; i < socketsActivos.size(); i++) {
			DataOutputStream out = new DataOutputStream(socketsActivos.get(i).getOutputStream());
			out.writeUTF(mensaje);
//			System.out.println("ENVIO MENSAJE DE COMENZAR A LOS CLIENTES ::::");
			
			
		}
		
	}



	public ArrayList<Socket> getSocketsActivos() {
		return socketsActivos;
	}



	public void setSocketsActivos(ArrayList<Socket> socketsActivos) {
		this.socketsActivos = socketsActivos;
	}



	public void cerrarSockets() throws IOException {
		
//		System.out.println("carrera corriendo : " + carrera.isCorriendo());
//		System.out.println("carrera finalizo : " + carrera.isCarreraFinalizada());
		mostrarApuestas();
		String mensaje = "CLOSE";
		for (int i = 0; i < socketsActivos.size(); i++) {
			DataOutputStream out = new DataOutputStream(socketsActivos.get(i).getOutputStream());
			out.writeUTF(mensaje);
			socketsActivos.get(i).close();
		}
		//dSock.close();
	}



	private void mostrarApuestas() {
		
		String mensaje = "";
		String personas = "";
		
		for (int i = 0; i < carrera.getCaballos().length; i++) {
			mensaje += ":::::::::\n:::::::::\n";
			mensaje += "Nombre Caballo : " + carrera.getCaballos()[i].getIdentificador() + "\n";
			mensaje += "Monto Caballo  : " + carrera.getCaballos()[i].getMontoTotal() + "\n";
			mensaje += "Gano? : " + carrera.getCaballos()[i].isGanador() + "\n";
			mensaje += "Personas que apostaron por el : \n{";
			int contador = 0;
			for (int j = 0; j < carrera.getCaballos()[i].getPersonasQueApostaron().size(); j++) {
				personas += carrera.getCaballos()[i].getPersonasQueApostaron().get(j) + "\n";
				contador ++;
			}
			mensaje += personas + "} \n";
			mensaje += "Numero de apuestas realizadas a este caballo : " + contador;
			mensaje += "\n:::::::::\n:::::::::\n";
			mensaje += "\n\n";
			System.out.println(mensaje);
			mensaje = "";
			personas = "";
			contador = 0;
		}
		
		agregarAClientesAPersistirSiGano();
		establecerConexConServerBD();
		
	}



	private void establecerConexConServerBD() {
		
		try {
			Socket socket = new Socket(LOCAL_HOST, PORT_BD);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			String mensaje = "GUARDAR;";
			System.out.println("Chequeo Method Establecer Conex con Server BD");
			for (int i = 0; i < clientesAPersistir.size(); i++) {
				mensaje += clientesAPersistir.get(i) + "\n";
				System.out.println(clientesAPersistir.get(i));
			}
			out.writeUTF(mensaje);
			String mensajeObtenido = in.readUTF();
			System.out.println("Mensaje Obtenido por el Servidor BD al GUARDAR : " + mensajeObtenido);
			socket.close();
			
		} catch (IOException e) {
			System.out.println("Exception in ConexServerBD");
		}	
	}



	private void agregarAClientesAPersistirSiGano() {
		
		String caballoGanador = "";
		boolean flag = false;
		for (int i = 0; i < carrera.getCaballos().length && !flag; i++) {
			if(carrera.getCaballos()[i].isGanador()) {
				caballoGanador = carrera.getCaballos()[i].getIdentificador();
				flag = true;
			}
		}
		for (int i = 0; i < clientesAPersistir.size(); i++) {
			String cliente = clientesAPersistir.get(i);
			if (cliente.split(",")[1].compareToIgnoreCase(caballoGanador)==0) {
				String clienteModificado = "";
				clienteModificado = cliente.split(",")[0] + "," + cliente.split(",")[1] + "," + cliente.split(",")[2] + "," + "SI";
				clientesAPersistir.remove(i);
				clientesAPersistir.add(clienteModificado);
			}
		}
		
	}



	public void apostarPorCaballo(String mensaje) {
		
		String[] datos = mensaje.split(",");
		
		for (int i = 0; i < carrera.getCaballos().length; i++) {
			
			if (carrera.getCaballos()[i].getIdentificador().compareToIgnoreCase(datos[1])==0) {
				double monto = carrera.getCaballos()[i].getMontoTotal();
				carrera.getCaballos()[i].setMontoTotal(monto + Double.parseDouble(datos[2]));
				carrera.getCaballos()[i].getPersonasQueApostaron().add(datos[0]);
			}
		}
		
	}

	public MulticastSocket getDtSocketAudio() {
		return dtSocketAudio;
	}



	public void setDtSocketAudio(MulticastSocket dtSocketAudio) {
		this.dtSocketAudio = dtSocketAudio;
	}



	public void agregarClienteParaPersistir(String mensaje) {
		
		//System.out.println("Cedula : " + mensaje.split(",")[0] + " Caballo : " +  mensaje.split(",")[1] + " Monto : $" + mensaje.split(",")[2] ) ;
		String guardar = mensaje.split(",")[0] + "," + mensaje.split(",")[1] + "," + mensaje.split(",")[2] + "," + "NO";
		clientesAPersistir.add(guardar);
		
		
	}



	public ArrayList<String> getClientesAPersistir() {
		return clientesAPersistir;
	}



	public void setClientesAPersistir(ArrayList<String> clientesAPersistir) {
		this.clientesAPersistir = clientesAPersistir;
	}



	public boolean isWebService() {
		return webService;
	}



	public void setWebService(boolean webService) {
		this.webService = webService;
	}



	public ServerSocket getServerSocketWebService() {
		return serverSocketWebService;
	}



	public void setServerSocketWebService(ServerSocket serverSocketWebService) {
		this.serverSocketWebService = serverSocketWebService;
	}



	public String pedirDatosAlServerBD(String cedula) {
		String datosObtenidos = "";
		try {
			Socket socket = new Socket(LOCAL_HOST, PORT_BD);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			String mensaje = "PEDIR_DATOS;" + cedula;
			System.out.println("Chequeo Method pedir Datos Al Server BD");	
			out.writeUTF(mensaje);
			String mensajeObtenido = in.readUTF();
			datosObtenidos = mensajeObtenido;
			System.out.println("Mensaje Obtenido por el Servidor BD al PEDIR DATOS : " + mensajeObtenido);
			socket.close();	
		} catch (IOException e) {
			System.out.println("Exception in ConexServerBD");
		}	
		return datosObtenidos;
	}



	

}

