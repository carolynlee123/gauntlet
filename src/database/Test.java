package database;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Test {

	public static void main(String[] args){
		DataClient dc = new DataClient("localhost", 6789);
//		dc.start();
//		String[] str = new String[2];
//		str[0] = "name";
//		str[1] = "test";
//		System.out.println("here");
//		boolean res = dc.checkname_pw(str);
//		str = new String[3];
//		str[0] = "lala";
//		str[1] = "boo";
//		str[2] = "useless";
//		boolean res2 = dc.add_user(str);
//		if(res){
			System.out.println("true");
//		}else{
//			System.out.println("false");
//		}
//		int rr = dc.getScore();
//		System.out.println(rr);
//		System.out.println(res2);
//		Integer score = 22;
//		dc.update_slider_score(score);
		Map<String, Integer> temp = dc.getPongLeader();
		for (Map.Entry<String, Integer> entry : temp.entrySet())
		{
		    System.out.println(entry.getKey() + " /test/ " + entry.getValue());
		}
//		temp = dc.getSliderLeader();
//		temp = dc.getSnakeLeader();
//		temp = dc.getSpaceLeader();
	}
}
