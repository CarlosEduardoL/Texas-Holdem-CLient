package communication;

import model.Mensaje;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class TCPConnection {
	
	private static TCPConnection instance = null;
	
	private TCPConnection(int port) {
		try {
			server = new ServerSocket(port);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
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
	private Emisor emisor;
	private Receptor receptor;
	
	
	// clients
	Hashtable<String,Connection> connections = new Hashtable<String,Connection>();
	
	//Metodo del servidor
	
	public void waitForConnection() {
		new Thread(() -> waitForConnection(0)).start();
	}
	
	private void waitForConnection(int l) {
		try {
			while(true) {
				System.out.println("Esperando cliente");
				Socket socket = server.accept();
				System.out.println("Cliente conectado!");
				connections.put(socket.getInetAddress().getHostAddress(),new Connection(socket,listeners));
				for (int i = 0; i < listeners.size(); i++) {
					listeners.get(i).onConnection(socket.getInetAddress().getHostAddress());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Metodo del cliente
	public void connect(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			receptor = new Receptor(socket.getInputStream(),listeners,socket.getInetAddress().getHostAddress());
			receptor.start();
			emisor = new Emisor(socket.getOutputStream());
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).onConnection(socket.getInetAddress().getHostAddress());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void sendMessage(String msj) {
		if(socket != null) {
            Mensaje m = new Mensaje(msj,socket.getInetAddress().getHostAddress());
            emisor.enviarMensaje( m.toString());
        }
	}
	
	public void sendBrodcast(String msj) {
		Enumeration<String> keys = connections.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			connections.get(key).sendMessage(msj);
		}
	}

	public void closeConnection() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	//Hacer la vlase observable
	public interface ConnectionEvent{
		void onConnection(String ip);
		void onMessage(String msj);
	}
	
	private final List<ConnectionEvent> listeners = new ArrayList<>();
	
	public void addConnectionEvent(ConnectionEvent listener) {
		this.listeners.add(listener);
	}
	
	
	
	
	
	
}
