package com.zinno.nim.events.player.security;

import com.zinno.nim.game.game.Game;
import com.zinno.nim.game.util.GameMaker;
import com.zinno.nim.util.CenteredText;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreProcess implements Listener {
	
	@EventHandler
	public void onCommandPreProccess(PlayerCommandPreprocessEvent event) {
		if(!(GameMaker.checkPlayer(event.getPlayer().getName())))
			return;
		if(event.getMessage().toLowerCase().contains("/nim"))
			return;
		GameMaker.delGame(event.getPlayer().getName());
		Player player = event.getPlayer();
		player.sendMessage("");
		CenteredText.sendCenteredMessage(player, ChatColor.GOLD + " ----------  Dr. Nim  ---------- ");
		CenteredText.sendCenteredMessage(player, ChatColor.RED + "Command detected...");
		CenteredText.sendCenteredMessage(player,
				ChatColor.RED.toString() + ChatColor.ITALIC + "Exiting game");
		CenteredText.sendCenteredMessage(player, ChatColor.GOLD + "-- <> --");
		event.setCancelled(true);
	}
	
}
