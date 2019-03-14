package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TCPConnection {

	private static TCPConnection instance = null;

	private TCPConnection(int port) {
		//try {
			//server = new ServerSocket(port);
			listeners = new ArrayList<>();
		//}catch(IOException ex) {
		//	ex.printStackTrace();
		//}
	}

	public synchronized static TCPConnection getInstance(int port) {
		if(instance == null) {
			instance = new TCPConnection(port);
		}
		return instance;
	}

	public synchronized static TCPConnection getInstance() {
		return instance;
	}

	//Global
	private Socket socket;
	private ServerSocket server;
	private Receptor receptor;
	private Emisor emisor;

	//Metodo del servidor
	public void waitForConnection(int port) {
		try {
			System.out.println("Esperando cliente");
			socket = server.accept();
			System.out.println("Cliente conectado!");
			if(listeners == null) listeners = new ArrayList<>();
			receptor = new Receptor(socket.getInputStream(), listeners);
			receptor.start();
			emisor = new Emisor(socket.getOutputStream());

			for(int i=0 ; i<listeners.size() ; i++) listeners.get(i).onConnection();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Metodo del cliente
	public void connect(String direccion, int port) {
		for (int i = 0; i < 256 && socket == null; i++) {
			Long time = System.currentTimeMillis();
			for (int j = 1; j < 255&& socket == null; j++) {
				String[] algo = direccion.replace(".","j").split("j");
				String ip =  algo[0]+"."+algo[1]+"."+i+"."+j;
				new Thread(
						() -> {
								//System.out.println("ip = " + ip);

							try {
								if (TCPConnection.this.socket == null){

									Socket socket = new Socket();
									socket.connect(new InetSocketAddress(ip, port), 2000);
									BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

									new Thread(
											() -> {
												try {
													String mensaje = reader.readLine();
													System.out.println(mensaje);
													if (mensaje != null){
														if (mensaje.contains("Disponible") && TCPConnection.this.socket == null){
															TCPConnection.this.socket = socket;
															receptor = new Receptor(socket.getInputStream(), listeners);
															receptor.start();
															emisor = new Emisor(socket.getOutputStream());

															for(int l=0 ; l<listeners.size() ; l++) listeners.get(l).onConnection();
														}
													}
												} catch (IOException e) {
													e.printStackTrace();
												}
											}
									).start();

									try {
										Thread.sleep(5000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									if (!socket.equals(TCPConnection.this.socket)){
										socket.close();
									}

								}
							} catch (IOException  e) {
							}
						}
				).start();

			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(i + "" +
					" \nTiempo de busqueda:: " + (System.currentTimeMillis() - time) + "" +
					" \nCantidad De hilos:: " + Thread.activeCount());
		}
	}

	public void sendMessage(String msj) {
		emisor.enviarMensaje(msj);
	}

	public void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Hacer la clase sea observable
	public interface ConnectionEvent{
		void onConnection();
		void onMessage(String msj);
	}

	private List<ConnectionEvent> listeners;

	public void addConnectionEvent(ConnectionEvent listener) {
		if(listeners == null) {
			listeners = new ArrayList<>();
		}
		listeners.add(listener);
	}







}