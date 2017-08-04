package com.zinno.nim.events.player.security;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.zinno.nim.game.util.GameMaker;

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onEvent(InventoryClickEvent event ) {
		if(!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getWhoClicked();
		if(!(GameMaker.checkPlayer(player.getName()))) {
			return;
		}
		event.setCancelled(true);
	}
}
