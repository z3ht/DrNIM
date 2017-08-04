package com.zinno.nim.game.game;

import org.bukkit.entity.Player;

import com.zinno.nim.game.users.Computer;
import com.zinno.nim.game.users.User;

public class SinglePlayerGame extends Game {
	
	public SinglePlayerGame(Player p1, int xLoc, int difficulty, int gameType) {
		super(xLoc, gameType);
		this.user1 = new User(p1, this.board, this, xLoc);
		this.comp = new Computer(this, this.board, (byte) difficulty);
		this.uList.add(this.user1);
		this.turn = user1.getPlayer().getName();
		readyPlayers();
	}

}
