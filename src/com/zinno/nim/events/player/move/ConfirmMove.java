package com.zinno.nim.events.player.move;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.zinno.nim.game.board.Board;
import com.zinno.nim.game.game.Game;
import com.zinno.nim.game.users.User;
import com.zinno.nim.game.util.GameMaker;
import com.zinno.nim.util.CenteredText;

import net.md_5.bungee.api.ChatColor;

public class ConfirmMove implements Listener {
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			return;
		} else if(!(event.getMaterial() == Material.BLAZE_POWDER)) {
			return;
		}
		Player player = event.getPlayer();
		if(!(GameMaker.checkPlayer(player.getName()))) {
			return;
		}
		Game game = GameMaker.getGame(player.getName());
		if(!(game.getTurn().equals(player.getName()))) {
			CenteredText.sendCenteredMessage(player, ChatColor.GOLD + " ----------  Dr. Nim  ---------- ");
			CenteredText.sendCenteredMessage(player, ChatColor.RED + "It's not your turn!");
			CenteredText.sendCenteredMessage(player,
					ChatColor.RED.toString() + ChatColor.ITALIC + "Read the tutorial book for rules");
			CenteredText.sendCenteredMessage(player, ChatColor.GOLD + "-- <> --");
			return;
		}
		User user = game.getUser(player.getName());
		Board board = game.getBoard();
		if(!board.isLegalMove()) {
			CenteredText.sendCenteredMessage(player, ChatColor.GOLD + " ----------  Dr. Nim  ---------- ");
			CenteredText.sendCenteredMessage(player, ChatColor.RED + "That is not a legal move");
			CenteredText.sendCenteredMessage(player,
					ChatColor.RED.toString() + ChatColor.ITALIC + "Read the tutorial book for rules");
			CenteredText.sendCenteredMessage(player, ChatColor.GOLD + "-- <> --");
			return;
		}
		user.inputMove(board.getSelectedChips());
	}

}
