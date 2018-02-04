package com.zinno.nim.events.player.security;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.zinno.nim.game.util.GameMaker;
import com.zinno.nim.util.CenteredText;

import net.md_5.bungee.api.ChatColor;

public class DropItem implements Listener {

	@EventHandler
	public void onEvent(PlayerDropItemEvent event) {
		if(!(GameMaker.checkPlayer(event.getPlayer().getName()))) {
			return;
		}
		Player player = event.getPlayer();
		player.sendMessage("");
		CenteredText.sendCenteredMessage(player, ChatColor.GOLD + " ----------  Dr. Nim  ---------- ");
		CenteredText.sendCenteredMessage(player, ChatColor.RED + "You thought you could just... drop that?!?");
		CenteredText.sendCenteredMessage(player,
				ChatColor.RED.toString() + ChatColor.ITALIC + "Read the tutorial book for rules");
		CenteredText.sendCenteredMessage(player, ChatColor.GOLD + "-- <> --");
		event.setCancelled(true);
	}

}
