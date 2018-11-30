package Server_BD;

/**
 * Servidor que se encarga de darle manejo a las bases de datos. Posee dos funcionalidades :
 * 
 * 1) Guardar en archivo txt lo enviado a traves de tcp
 * 
 * 2) Retornar los datos que se encuentran en el archivo txt indicado
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.util.Scanner;


public class ServerBD {
	
	private ServerSocket socketServer;
	private boolean isAlive;
	public static final int PORT = 6500;
	public static final String RUTA_BD = "BD/baseDatos.txt";
	
	public ServerBD() {
	
		try {
			System.out.println("STATE SERVER BD :: ON ::");
			inicializarSocketServidor();
			isAlive = true;
			HiloAtencionServidores hiloAtencion = new HiloAtencionServidores(this);
			hiloAtencion.start();
		} catch (IOException e) {
			System.out.println("Exception in ServerBD Builder");
		}
		
	}
	
	
	
	public void guardarEnBaseDeDatos(String informacion)  {
		
		
//		try {
//			FileWriter fichero = new FileWriter(RUTA_BD);
//			fichero.write(informacion);
//			fichero.close();
//		} catch (IOException e) {
//			System.out.println("Exception in Guardar en BD");
//		}
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				File texto = new File(RUTA_BD);
				if(!texto.exists()) {
					if(texto.createNewFile()) {
						System.out.println("El fichero se ha creado correctamente"); 
					}
					else {
						System.out.println("No ha podido ser creado el fichero"); 					
				}
			}
				else {
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(texto, true), "UTF8")); 
					out.write(informacion); 
					out.write("\n"); 
					out.close(); 
				}
				
				
		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	public void retornarInfoBD(DataOutputStream out) throws IOException {
		String mensajeDevolver = "";
		try {
			File texto = new File(RUTA_BD);
			Scanner s = new Scanner(texto);
			while (s.hasNextLine()) {
				mensajeDevolver += s.nextLine()+ "\n";
			}
			out.writeUTF(mensajeDevolver);
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error en guardado de TXT BD");
		}
		
		
	}
	
	public void inicializarSocketServidor() throws IOException {
		socketServer = new ServerSocket(PORT);
	}

	public ServerSocket getSocketServer() {
		return socketServer;
	}

	public void setSocketServer(ServerSocket socketServer) {
		this.socketServer = socketServer;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public static void main(String[] args) {
		ServerBD serverBD = new ServerBD();
	}
	
	
}
