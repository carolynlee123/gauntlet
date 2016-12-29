package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkClient implements Runnable {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket s;
	
	public NetworkClient(String hostName, int hostPort) {
		try {
			s = new Socket(hostName, hostPort);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			new Thread(this).start();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void sendObject(Object object) {
		if (object != null) {
			try {
				oos.writeObject(object);	// write object to NetworkManager
				oos.flush(); // remember to flush
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		if (oos != null && ois != null) {
			try {
				while (true) {
					Object object = ois.readObject();
					if (object != null) {
						// TODO James: react to object
						if (object instanceof String) {	// TODO: test code
							System.out.println((String)object);
						}
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			} finally {
				try {
					if (oos != null) {
						oos.close();
					}
					if (ois != null) {
						ois.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
}
