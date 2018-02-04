package com.zinno.nim.events.player.move;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.zinno.nim.game.board.Board;
import com.zinno.nim.game.board.Chip;
import com.zinno.nim.game.game.Game;
import com.zinno.nim.game.util.GameMaker;
import com.zinno.nim.util.CenteredText;
import com.zinno.nim.util.Config;

import net.md_5.bungee.api.ChatColor;

public class Click implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEvent(PlayerInteractEvent event) {
		if(!(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {
			return;
		} else if(!(GameMaker.checkPlayer(event.getPlayer().getName()))) {
			return;
		} else if(event.getPlayer().getTargetBlock(null, 30) == null || event.getPlayer().getTargetBlock(null, 30).isEmpty()) {
			return;
		}
		
		Player player = event.getPlayer();
		Game game = GameMaker.getGame(player.getName());
		Board board = game.getBoard();
		if(!(game.getTurn().equals(player.getName()))) {
			CenteredText.sendCenteredMessage(player, ChatColor.GOLD + " ----------  Dr. Nim  ---------- ");
			CenteredText.sendCenteredMessage(player, ChatColor.RED + "It's not your turn!");
			CenteredText.sendCenteredMessage(player, ChatColor.RED.toString() + ChatColor.ITALIC + "Type /nim tut for a tutorial");
			CenteredText.sendCenteredMessage(player, ChatColor.GOLD + "-- <> --");
			return;
		}
		Block currentBlock = player.getTargetBlock(null, 30);
		if(!board.isChip(currentBlock.getLocation())) {
			return;
		}
		Chip chip = board.getChip(currentBlock.getLocation());
		if(!board.isLegalSelection(chip)) {
			chip.incorrectSelection();
			new BukkitRunnable() {
				@Override
				public void run() {
					chip.removeColor();
				}
			}.runTaskLater(Config.getPlugin(), 20);
			CenteredText.sendCenteredMessage(player, ChatColor.GOLD + " ----------  Dr. Nim  ---------- ");
			CenteredText.sendCenteredMessage(player, ChatColor.RED + "You can't move there");
			CenteredText.sendCenteredMessage(player, ChatColor.RED.toString() + ChatColor.ITALIC + "Read the tutorial book for rules");
			CenteredText.sendCenteredMessage(player, ChatColor.GOLD + "-- <> --");
			return;
		}
		
		if(chip.isSelected()) {
			chip.removeColor();
			board.checkForReset();
		} else {
			chip.selectChip();
		}
	}
}
