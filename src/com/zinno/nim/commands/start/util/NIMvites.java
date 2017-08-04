package com.zinno.nim.commands.start.util;

import java.util.HashMap;

public class NIMvites {
	private NIMvites() {}
	
	private static HashMap<String, String> nimInv = new HashMap<String, String>();
	
	public static void addNimInvs(String original, String invited) {
		nimInv.put(invited.toLowerCase(), original.toLowerCase());
	}
	
	public static String getNimInvs(String name) {
		for(String s : nimInv.keySet()) {
			if(s.equals(name.toLowerCase())) {
				return nimInv.get(s);
			}
		}
		return null;
	}
	
	public static void delNimInvs(String name) {
		for(String s : nimInv.keySet()) {
			if(s.equalsIgnoreCase(name)) {
				nimInv.remove(s);
				return;
			}
		}
	}
	
	public static boolean killNimInv(String sender, String invited) {
		for(String s : nimInv.keySet()) {
			if(s.equalsIgnoreCase(invited) && nimInv.get(s).equalsIgnoreCase(sender)) {
				nimInv.remove(s);
				return true;
			}
		}
		return false;
	}
}
