package networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class NetworkManager {
	private Vector<MessengerThread> mtVect;
	private NetworkClient nc;
	private boolean isHost = false;
	
	public NetworkManager(int port) {
		isHost = true;
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
			nc = new NetworkClient(InetAddress.getLocalHost().getHostName(), port);
			while (true) {
				System.out.println("Waiting for client to connect...");
				Socket s = ss.accept();
				System.out.println("Client " + s.getInetAddress() + ":" + s.getPort() + " connected");
				MessengerThread mt = new MessengerThread(s, this);
				mtVect.add(mt);
				new Thread(mt).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
	
	public NetworkManager(String hostName, int port) {
		isHost = false;
		nc = new NetworkClient(hostName, port);
		new Thread(nc).start();
	}
	
	public void sendObject(Object object) {
		nc.sendObject(object);
	}
	
	public void sendObjectToClients(Object object) {
		if (isHost) {
			for (MessengerThread mt : mtVect) {
				mt.sendObject(object);
			}
		}
	}
}

class MessengerThread implements Runnable {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private NetworkManager server;
	private Socket socket;
	
	public MessengerThread(Socket socket, NetworkManager server) {
		try {
			this.socket = socket;
			this.server = server;
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendObject(Object object) {
		try {
			oos.writeObject(object);	// write object to NetworkClient
			oos.flush();	// remember to flush
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				Object object = ois.readObject();
				if (object != null) {
					server.sendObjectToClients(object);
				}
			}
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (ois != null) {
					ois.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
}