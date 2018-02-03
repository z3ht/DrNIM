package com.zinno.nim.commands;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.zinno.nim.util.CenteredText;

import net.md_5.bungee.api.ChatColor;

public class NIM implements CommandExecutor, TabCompleter {
	
	private static HashMap<List<String>, SubCommand> subCommand = new HashMap<List<String>, SubCommand>();
	private static List<String> tabCompleteList =  new ArrayList<>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!sender.hasPermission("nim.nim")) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use the NIM plugin");
			return true;
		}
		
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
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
		
		for (List<String> sList : subCommand.keySet()) {
			if (sList.contains(args[0].toLowerCase())) {
				subCommand.get(sList).runCommand(sender, cmd, args);
				return true;
			}
		}
		
		String intendedItem = null;
		for (String tabCompleteItem : tabCompleteList) {
			if (tabCompleteItem.toLowerCase().startsWith(args[0].toLowerCase())) {
				if (intendedItem != null) {
					sender.sendMessage(ChatColor.RED + "The command could not be found");
					return true;
				}
				intendedItem = tabCompleteItem;
			}
		}
		if (intendedItem == null) {
			sender.sendMessage(ChatColor.RED + "The command could not be found");
			return true;
		}
		Bukkit.dispatchCommand(sender, "nim " + intendedItem + " " + args.toString().substring(args[0].length() + args[1].length() + 1));
		return true;
	}
	
	public static void addCommand(List<String> list, SubCommand cmd) {
		subCommand.put(list, cmd);
		tabCompleteList.addAll(list);
		Collections.sort(tabCompleteList);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 1)
			return null;
		if(args.length == 0)
			return tabCompleteList;
		List<String> cutList = new ArrayList<String>();
		for(String item : tabCompleteList) {
			if(item.startsWith(args[0].toLowerCase()))
				cutList.add(item);
		}
		if(cutList.isEmpty())
			return null;
		return cutList;
	}
	
}
