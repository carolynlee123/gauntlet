package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DataServer {
	private  ServerSocket ss;
	private  Map<String, Integer> n_id;
	private  Map<Integer, String> id_pw;
	private  Map<Integer, Integer> id_score;
	private Map<Integer, Integer> id_slider, id_snake, id_pong, id_space;
	private  Vector<DataThread> dt;
	private Lock lock = new ReentrantLock();
	
	public DataServer(){

		n_id = Collections.synchronizedMap(new HashMap<String, Integer>()); 
		//the map for user name & user id
		id_pw = Collections.synchronizedMap(new HashMap<Integer, String>()); 
		// the map for user id & their password
		id_score = Collections.synchronizedMap(new HashMap<Integer, Integer>()); 
		// the map for user id & their score
		id_slider = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		id_snake = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		id_space = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		id_pong = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		dt = new Vector<DataThread>();
		System.out.println ("Waiting on port 6789");
		Scanner sc=null;
		try {
			sc = new Scanner(new File("info/db.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(sc.hasNext()) {
			// read from the file to get pre-exist information
			int id = sc.nextInt();
			String name = sc.next();
			String password = sc.next();
			int slider = sc.nextInt();
			int snake = sc.nextInt();
			int space = sc.nextInt();
			int pong = sc.nextInt();
			int total = slider+snake+space+pong;
			System.out.println(id + " " + name + " " + password + " "+ slider + " " + snake + " " + space + " " + pong);
			n_id.put(name, id);
			id_pw.put(id, password);
			id_slider.put(id, slider);
			id_snake.put(id, snake);
			id_space.put(id, space);
			id_pong.put(id, pong);
			id_score.put(id, total);
		}
		sc.close();
		try{
			ss = new ServerSocket(6789);
			while(true) {
				Socket connected = ss.accept();
				System.out.println("connected" + connected.getInetAddress());
				DataThread incoming = new DataThread(connected, this);
				dt.add(incoming);
				incoming.start();
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	public static void main(String[] args) throws Exception{
		DataServer ds = new DataServer();
	}
	//search through user-id map; 
	//if the username exist, it will return user's id
	//if the username doesn't exist, it will return -1
	public int searchUsername(String name){
		lock.lock();
		Integer name_id = n_id.get(name);
//		System.out.println("called");
		lock.unlock();
		if(name_id==null){
			return -1;
		}
		return name_id;
	}
	//search through id-password map; 
	//if the id exist, it will return id's password
	//if the id doesn't exist, it will return null (I think this is how map work)
	public String searchPassword(int number){
		lock.lock();
		String userPassword = id_pw.get(number);
		lock.unlock();
		return userPassword;
	}
	//search through id-score map; 
	//if the id exist, it will return id's score
	//if the id doesn't exist, it will return null (I think this is how map work)
	public int searchScore(int number){
		lock.lock();
		System.out.println("called");
		Integer userScore = id_score.get(number);
		lock.unlock();
		return userScore;
	}
	
	public int searchSliderScore(int number){
		System.out.println("called");
		Integer userScore = id_slider.get(number);
		return userScore;
	}
	
	public int searchSnakeScore(int number){
		System.out.println("called");
		Integer userScore = id_snake.get(number);
		return userScore;
	}
	
	public int searchSpaceScore(int number){
		System.out.println("called");
		Integer userScore = id_space.get(number);
		return userScore;
	}
	
	public int searchPongScore(int number){
		System.out.println("called");
		Integer userScore = id_pong.get(number);
		return userScore;
	}
	//add a new username & password combination
	//this new user will start with score 0
	//this methods should be used after new user name is already checked so that there are no repeat user name
	public void addu_pw(String new_name, String new_password){
		lock.lock();
		int id_max = 0;
		for (Map.Entry<String, Integer> entry : n_id.entrySet()) // this loop will get the max id
		{
//		    System.out.println(entry.getKey() + "/" + entry.getValue());
		    if(id_max<entry.getValue()){
		    	id_max = entry.getValue();
		    }
		}
		System.out.println("largest id " + id_max);
		id_max++;
		n_id.put(new_name, id_max);
		id_pw.put(id_max, new_password);
		id_slider.put(id_max, 0);
		id_snake.put(id_max, 0);
		id_space.put(id_max, 0);
		id_pong.put(id_max, 0);
		id_score.put(id_max, 0);
		save();
		lock.unlock();
	}
	//provide with user's id and a new score, then this method will try to update older score
	//if the new score is > then old score, the score will be updated
	//if the new score is < then old score, nothing will happen

//	public boolean update_score(int user_id, int score){
//		lock.lock();
//		int prev_score = id_score.get(user_id);
//		System.out.println("ds score update " + prev_score + "<=" + score);
//		if(prev_score<score){
//			id_score.put(user_id, score);
//			lock.unlock();

//	public boolean update_score(int user_id, int score){
//		int prev_score = id_score.get(user_id);
//		System.out.println("ds score update " + prev_score + "<=" + score);
//		if(prev_score<score){
//			id_score.put(user_id, score);
//			System.out.println("saved");
//			save();
//			return true;
//		} // if change been made, this method will return true; vice versa
//		//save();
//		return false;
//		
//	}
	
	public boolean update_score(int user_id, int score, int game_id){
		lock.lock();
		Map<Integer, Integer> map = null;
		if(game_id==0){
			map = id_slider;
			System.out.println("slider");
		}else if(game_id==1){
			map = id_snake;
			System.out.println("snake");
		}else if(game_id==2){
			map = id_space;
			System.out.println("space invader");
		}else if(game_id==3){
			map = id_pong;
			System.out.println("pong");
		}
		int prev_score = map.get(user_id);
		System.out.println("ds score update " + prev_score + "<=" + score);
		if(prev_score<score){
			map.put(user_id, score);
			System.out.println("saved");
			save();
			int slider = id_slider.get(user_id);
			int snake = id_snake.get(user_id);
			int space = id_space.get(user_id);
			int pong = id_pong.get(user_id);
			int total = slider + snake + space + pong;
			id_score.put(user_id, total);
			lock.unlock();
			return true;
		}
		lock.unlock(); // if change been made, this method will return true; vice versa
		//save();
		return false;
	}
	
	// save all map to file
	public void save(){
		lock.lock();
		try {
			PrintWriter pr = new PrintWriter("info/db.txt");
			for (Map.Entry<String, Integer> entry : n_id.entrySet())
			{
//			    System.out.println(entry.getKey() + "/" + entry.getValue());
			    String temp_pw = id_pw.get(entry.getValue());
//			    int temp_score = id_score.get(entry.getValue());
			    int slider = id_slider.get(entry.getValue());
			    int snake = id_snake.get(entry.getValue());
			    int space = id_space.get(entry.getValue());
			    int pong = id_pong.get(entry.getValue());
			    pr.write(entry.getValue() + " " + entry.getKey() + " " + temp_pw + " " + slider + " " + snake + " " + space + " " + pong);
			    pr.write("\n");
			}
			if(pr!=null){
				pr.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			lock.unlock();
		}
	}
	//this methods will combine every user name and their score into a single map and then return it
	public Map<String, Integer> getLeaderboard(){
		Map<String, Integer>back = new HashMap<String, Integer>();
		lock.lock();
		for (Map.Entry<String, Integer> entry : n_id.entrySet())
		{
//		    System.out.println(entry.getKey() + "/" + entry.getValue());
		    int temp_score = id_score.get(entry.getValue());
		    back.put(entry.getKey(), temp_score);
		}
		lock.unlock();
		return back;
	}
	
	public Map<String, Integer> getSliderboard(){
		Map<String, Integer>back = new HashMap<String, Integer>();
		lock.lock();
		for (Map.Entry<String, Integer> entry : n_id.entrySet())
		{
//		    System.out.println(entry.getKey() + "/" + entry.getValue());
		    int temp_score = id_slider.get(entry.getValue());
		    back.put(entry.getKey(), temp_score);
		}
		lock.unlock();
		return back;
	}
	
	public Map<String, Integer> getSnakeboard(){
		Map<String, Integer>back = new HashMap<String, Integer>();
		lock.lock();
		for (Map.Entry<String, Integer> entry : n_id.entrySet())
		{
//		    System.out.println(entry.getKey() + "/" + entry.getValue());
		    int temp_score = id_snake.get(entry.getValue());
		    back.put(entry.getKey(), temp_score);
		}
		lock.unlock();
		return back;
	}
	
	public Map<String, Integer> getSpaceboard(){
		Map<String, Integer>back = new HashMap<String, Integer>();
		lock.lock();
		for (Map.Entry<String, Integer> entry : n_id.entrySet())
		{
//		    System.out.println(entry.getKey() + "/" + entry.getValue());
		    int temp_score = id_space.get(entry.getValue());
		    back.put(entry.getKey(), temp_score);
		}
		lock.unlock();
		return back;
	}
	
	public Map<String, Integer> getPongboard(){
		Map<String, Integer>back = new HashMap<String, Integer>();
		lock.lock();
		for (Map.Entry<String, Integer> entry : n_id.entrySet())
		{
//		    System.out.println(entry.getKey() + "/" + entry.getValue());
		    int temp_score = id_pong.get(entry.getValue());
		    back.put(entry.getKey(), temp_score);
		}
		lock.unlock();
		return back;
	}
	
	public void removeDataThread(DataThread ct) {
		dt.remove(ct);
	}
}
