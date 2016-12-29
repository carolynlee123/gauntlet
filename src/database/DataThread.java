package database;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class DataThread extends Thread{
	private Socket s;
	private DataServer server;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private String user_name=null;
	private String password=null;
	private Integer id=null;
	public DataThread(Socket s, DataServer server){
		this.s = s;
		this.server = server;
	}
	
	public void run(){
		try {
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			while(true){
				Object o = ois.readObject();
				//see if DataClient send something
				if(o!=null){
					//so DataClient send something
					if(o instanceof String[]){//if client send a string array, it should only be asking for 
						//Verify the combination of Username and password
						String incoming_u = ((String[])o)[0];
						String incoming_p = ((String[])o)[1];
						//String mode = ((String[])o)[2];
						if(((String[])o).length==2){
							System.out.println(incoming_u + " +in dt+ " + incoming_p);
							Integer id_temp = server.searchUsername(incoming_u);
							System.out.println("do we have id back " + id_temp);
							String temp_pw = server.searchPassword(id_temp);
							if(id_temp==-1){
								oos.writeBoolean(false);
								oos.flush();
								oos.reset();
							}
							else if(!temp_pw.equals(incoming_p)){
								oos.writeBoolean(false);
								oos.flush();
								oos.reset();
							}
							else{
								oos.writeBoolean(true);
								oos.flush();
								oos.reset();
								this.user_name = incoming_u;
								this.password = incoming_p;
								this.id = id_temp;
								System.out.println("info in dt: " + this.user_name + " " + this.password + " " + this.id);
							}
						}
						else if(((String[])o).length==3){//if the length is longer(or maybe shorter?), currently it will mean client is trying to create a new user
//							System.out.println(((String[])o)[0] + ((String[])o)[1] + ((String[])o)[2]);
							Integer id_temp = server.searchUsername(incoming_u);
//							System.out.println(id_temp);
							if(id_temp==-1){
								server.addu_pw(incoming_u, incoming_p);
								this.user_name = incoming_u;
								this.password = incoming_p;
								this.id = server.searchUsername(incoming_u);
								System.out.println("new user added");
								System.out.println(id + " " + user_name + " " + password);
								oos.writeBoolean(true);
								oos.flush();
								oos.reset();
//								server.save();
							}else{
								oos.writeBoolean(false);
								oos.flush();
								oos.reset();
							}
						}
						
					}else if(o instanceof String){
						if(((String)o).equals("10")){//code 10 = request total score
							if(id!=null){
//								System.out.println("dt here");
								int back_score = server.searchScore(id);
								oos.writeInt(back_score);
//								oos.write(new Integer(back_score));
								oos.flush();
								oos.reset();
							}
							else{
								oos.writeInt(-1);
								oos.flush();
								oos.reset();
							}
						}
						else if(((String)o).equals("11")){//code 11 = request slider score
							if(id!=null){
//								System.out.println("dt here");
								int back_score = server.searchSliderScore(id);
								oos.writeInt(back_score);
//								oos.write(new Integer(back_score));
								oos.flush();
								oos.reset();
							}
							else{
								oos.writeInt(-1);
								oos.flush();
								oos.reset();
							}
						}
						else if(((String)o).equals("12")){//code 11 = request snake score
							if(id!=null){
//								System.out.println("dt here");
								int back_score = server.searchSnakeScore(id);
								oos.writeInt(back_score);
//								oos.write(new Integer(back_score));
								oos.flush();
								oos.reset();
							}
							else{
								oos.writeInt(-1);
								oos.flush();
								oos.reset();
							}
						}
						else if(((String)o).equals("13")){//code 11 = request space invader score
							if(id!=null){
//								System.out.println("dt here");
								int back_score = server.searchSpaceScore(id);
								oos.writeInt(back_score);
//								oos.write(new Integer(back_score));
								oos.flush();
								oos.reset();
							}
							else{
								oos.writeInt(-1);
								oos.flush();
								oos.reset();
							}
						}
						else if(((String)o).equals("14")){//code 11 = request pong score
							if(id!=null){
//								System.out.println("dt here");
								int back_score = server.searchPongScore(id);
								oos.writeInt(back_score);
//								oos.write(new Integer(back_score));
								oos.flush();
								oos.reset();
							}
							else{
								oos.writeInt(-1);
								oos.flush();
								oos.reset();
							}
						}
						else if(((String)o).equals("90")){//code 90 = request leaderboard
//								System.out.println("dt here");
								Map<String, Integer> dtm = server.getLeaderboard();
								oos.writeObject(dtm);
//								oos.write(new Integer(back_score));
								oos.flush();
								oos.reset();
						}
						else if(((String)o).equals("91")){//code 91 = request slider leaderboard
//								System.out.println("dt here");
								Map<String, Integer> dtm = server.getSliderboard();
								oos.writeObject(dtm);
//								oos.write(new Integer(back_score));
								oos.flush();
								oos.reset();
						}
						else if(((String)o).equals("92")){//code 92 = request snake leaderboard
//								System.out.println("dt here");
								Map<String, Integer> dtm = server.getSnakeboard();
								oos.writeObject(dtm);
//								oos.write(new Integer(back_score));
								oos.flush();
								oos.reset();
						}
						else if(((String)o).equals("93")){//code 93 = request space invader leaderboard
//								System.out.println("dt here");
								Map<String, Integer> dtm = server.getSpaceboard();
								oos.writeObject(dtm);
//								oos.write(new Integer(back_score));
								oos.flush();
								oos.reset();
						}
						else if(((String)o).equals("94")){//code 94 = request pong leaderboard
								System.out.println("dt here");
								Map<String, Integer> dtm = server.getPongboard();
								oos.writeObject(dtm);
//								oos.write(new Integer(back_score));
								oos.flush();
								oos.reset();
						}
						else if(((String)o).equals("21")){//code "21" = request to update current user's slider score
							Integer in = null;
							while(in==null){
								in = ois.readInt();
							}
							if(this.id!=null){
								server.update_score(this.id, in, 0);
							}
						}
						else if(((String)o).equals("22")){//code "22" = request to update current user's slider score
							Integer in = null;
							while(in==null){
								in = ois.readInt();
							}
							if(this.id!=null){
								server.update_score(this.id, in, 1);
							}
						}
						else if(((String)o).equals("23")){//code "23" = request to update current user's slider score
							Integer in = null;
							while(in==null){
								in = ois.readInt();
							}
							if(this.id!=null){
								server.update_score(this.id, in, 2);
							}
						}
						else if(((String)o).equals("24")){//code "24" = request to update current user's slider score
							Integer in = null;
							while(in==null){
								in = ois.readInt();
							}
							if(this.id!=null){
								server.update_score(this.id, in, 3);
							}
						}
					}
//					else if(o instanceof Integer){
//						if(this.id!=null){
//							boolean success = server.update_score(this.id, (Integer)o);
//							System.out.println("dt result of score update " + success);
//						}
//					}
				}
			}
		} 
		catch (IOException e) {
			server.removeDataThread(this);
			System.out.println("DataThread IOException");
//			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			System.out.println("DataThread ClassNotFoundException");
//			e.printStackTrace();
		}
	}
	
}
