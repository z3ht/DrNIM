package com.zinno.nim.commands.start;

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
			sender.sendMessage(ChatColor.RED + "Only players can use this command");
			return;
		}
		Player player = (Player) sender;
		
		if(NIMvites.killNimInv(player.getName(), args[1])) {
			player.sendMessage(ChatColor.GREEN + "The invitation has been deleted!");
			return;
		}
		player.sendMessage(ChatColor.RED + "The invitation could not be found or already expired");
	}

}
