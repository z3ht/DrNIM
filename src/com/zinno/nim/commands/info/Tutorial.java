package com.zinno.nim.commands.info;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.zinno.nim.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;

public class Tutorial {

	public static ItemStack createTutBook() {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		bookMeta.addPage( ChatColor.GOLD.toString() + ChatColor.BOLD + "Welcome to NIM" + ChatColor.RESET + "\n"
				+ ChatColor.GRAY + "Developed By: Zinno" + ChatColor.RESET + "\n" + "\n" + ChatColor.BLUE
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
				+ "  - This version of NIM was first released on July 23, 2017");
		bookMeta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Dr. NIM");
		bookMeta.setAuthor("Zinno");
		book.setItemMeta(bookMeta);
		return book;
	}
}
