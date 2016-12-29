package pong;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PongNetClient extends Thread {
	
	private int sockID;
	private String ip;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private PongGameManager pgm; 
	private Socket s;

	public PongNetClient(int sockID, String ip, PongGameManager pgm)
	{
		this.sockID = sockID; 
		this.ip = ip;
		this.pgm = pgm; 
		s = null; 
		ois = null;
		oos = null; 
		try {
			 s = new Socket(ip, sockID);
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	public void sendObject(Object obj)
	{

		try {
			oos.writeObject(obj);
			oos.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void run ()
	{
		try {

			while(true)
			{
				Object obj = ois.readObject();
				if(obj instanceof PongBall)
				{
					PongBall ball = (PongBall) obj;
					pgm.setBall(ball);
				}
				if(obj instanceof Integer)
				{
					int i = (int)obj; 
					System.out.println("getting location "+i);

					pgm.setNetPaddle(i);
				}
				if(obj instanceof PongPaddle)
				{
					//System.out.println("recieved paddle moving ");
					PongPaddle recieved = (PongPaddle) obj; 
					//System.out.println("recieved paddle moving to " +recieved.toString());
				}
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try{

				if(ois!=null)
				{
					ois.close();
				}
				if(oos!=null)
				{
					oos.close(); 
				}
				if(s!=null)
				{
					s.close();
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
