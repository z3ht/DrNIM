package com.zinno.nim.events.player.security;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.zinno.nim.game.util.GameMaker;

public class PlayerMove implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEvent(PlayerMoveEvent event) {
		if (!GameMaker.checkPlayer(event.getPlayer().getName())) {
			return;
		}
		if (event.getFrom().getX() != event.getTo().getX()
				|| event.getFrom().getY() != event.getTo().getY()
				|| event.getFrom().getZ() != event.getTo().getZ()) {
			event.setCancelled(true);
			Player player = event.getPlayer();
			Location standingOnLoc = player.getLocation();
			standingOnLoc.setY(standingOnLoc.getY()-1);
			Block standingOnBlock = standingOnLoc.getBlock();
			if(standingOnBlock.getTypeId() == 0) {
				if(player.getAllowFlight()) {
					player.setFlying(true);
				} else {
					standingOnBlock.setTypeId(166);
				}
			}
			
		}
	}

}
