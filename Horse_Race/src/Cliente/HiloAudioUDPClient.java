package Cliente;

/**
 * Es el hilo que se encarga  de escuchar el multicast del server respecto al audio en vivo
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class HiloAudioUDPClient extends Thread{

	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;	
	
	private Cliente cliente;

	public HiloAudioUDPClient(Cliente cliente) {
		
		this.cliente = cliente;
	}
	
	public void run() {
		/**
		try {
			cliente.setDtSocket(new MulticastSocket(cliente.PORT_AUDIO));
			InetAddress inetAddress = InetAddress.getByName(cliente.DIRECCION_MULTICAST);
			cliente.getDtSocket().joinGroup(inetAddress);
			initiateAudio();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	
	private AudioFormat getAudioFormat() {
		float sampleRate = 16000F;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	private void playAudio() {
		byte[] buffer = new byte[10000];
		try {
			int count;
			while ((count = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
				if (count > 0) {
					sourceDataLine.write(buffer, 0, count);
				}
			}
		} catch (Exception e) {
			// Handle exceptions
		}
	}

	private void initiateAudio() {
		try {
			MulticastSocket socket = cliente.getDtSocket();
			byte[] audioBuffer = new byte[10000];
			// ...
			while (true) {
				DatagramPacket packet = new DatagramPacket(audioBuffer, audioBuffer.length);
				socket.receive(packet);
				// ...

				try {
					byte audioData[] = packet.getData();
					InputStream byteInputStream = new ByteArrayInputStream(audioData);
					AudioFormat audioFormat = getAudioFormat();
					audioInputStream = new AudioInputStream(byteInputStream, audioFormat,
							audioData.length / audioFormat.getFrameSize());
					DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

					sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
					sourceDataLine.open(audioFormat);
					sourceDataLine.start();
					playAudio();
				} catch (Exception e) {
					// Handle exceptions
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
