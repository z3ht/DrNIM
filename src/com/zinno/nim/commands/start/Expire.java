package com.zinno.nim.commands.start;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zinno.nim.commands.SubCommand;
import com.zinno.nim.commands.start.util.NIMvites;

import net.md_5.bungee.api.ChatColor;

public class Expire implements SubCommand {

	@Override
	public void runCommand(CommandSender sender, Command cmd, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
					+ ChatColor.RED + "Only players can use this command");
			return;
		}
		Player player = (Player) sender;
		
		if(NIMvites.killNimInv(player.getName(), args[1])) {
			player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
					+ ChatColor.GREEN + "The invitation has been deleted!");
			Bukkit.getPlayer(args[1]).sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
					+ ChatColor.GOLD + "The invitation from " + player.getName() + " has expired!");
			return;
		}
		player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
				+ ChatColor.RED + "The invitation could not be found or already expired");
	}

}
