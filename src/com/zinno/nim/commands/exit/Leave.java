package com.zinno.nim.commands.exit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zinno.nim.commands.SubCommand;
import com.zinno.nim.game.util.GameMaker;

import net.md_5.bungee.api.ChatColor;

public class Leave implements SubCommand {

	@Override
	public void runCommand(CommandSender sender, Command cmd, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command");
			return;
		} Player player = (Player) sender;
		if(!GameMaker.checkPlayer(player.getName())) {
			player.sendMessage(ChatColor.RED + "You're not playing any NIM games");
			return;
		}
		GameMaker.delGame(player.getName());
	}

}
