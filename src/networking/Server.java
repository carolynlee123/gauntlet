package networking;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import pong.PongBall;
import pong.PongBallRequest;
import pong.PongGameManager;
import pong.PongPaddle;
import puzzle.MultiplePlayerLobby;

public class Server extends Thread {

	private final int sockID;
	private Socket s1, s2;
	private PongServerThread pst1, pst2;
	public final static int maxPlayers = 2;
	private PongGameManager pgm;
	MultiplePlayerLobby mpl;
	int totalScoreCount = 0;
	Long [] compareScore;
	ServerSocket ss;

	public Server(int sockID, PongGameManager pgm) {
		this.sockID = sockID;
		this.pgm = pgm;
		pst1 = null;
		pst2 = null;
		this.start();

	}

	public Server(int sockID, MultiplePlayerLobby mpl) {
		System.out.println("In the server constructor");
		this.sockID = sockID;
		this.pgm = pgm;
		pst1 = null;
		pst2 = null;
		this.mpl = mpl;
		try {
			ss = new ServerSocket(sockID);
			this.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public void reset() {
		pst1.keepGoing = false;
		pst2.keepGoing = false;
	}

	public void setPongGameManager(PongGameManager pgm) {
		this.pgm = pgm;
	}

	public Server(int sockID) {
		this.sockID = sockID;
	}

	public void sendObjectToAll(Object obj, PongServerThread pst) {
		if (pst == pst1 && pst2 != null) {
			System.out.println("sending to player 2");
			if (obj instanceof PongPaddle) {
				PongPaddle temp = (PongPaddle) obj;
				System.out.println("server thread sending : " + temp.toString());
			}
			pst2.sendObject(obj);
			;
		} else if (pst == pst2 && pst1 != null) {
			System.out.println("sending to player 1");
			if (obj instanceof PongPaddle) {
				PongPaddle temp = (PongPaddle) obj;
				System.out.println("server thread sending : " + temp.toString());
			}
			pst1.sendObject(obj);
		}
	}

	public void sendObjectToAll(Object obj) {
		pst2.sendObject(obj);
		pst1.sendObject(obj);
	}

	public void run() {
		//ServerSocket ss = null;
		try {
			/*
			 * int nPlayers =0; while(nPlayers<maxPlayers) { s = ss.accept(); }
			 */
			s1 = ss.accept();
			pst1 = new PongServerThread(s1,0);
			//pst1.start();
			s2 = ss.accept();
			pst2 = new PongServerThread(s2,1);
			//pst2.start();
			sendObjectToAll("--Done");
			sendObjectToAll("--Done");
			//ss.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
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

	class PongServerThread extends Thread {
		private Socket s;
		private Server ps;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		private int threadID; 
		boolean keepGoing = true;
		public PongServerThread(Socket s, int id) {
			this.s = s;
			this.threadID = id; 
			oos = null;
			ois = null;
			try {
				oos = new ObjectOutputStream(s.getOutputStream());
				ois = new ObjectInputStream(s.getInputStream());
				this.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void sendObject(Object obj) {
			try {
				if (obj instanceof PongPaddle) {
					PongPaddle temp = (PongPaddle) obj;
					System.out.println("server thread sending : " + temp.toString());
				}
				if (obj instanceof String) {
					String temp = (String) obj;
					System.out.println("server thread sending : " + temp);
				}
				oos.writeObject(obj);
				oos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
		
		private void compareScores(Long[] score2) {
			System.out.println("Comparing the scores!");
			System.out.println("In compare, id1 is: " + compareScore[0]);
			System.out.println("In compare, id2 is: " + score2[0]);
			System.out.println("Score 1" + compareScore[1]);
			System.out.println("Score 2" + score2[1]);
			if (compareScore[1] > score2[1]) {
				Long[] temp = {compareScore[0], compareScore[1], score2[1]};
				sendObject("Hello");
				//sendObject(temp);
				Server.this.sendObjectToAll(temp);
				System.out.println("Player 0 won");
			}
			else {
				Long[] temp = {score2[0], score2[1], compareScore[1]};
				sendObject("Hello 2");
				//sendObject(temp);
				Server.this.sendObjectToAll(temp);
				System.out.println("Player 1 won");
			}
		}

		public void run() {

			try {
				while (keepGoing) {
					Object obj = ois.readObject();
					if(obj == null) continue;
					if (obj instanceof String) {
						String temp = obj.toString();
						if(temp.equals("~~RequestBall") )
						{
							PongBall pb = new PongBall();
							pb.randomBallRelease();
							Server.this.sendObjectToAll(pb);
						}
						if(temp.equals("~~EndGame"))
						{
							if(!pgm.gameOver()) pgm.endGame();
						}
						Server.this.sendObjectToAll(obj);
					}
					else if (obj instanceof Long[]) {
						System.out.println("Got a score!");
						totalScoreCount++;
						
						if (totalScoreCount == 1) {
							compareScore = (Long []) obj;
							System.out.println("In thread, id is: " + compareScore[0]);
						}
						else {
							Long [] temp = (Long []) obj;
							compareScores(temp);
						}
					}
					else {
						if(obj instanceof Integer)
						{
							int i= (int)obj; 
							if(threadID==0)
							{
								if(!pgm.gameOver())pgm.setRightPaddle(i);
							}
							else
							{
								if(!pgm.gameOver())pgm.setLeftPaddle(i);
							}
							//Server.this.sendObjectToAll(pgm.getPongBall());
						}
						/*
						if (obj instanceof PongBallRequest) {
							PongBall pb = new PongBall();
							pb.randomBallRelease();
							pgm.setBall(pb);
							Server.this.sendObjectToAll(pb);
							continue;
						}*/
						Server.this.sendObjectToAll(obj, this);
						// other server
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				long temp = -1;
				mpl.setWinnerLoserFrame(temp, temp, temp);
				assert(false);

				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			finally {
			
				try {
					if (oos != null) {
						oos.close();
					}
					if (ois != null) {
						ois.close();
					}
					if (s != null) {
						s.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

