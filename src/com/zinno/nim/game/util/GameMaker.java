package com.zinno.nim.game.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.zinno.nim.game.game.Game;
import com.zinno.nim.game.game.SinglePlayerGame;
import com.zinno.nim.game.game.TwoPlayerGame;

import net.md_5.bungee.api.ChatColor;

public final class GameMaker {
	private GameMaker() {}
	
	private static HashMap<List<String>, Game> activeGames = new HashMap<>();
	
	public static Game createGame(Player p1, int difficulty, int gameType) {
		Game game = new SinglePlayerGame(p1, calculateXLoc(), difficulty, gameType);
		activeGames.put(Arrays.asList(p1.getName().toLowerCase()), game);
		return game;
	}
	
	private static int calculateXLoc() {
		return (activeGames.size()-1)*1000;
	}

	public static Game createGame(Player p1, Player p2) {
		Game game = new TwoPlayerGame(p1, p2, calculateXLoc());
		activeGames.put(Arrays.asList(p1.getName().toLowerCase(), p2.getName().toLowerCase()), game);
		return game;
	}
	
	public static Game getGame(String name) {
		for(List<String> sList : activeGames.keySet()) {
			if(sList.contains(name.toLowerCase())) {
				return activeGames.get(sList);
			}
		}
		Bukkit.broadcast(ChatColor.RED + "Error: NIM Game for " + name + " could not be found", "nim.debug");
		return null;
	}
	
	public static boolean checkPlayer(String name) {
		for(List<String> sList : activeGames.keySet()) {
			if(sList.contains(name.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public static void delGame(String name) {
		for(List<String> sList : activeGames.keySet()) {
			if(sList.contains(name.toLowerCase())) {
				activeGames.get(sList).endGame(false);
				activeGames.remove(sList);
			}
		}
	}

	public static void delGame(String name, boolean isDone) {
		for(List<String> sList : activeGames.keySet()) {
			if(sList.contains(name.toLowerCase())) {
				activeGames.get(sList).endGame(true);
				activeGames.remove(sList);
			}
		}
	}
	
	public static void delAllGames() {
		for(Game game : activeGames.values()) {
			game.endGame(false);
		}
		activeGames = null;
	}
	
}
