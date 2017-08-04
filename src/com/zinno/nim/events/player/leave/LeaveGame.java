package com.zinno.nim.events.player.leave;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.zinno.nim.game.util.GameMaker;

public class LeaveGame implements Listener {
	
	@EventHandler
	public void kickEvent(PlayerKickEvent event) {
		if(!GameMaker.checkPlayer(event.getPlayer().getName())) {
			return;
		}
		GameMaker.delGame(event.getPlayer().getName());
	}
	
	@EventHandler
	public void quitEvent(PlayerQuitEvent event) {
		if(!GameMaker.checkPlayer(event.getPlayer().getName())) {
			return;
		}
		GameMaker.delGame(event.getPlayer().getName());
	}
}
