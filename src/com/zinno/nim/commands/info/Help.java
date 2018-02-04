package com.zinno.nim.commands.info;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zinno.nim.commands.start.computer.util.InventoryStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zinno.nim.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Help implements SubCommand, Listener {
	
	private Inventory inventory;
	
	@EventHandler
	public void onClickEvent(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player) ||
				!(event.getInventory().getName().equals(inventory.getName()))) {
			return;
		}
		event.setCancelled(true);
	}

	public void runCommand(CommandSender sender, Command cmd, String[] args) {
		if(!(sender instanceof Player)) {
			String[] commandList = new String[] {
					"/nim start",
					"/nim start <OpponentName>",
					"/nim accept",
					"/nim expire",
					"/nim leave",
					"/nim help"
			};
			sender.sendMessage(ChatColor.DARK_AQUA + "Available commands:");
			for(String command : commandList) {
				sender.sendMessage(ChatColor.AQUA + "    " + command);
			}
			return;
		}
		Player player = (Player) sender;
		this.inventory = Bukkit.createInventory(player, 9, ChatColor.BOLD + "NIM Commands:");
		ItemStack starFiller = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta starFillerMeta = starFiller.getItemMeta();
		starFillerMeta.setDisplayName(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "HELP");
		starFiller.setItemMeta(starFillerMeta);
		inventory.setItem(0, starFiller);
		inventory.setItem(inventory.getSize() - 1, starFiller);
		
		HelpItem[] helpItems = new HelpItem[] {
				new HelpItem("/NIM", "The base of all NIM related commands", "/nim"),
				new HelpItem("/NIM Start", "Begin a NIM game with the computer", "/nim start"),
				new HelpItem("/NIM Invite <Opponent Name>", "Send a NIM game invitation to an online opponent", null),
				new HelpItem("/NIM Accept", "Accept a NIM game invitation", "/nim accept"),
				new HelpItem("/NIM Expire <Invite Name>", "Delete a NIM game invitation", null),
				new HelpItem("/NIM Leave", "Leave the current NIM game", "/nim leave"),
				new HelpItem("/NIM Help", "List of available NIM commands", "/nim help")
		};
		
		for(int c = 0; c < helpItems.length; c++) {
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) (c % 10));
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + helpItems[c].getName());
			itemMeta.setLore(Arrays.asList(ChatColor.GRAY + helpItems[c].getInfo()));
			item.setItemMeta(itemMeta);
			inventory.setItem(Math.abs(helpItems.length - inventory.getSize())/2 + c, item);
		}
		
		player.openInventory(inventory);
	}

}
