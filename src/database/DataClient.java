package database;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class DataClient extends Thread{
	private ObjectInputStream ois=null;
	private ObjectOutputStream oos=null;
	private Socket s;
	private String[] n_pw;
	
	public DataClient(String address, int port){
		try {
			s = new Socket(address, port);

			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start();
	}
	
	public void run(){
//		try {
			//n_pw = null;
			while(true){
				
			}
//		} 
//		catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	
	//check user name - password combination
	//Note: accept String[] as input. Length of this String[] have to be 2, and with order {[User Name] [Password]}
	public boolean checkname_pw(String[] combo){
//		n_pw = combo;
		boolean back=false;
		try {
			if(oos!=null){
			oos.writeObject(combo);
//			System.out.println("dc send");
			oos.flush();
			oos.reset();
			Object o = null;
			while(true){
				o = ois.readBoolean();
				if(o!=null){
						back = (boolean)o;
						break;
				}
				//return (boolean)o;
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return back;
	}
	
	//try to create a new user
	//if a new user is created, return true; else, return false
	//Note: must give a String[] of length 3 as input
	//Note: content of String[]: {[User Name] [Password] [Anything, this element is useless, but have to be here]}
	public boolean add_user(String[] n_pw_try){
//		n_pw = combo;
		boolean back=false;
		try {
			if(oos!=null){
			oos.writeObject(n_pw_try);
//			System.out.println("dc send name try");
			oos.flush();
			oos.reset();
			Object o = null;
			while(true){
				o = ois.readBoolean();
				if(o!=null){
						back = (boolean)o;
						break;
				}
				//return (boolean)o;
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return back;
	}
	
	public int getScore(){
		Integer back=null;
		try {
			if(oos!=null){
//				Integer temp = 1;
			oos.writeObject("10");
//			System.out.println("dc send");
			oos.flush();
			oos.reset();
			Object o = null;
			while(true){
				o = ois.readInt();
				if(o!=null){
						back = (Integer)o;
						break;
				}
				//return (boolean)o;
			}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return back;
	}
//	public void update_score(Integer new_score){
//		try {
//			if(oos!=null){
//				Integer send = new_score;
////				String temp = "score";
//				oos.writeObject("score");
//				oos.flush();
//				oos.reset();
//				oos.writeInt(send);
//				oos.flush();
//				oos.reset();
////				System.out.println("dc send new score " + new_score);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public void update_slider_score(Integer new_score){
		try {
			if(oos!=null){
				Integer send = new_score;
//				String temp = "score";
				oos.writeObject("21");
				oos.flush();
				oos.reset();
				oos.writeInt(send);
				oos.flush();
				oos.reset();
//				System.out.println("dc send new score " + new_score);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update_snake_score(Integer new_score){
		try {
			if(oos!=null){
				Integer send = new_score;
//				String temp = "score";
				oos.writeObject("22");
				oos.flush();
				oos.reset();
				oos.writeInt(send);
				oos.flush();
				oos.reset();
//				System.out.println("dc send new score " + new_score);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update_space_score(Integer new_score){
		try {
			if(oos!=null){
				Integer send = new_score;
//				String temp = "score";
				oos.writeObject("23");
				oos.flush();
				oos.reset();
				oos.writeInt(send);
				oos.flush();
				oos.reset();
//				System.out.println("dc send new score " + new_score);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update_pong_score(Integer new_score){
		try {
			if(oos!=null){
				Integer send = new_score;
//				String temp = "score";
				oos.writeObject("24");
				oos.flush();
				oos.reset();
				oos.writeInt(send);
				oos.flush();
				oos.reset();
//				System.out.println("dc send new score " + new_score);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, Integer> getLeader(){
		Map<String, Integer> dtm = null;
		try {
			if(oos!=null){
				oos.writeObject("90");
				oos.flush();
				oos.reset();
				while(true){
					Object o = ois.readObject();
					if(o!=null && o instanceof Map){
						dtm = (Map<String, Integer>)o;
						break;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtm;
	}
	
	public Map<String, Integer> getSliderLeader(){
		Map<String, Integer> dtm = null;
		try {
			if(oos!=null){
				oos.writeObject("91");
				oos.flush();
				oos.reset();
				while(true){
					Object o = ois.readObject();
					if(o!=null && o instanceof Map){
						dtm = (Map<String, Integer>)o;
						break;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtm;
	}
	public Map<String, Integer> getSnakeLeader(){
		Map<String, Integer> dtm = null;
		try {
			if(oos!=null){
				oos.writeObject("92");
				oos.flush();
				oos.reset();
				while(true){
					Object o = ois.readObject();
					if(o!=null && o instanceof Map){
						dtm = (Map<String, Integer>)o;
						break;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtm;
	}
	public Map<String, Integer> getSpaceLeader(){
		Map<String, Integer> dtm = null;
		try {
			if(oos!=null){
				oos.writeObject("93");
				oos.flush();
				oos.reset();
				while(true){
					Object o = ois.readObject();
					if(o!=null && o instanceof Map){
						dtm = (Map<String, Integer>)o;
						break;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtm;
	}
	public Map<String, Integer> getPongLeader(){
		Map<String, Integer> dtm = null;
		try {
			if(oos!=null){
				oos.writeObject("94");
				oos.flush();
				oos.reset();
				while(true){
					Object o = ois.readObject();
//					System.out.println(o.getClass());
					if(o!=null){
						if(o instanceof Map){
							dtm = (Map<String, Integer>)o;
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtm;
	}
	
}
