package com.zinno.nim.game.game;

import org.bukkit.entity.Player;

import com.zinno.nim.game.users.User;

public class TwoPlayerGame extends Game {

	public TwoPlayerGame(Player player1, Player player2, int xLoc) {
		super(xLoc);
		this.user1 = new User(player1, this.board, this, xLoc);
		this.user2 = new User(player2, this.board, this, xLoc);
		this.uList.add(user1);
		this.uList.add(user2);
		int randomNum = (int) Math.random()*uList.size();
		this.turn = uList.get(randomNum).getPlayer().getName();
		readyPlayers();
	}
	
}
