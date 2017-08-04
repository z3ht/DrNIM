package com.zinno.nim.commands;

import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zinno.nim.util.CenteredText;

import net.md_5.bungee.api.ChatColor;

public class NIM implements CommandExecutor {
	
	private static HashMap<List<String>, SubCommand> subCommand = new HashMap<List<String>, SubCommand>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!sender.hasPermission("nim.nim")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use NIM");
			return true;
		}
		
		if(args.length == 0) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Only players can access this command");
				return true;
			}
			Player player = (Player) sender;
			CenteredText.sendCenteredMessage(player, ChatColor.LIGHT_PURPLE + "---------------------");
			CenteredText.sendCenteredMessage(player, ChatColor.AQUA + "Dr. NIM");
			CenteredText.sendCenteredMessage(player, ChatColor.AQUA + "Designed By: Zinno");
			CenteredText.sendCenteredMessage(player, ChatColor.LIGHT_PURPLE + "---------------------");
			player.sendMessage("");
			player.sendMessage(ChatColor.GOLD + "Pssst... Type /nim help for a list of commands");
			return true;
		}
		
		for(List<String> sList : subCommand.keySet()) {
			if(sList.contains(args[0].toLowerCase())) {
				subCommand.get(sList).runCommand(sender, cmd, args);
				return true;
			}
		}
		sender.sendMessage(ChatColor.RED + "The command could not be found");	
		return true;
	}
	
	public static void addCommand(List<String> list, SubCommand cmd) {
		subCommand.put(list, cmd);
	}
	
}
