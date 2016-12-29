package gameClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class LeaderBoard {
	private Vector<String> names;
	private Vector<Integer> scores;
	LeaderBoard(){
		names = new Vector<String>();
		scores = new Vector<Integer>();
	}
	
	public void read(){
		File in = new File("src/game/leader.txt");
		Scanner sc = null;
		//Read in the board
		try {
			sc = new Scanner(in);
		} catch (FileNotFoundException e) {}
		
		while(sc.hasNext()){
			String temp = sc.next();
			Integer temp2 = sc.nextInt();
			names.add(temp);
			//System.out.println(names);
			scores.add(temp2);
			//System.out.println(scores);
		}
		sc.close();
	}
	public void write() throws IOException{
		File realFile = new File("src/game/leader.txt");
			PrintWriter pr;
			pr = new PrintWriter(realFile);
		for(int i=0;i<names.size();i++){
			pr.println(names.get(i) + " " + scores.get(i));
		}
		pr.close();
	}
	public int size(){
		return names.size();
	}
	public String getName(int l){
		return names.get(l);
	}
	public Integer getScore(int l){
		return scores.get(l);
	}
	public void add(String n, Integer s){
		names.add(n);
		scores.add(s);
	}
	public Vector<String> getAllname(){
		Vector<String> back = new Vector<String>();
		for(int i=0;i<names.size();i++){
			back.add(names.get(i));
		}
		return back;
	}
	public Vector<Integer> getAllscore(){
		Vector<Integer> back = new Vector<Integer>();
		for(int i=0;i<scores.size();i++){
			back.add(scores.get(i));
		}
		return back;
	}
}
