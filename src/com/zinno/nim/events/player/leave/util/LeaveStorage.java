package com.zinno.nim.events.player.leave.util;

import java.util.ArrayList;
import java.util.List;

public class LeaveStorage {
	
	private static List<String> leavers = new ArrayList<String>();
	
	public static boolean checkPlayer(String name) {
		if(leavers.contains(name)) {
			return true;
		}
		return false;
	}
	
	public static void addPlayer(String name) {
		leavers.add(name);
	}
	
	public static void delPlayer(String name) {
		leavers.remove(name);
	}
}
