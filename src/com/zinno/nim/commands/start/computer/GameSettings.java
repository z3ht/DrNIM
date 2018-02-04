package com.zinno.nim.commands.start.computer;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class GameSettings {
	
	private static HashMap<Player, GameSettings> playerGameSettingsMap = new HashMap<Player, GameSettings>();
	
	public enum Types {
		DIFFICULTY,
		MAPSIZE
	}
	
	private int difficulty;
	private int mapSize;
	private boolean lockedOut;
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(int difficulty) {
		if(isLockedOut())
			return;
		this.difficulty = difficulty;
	}
	
	public int getMapSize() {
		return mapSize;
	}
	
	public void setMapSize(int mapSize) {
		if(isLockedOut())
			return;
		this.mapSize = mapSize;
	}
	
	public boolean isComplete() {
		return difficulty != 0 && mapSize != 0;
	}
	
	public boolean isLockedOut() {
		return lockedOut;
	}
	
	public void setLockedOut(boolean lockedOut) {
		this.lockedOut = lockedOut;
	}
	
	public static HashMap<Player, GameSettings> getPlayerGameSettingsMap() {
		return playerGameSettingsMap;
	}
	
	public static void setPlayerGameSettingsMap(HashMap<Player, GameSettings> playerGameSettingsMap) {
		GameSettings.playerGameSettingsMap = playerGameSettingsMap;
	}
}
