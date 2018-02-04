package com.zinno.nim.commands.start.computer.util;

import java.util.HashMap;

import org.bukkit.inventory.Inventory;

public class InventoryStorage {
	
	private static HashMap<String, Inventory> playerInv = new HashMap<String, Inventory>();
	private static HashMap<String, Integer> playerDifficulty = new HashMap<String, Integer>();
	private static HashMap<String, Integer> playerGameType = new HashMap<String, Integer>();
	
	public static Inventory getPlayerInv(String name) {
		return playerInv.get(name);
	}
	
	public static Integer getPlayerDifficulty(String name) {
		return playerDifficulty.get(name);
	}
	
	public static Integer getPlayerGameType(String name) {
		return playerGameType.get(name);
	}
	
	public static void setPlayerInv(String name, Inventory inv) {
		playerInv.put(name, inv);
	}
	
	public static void setPlayerDifficulty(String name, int difficulty) {
		playerDifficulty.put(name, difficulty);
	}
	
	public static void setPlayerGameType(String name, int gameType) {
		playerGameType.put(name, gameType);
	}

}
