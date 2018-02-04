package com.zinno.nim.commands.start;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.zinno.nim.commands.SubCommand;
import com.zinno.nim.commands.start.computer.InventoryMaker;
import com.zinno.nim.commands.start.util.NIMvites;
import com.zinno.nim.game.util.GameMaker;
import com.zinno.nim.util.CenteredText;

import net.md_5.bungee.api.ChatColor;

public class Start implements SubCommand {
	
	public void runCommand(CommandSender sender, Command cmd, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
					+ ChatColor.RED + "Only players can use this command");
			return;
		}
		Player player = (Player) sender;
		if(!player.hasPermission("nim.start")) {
			player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
					+ ChatColor.RED + "You don't have permission to start a game of NIM");
			return;
		}
		if(GameMaker.checkPlayer(player.getName())) {
			player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
					+ ChatColor.RED + "You can only play one NIM game at a time");
			return;
		}
		switch(args.length) {
		case 1:
			player.openInventory(InventoryMaker.getInv());
			return;
		case 2:
			if(args[1].equalsIgnoreCase(player.getName())) {
				player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
						+ ChatColor.RED + "You can not send invites to yourself.");
				player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
						+ ChatColor.RED + "Type /nim start to play against the computer");
				return;
			}
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.getName().equalsIgnoreCase(args[1])) {
					NIMvites.addNimInvs(player.getName(), p.getName());
					CenteredText.sendCenteredMessage(p, ChatColor.GOLD + "  --  Dr NIM:  --  ");
					CenteredText.sendCenteredMessage(p, ChatColor.GOLD.toString() + player.getName() + " has challenged you to a game of NIM");
					CenteredText.sendCenteredMessage(p, ChatColor.GOLD + "Type: /NIM accept to accept the game invite");
					player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
							+ ChatColor.GOLD + "The game invite has been sent");
					player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
							+ ChatColor.GOLD + "Type /NIM expire <Invited Player Name> to delete the invitation");
					player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
							+ ChatColor.GOLD + "It will expire in 60 seconds");
					new BukkitRunnable() {
				        
			            @Override
			            public void run() {
			            	if(NIMvites.killNimInv(player.getName(), p.getName())) {
								player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
										+ ChatColor.GOLD + "The NIM invitation to " + p.getName() + " has expired");
								p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
										+ ChatColor.GOLD + "The NIM invitation from " + player.getName() + " has expired");
								return;
							}
			            }
			            
			        }.runTaskLater(Bukkit.getPluginManager().getPlugin("DrNIM"), 1200);
					return;
				}
			}
			player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW.toString() + ChatColor.BOLD + "NIM" + ChatColor.DARK_GRAY + "] "
					+ ChatColor.RED + "The player could not be found");
			return;
		}
	}
	
	

}
