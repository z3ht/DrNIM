package com.zinno.nim.commands.info;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.zinno.nim.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;

public class Tutorial implements SubCommand {

	@Override
	public void runCommand(CommandSender sender, Command cmd, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use this command");
			return;
		}
		Player player = (Player) sender;
		if(!(player.hasPermission("nim.tutorial"))) {
			player.sendMessage(ChatColor.RED + "No Permission");
			return;
		}
		if(player.getInventory().addItem(Tutorial.createTutBook()).isEmpty()) {
			player.sendMessage(ChatColor.GREEN + "A tutorial book has been sent to your inventory");
		} else {
			player.sendMessage(ChatColor.RED + "No open inventory slots found for tutorial book");
			return;
		}
		
	}

	public static ItemStack createTutBook() {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		bookMeta.addPage(ChatColor.BOLD.toString() + ChatColor.LIGHT_PURPLE + "Welcome to NIM!" + ChatColor.RESET + "\n"
				+ ChatColor.DARK_PURPLE + "Developed By: Zinno" + ChatColor.RESET + "\n" + "\n" + ChatColor.BLUE
				+ "Objective:" + ChatColor.RESET + "\n" + "Force the other player to be the last to remove a piece");
		bookMeta.addPage(ChatColor.BLUE + "Rules:" + ChatColor.RESET + "\n"
				+ "  - You may only remove pieces from one row at a time" + "\n"
				+ "  - You may remove as many pieces from a single row as you want" + "\n"
				+ "  - You must always remove at least one piece" + "\n" 
				+ "  - You may not skip your turn");
		bookMeta.addPage(ChatColor.BLUE + "How to:" + ChatColor.RESET + "\n" +
				ChatColor.BOLD + "  Remove a piece:" + "\n" + ChatColor.RESET +
				"Simply hover over the desired piece and left click" + "\n" +
				ChatColor.BOLD + "  End a turn: " + "\n" + ChatColor.RESET +
				"Right click on the blaze powder in your inventory" + "\n"
				+ ChatColor.BOLD + "  Leave the game:" + "\n" + ChatColor.RESET + 
				"Double right click on the ender pearl in your inventory");
		bookMeta.addPage(ChatColor.BLUE + "Additional Info:" + ChatColor.RESET + "\n"
				+ "  - NIM originated from China as early as the 15th century" + "\n"
				+ "  - NIM is not a game based on luck, but instead XOR logic" + "\n"
				+ "  - This version of NIM was first released 7/23/17");
		bookMeta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Dr. NIM");
		bookMeta.setAuthor("Zinno");
		book.setItemMeta(bookMeta);
		return book;
	}
}
