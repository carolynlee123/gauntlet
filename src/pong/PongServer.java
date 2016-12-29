package pong;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PongServer extends Thread{

	private final int sockID;
	private Socket s1, s2; 
	private PongServerThread pst1, pst2; 
	public final static int maxPlayers = 2; 
	private PongGameManager pgm;
	public PongServer(int sockID, PongGameManager pgm)
	{
		this.sockID = sockID; 
		this.pgm = pgm; 
		pst1 = null;
		pst2 = null; 

	}
	public PongServer(int sockID)
	{
		this.sockID = sockID; 
	}
	public void sendObjectToAll(Object obj, PongServerThread pst)
	{
		if(pst == pst1 && pst2!=null)
		{
			System.out.println("sending to player 2");
			if(obj instanceof PongPaddle)
			{
				PongPaddle temp = (PongPaddle)obj;
				System.out.println("server thread sending : "+ temp.toString());
			}
			pst2.sendObject(obj);; 
		}
		else if(pst == pst2 && pst1!=null)
		{
			System.out.println("sending to player 1");
			if(obj instanceof PongPaddle)
			{
				PongPaddle temp = (PongPaddle)obj;
				System.out.println("server thread sending : "+ temp.toString());
			}
			pst1.sendObject(obj); 
		}
	}
	public void sendObjectToAll(Object obj)
	{
			pst2.sendObject(obj);; 
			pst1.sendObject(obj); 
	}
	
	public void run()
	{
		ServerSocket ss = null;
		try
		{
			/*int nPlayers =0;
			while(nPlayers<maxPlayers)
			{
				s = ss.accept();
			}*/
			ss = new ServerSocket(sockID); 
			s1 = ss.accept(); 
			pst1 = new PongServerThread(s1);
			pst1.start();
			s2 = ss.accept();
			pst2 = new PongServerThread(s2); 
			pst2.start(); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(ss!=null)
			{
				try{
					ss.close();
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		}
	}
	class PongServerThread extends Thread
	{
		private Socket s;
		private PongServer ps;
		ObjectOutputStream oos ;
		ObjectInputStream ois;
		public PongServerThread(Socket s)
		{
			this.s = s;
			oos = null; 
			ois =null;
			try{
				oos = new ObjectOutputStream(s.getOutputStream());
				ois = new ObjectInputStream( s.getInputStream()); 
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void sendObject(Object obj)
		{
			try {
				if(obj instanceof PongPaddle)
				{
					PongPaddle temp = (PongPaddle)obj;
					System.out.println("server thread sending : "+ temp.toString());
				}
				oos.writeObject(obj);
				oos.flush();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run()
		{

			try{



				while(true)
				{

					Object obj = ois.readObject();
					if(obj instanceof PongBallRequest)
					{
						PongBall pb = new PongBall();
						pb.randomBallRelease();
						PongServer.this.sendObjectToAll(pb);
						continue; 
					}
					PongServer.this.sendObjectToAll(obj, this);
				
				}
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(oos!=null)
					{
						oos.close();
					}
					if(ois!=null)
					{
						ois.close(); 
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
}
