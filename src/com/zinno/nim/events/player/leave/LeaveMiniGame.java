package com.zinno.nim.events.player.leave;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.zinno.nim.events.player.leave.util.LeaveStorage;
import com.zinno.nim.game.util.GameMaker;
import com.zinno.nim.util.CenteredText;
import com.zinno.nim.util.Config;

import net.md_5.bungee.api.ChatColor;

public class LeaveMiniGame implements Listener {
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			return;
		} else if(!(event.getMaterial() == Material.ENDER_PEARL)) {
			return;
		}
		Player player = event.getPlayer();
		if(!(GameMaker.checkPlayer(player.getName()))) {
			return;
		}
		event.setCancelled(true);
		if(LeaveStorage.checkPlayer(player.getName())) {
			GameMaker.delGame(event.getPlayer().getName());
			LeaveStorage.delPlayer(player.getName());
			return;
		}
		CenteredText.sendCenteredMessage(player, ChatColor.GOLD + " ----------  Dr. Nim  ---------- ");
		CenteredText.sendCenteredMessage(player, ChatColor.RED + "Click the pearl again to leave");
		CenteredText.sendCenteredMessage(player,
				ChatColor.RED.toString() + ChatColor.ITALIC + "Type /nim tut for a tutorial");
		CenteredText.sendCenteredMessage(player, ChatColor.GOLD + "-- <> --");
		LeaveStorage.addPlayer(player.getName());
		new BukkitRunnable() {
			public void run() {
				LeaveStorage.delPlayer(player.getName());
			}
		}.runTaskLater(Config.getPlugin(), 100);
	}
}
