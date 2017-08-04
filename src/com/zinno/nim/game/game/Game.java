package com.zinno.nim.game.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.zinno.nim.game.board.Board;
import com.zinno.nim.game.users.Computer;
import com.zinno.nim.game.users.User;
import com.zinno.nim.game.users.util.Helper;
import com.zinno.nim.game.util.GameMaker;
import com.zinno.nim.util.Config;

import net.md_5.bungee.api.ChatColor;

public abstract class Game {

	public User user1;
	public User user2;
	public List<User> uList = new ArrayList<User>();
	public Computer comp;
	public String turn;

	public Board board;

	public Game(int xLoc, int gameType) {
		this.board = new Board(xLoc, gameType);
	}

	public Game(int xLoc) {
		this.board = new Board(xLoc, 3);
	}

	public void readyPlayers() {
		int counter = 1;
		for (User u : uList) {
			u.clearPlayerInfo(counter);
			u.alert(ChatColor.GREEN + "Welcome to NIM", ChatColor.DARK_GREEN + "Developed By: Zinno");
			counter += 1;
		}
	}

	public void endGame(boolean isDone) {
		for (User u : uList) {
			u.returnPlayerInfo();
			u = null;
		}
		this.board.eraseAll();
		this.board = null;
	}

	public User getUser(String name) {
		for (User u : uList) {
			if (u.getPlayer().getName() == name) {
				return u;
			}
		}
		return null;
	}

	public Board getBoard() {
		return this.board;
	}

	public String getTurn() {
		return turn;
	}

	public void endTurn(User endTurnUser) {
		turn = "";
		board.update();
		if (uList.size() == 1) {
			if (Helper.getSize(board.getActiveChips()) == 0) {
				new BukkitRunnable() {
					public void run() {
						user1.alert(ChatColor.RED + "Sorry, you lost!", ChatColor.GOLD + "Play again soon!");
						GameMaker.delGame(user1.getPlayer().getName(), true);
					}
				}.runTaskLater(Config.getPlugin(), 20);
				return;
			}
			comp.beginTurn();
			turn = "cpu";
			return;
		}
		User beginTurnUser = uList.get((uList.indexOf(endTurnUser)+1)%uList.size());
		new BukkitRunnable() {
			public void run() {
				if (Helper.getSize(board.getActiveChips()) == 0) {
					endTurnUser.alert(ChatColor.RED + "Sorry, you lost!", ChatColor.GOLD + "Play again soon!");
					beginTurnUser.alert(ChatColor.GREEN + "Congratulations, you win!", ChatColor.GOLD + "Play again soon!");
					GameMaker.delGame(user1.getPlayer().getName(), true);
					this.cancel();
					return;
				}
				turn = beginTurnUser.getPlayer().getName();
				beginTurnUser.alert(ChatColor.GREEN + "It's your turn!", "");
				return;
			}
		}.runTaskLater(Config.getPlugin(), 40);
	}

	public void endTurn() {
		turn = "";
		new BukkitRunnable() {
			public void run() {
				board.update();
				if (Helper.getSize(board.getActiveChips()) == 0) {
					new BukkitRunnable() {
						public void run() {
							user1.alert(ChatColor.GREEN + "Congratulations, you win!",
									ChatColor.GOLD + "Play again soon!");
							GameMaker.delGame(user1.getPlayer().getName(), true);
						}
					}.runTaskLater(Config.getPlugin(), 20);
					return;
				}
				turn = user1.getPlayer().getName();
				user1.getPlayer().sendTitle(ChatColor.GREEN + "It's your turn!", "", 5, 10, 5);
			}
		}.runTaskLater(Config.getPlugin(), 40);
	}

}
