package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Receptor extends Thread{
	
	private InputStream input;
	private boolean isAlive = true;
	private String ip;
	List<TCPConnection.ConnectionEvent> listeners;
	
	public Receptor(InputStream input, List<TCPConnection.ConnectionEvent> listeners, String ip) {
		this.ip = ip;
		this.input = input;
		this.listeners = listeners;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			while( isAlive ) {
				String line = reader.readLine();
				//Detectamos la desconexión
				if(line ==  null) {
					input.close();
				}

				for (int i = 0; i < listeners.size(); i++) {
					listeners.get(i).onMessage(line);
				}
			}
		}catch(IOException ex) {
			
		}
	}

}
