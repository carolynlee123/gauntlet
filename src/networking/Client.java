package networking;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pong.PongBall;
import pong.PongGameManager;
import pong.PongPaddle;
import puzzle.MultiplePlayerLobby;

public class Client extends Thread {
	
	private int sockID;
	private String ip;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private PongGameManager pgm; 
	private MultiplePlayerLobby mpl;
	private Socket s;
	private int id;
	private boolean keepGoing = true;

	public Client(int sockID, String ip, PongGameManager pgm)
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
			this.start();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	public int getID()
	{
		return id; 
	}
	public Client(int sockID, String ip, MultiplePlayerLobby mpl, int id)
	{
		this.sockID = sockID; 
		this.id = id;
		this.ip = ip;
		this.mpl = mpl;
		s = null; 
		ois = null;
		oos = null; 
		try {
			 s = new Socket(ip, sockID);
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			this.start();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	

	public void setPongGameManager(PongGameManager pgm) {
		this.pgm = pgm;
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

			while(isKeepGoing())
			{
				Object obj = ois.readObject();
				if(obj == null) continue;

				if(obj instanceof PongBall)
				{
					PongBall ball = (PongBall) obj;
					if(!pgm.gameOver())pgm.setBall(ball);
				}
				if(obj instanceof Integer)
				{
					int i = (int)obj; 
					System.out.println("getting location "+i);

					if(!pgm.gameOver())pgm.setNetPaddle(i);
				}
				if(obj instanceof PongPaddle)
				{
					//System.out.println("recieved paddle moving ");
					PongPaddle recieved = (PongPaddle) obj; 
					//System.out.println("recieved paddle moving to " +recieved.toString());
				}
				if (obj instanceof String) {
					String temp = obj.toString();
					if (obj.equals("--Done")) {
						System.out.println("id is: " + id);
						System.out.println("Got a --Done object in the client");
						mpl.finishedGame();
					}
					if(temp.equals("~~EndGame"))
					{
						pgm.endGameFromClient();
					}
					if(temp.equals("~~IncrementPlayer2Score"))
					{
						pgm.incrementPlayer2Score(true);
					}
					if(temp.equals("~~IncrementPlayer1Score"))
					{
						pgm.incrementPlayer1Score(true);

					}
					else {
						System.out.println("Got a " + temp);
					}
				}
				if (obj instanceof Long[]) {
					System.out.println("Got a long [] in the client");
					Long [] temp = (Long []) obj;
					mpl.setWinnerLoserFrame(temp[0], temp[1], temp[2]);
				}
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			long temp = -1;
			mpl.setWinnerLoserFrame(temp, temp, temp);
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
	public boolean isKeepGoing() {
		return keepGoing;
	}
	public void setKeepGoing(boolean keepGoing) {
		this.keepGoing = keepGoing;
	}

}

