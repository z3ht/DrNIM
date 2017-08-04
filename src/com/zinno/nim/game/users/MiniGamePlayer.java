package com.zinno.nim.game.users;

import com.zinno.nim.game.board.Board;
import com.zinno.nim.game.game.Game;

public class MiniGamePlayer {
	
	Game game;
	Board board;
	
	public MiniGamePlayer(Game game, Board board) {
		this.game  = game;
		this.board = board;
	}
	
}
