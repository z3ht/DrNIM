package com.zinno.nim.commands.start;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zinno.nim.commands.SubCommand;
import com.zinno.nim.commands.start.util.NIMvites;
import com.zinno.nim.game.util.GameMaker;

public class Accept implements SubCommand {

	@Override
	public void runCommand(CommandSender sender, Command cmd, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command");
			return;
		}
		Player player = (Player) sender;
		if(GameMaker.checkPlayer(player.getName())) {
			player.sendMessage(ChatColor.RED + "You can only play one NIM game at a time");
			return;
		}
		String originalPlayer = NIMvites.getNimInvs(player.getName());
		if(GameMaker.checkPlayer(originalPlayer)) {
			player.sendMessage(ChatColor.RED + "The player who invited you has already begun a game");
			return;
		}
		if(NIMvites.getNimInvs(player.getName())==null) {
			player.sendMessage(ChatColor.RED + "You have no active NIM invites");
			return;
		}
		NIMvites.delNimInvs(player.getName());
		Player originalP = Bukkit.getPlayer(originalPlayer);
		GameMaker.createGame(originalP, player);
	}

}
