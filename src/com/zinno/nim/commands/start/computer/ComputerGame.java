package com.zinno.nim.commands.start.computer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.zinno.nim.commands.start.computer.util.ComputerGameStorage;

import net.md_5.bungee.api.ChatColor;

public class ComputerGame implements Listener {
	
	public void createInventory(Player player) {
		Inventory inv = Bukkit.createInventory(player, 27, "Dr. NIM");
		
		ItemStack info = new ItemStack(Material.MAP, 1);
		ItemMeta infoMeta = info.getItemMeta();
		infoMeta.setDisplayName(ChatColor.GREEN + "Select a difficulty and a map size to begin");
		info.setItemMeta(infoMeta);
		
		inv.setItem(4, info);
		
		ItemStack easy = new ItemStack(Material.COAL, 1);
		ItemMeta easyMeta = easy.getItemMeta();
		easyMeta.setDisplayName(ChatColor.GRAY + "Easy");
		easy.setItemMeta(easyMeta);
		
		ItemStack medium = new ItemStack(Material.GOLD_INGOT, 1);
		ItemMeta mediumMeta = medium.getItemMeta();
		mediumMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.ITALIC + "Medium");
		medium.setItemMeta(mediumMeta);
		
		ItemStack hard = new ItemStack(Material.DIAMOND, 1);
		ItemMeta hardMeta = hard.getItemMeta();
		hardMeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + ChatColor.ITALIC + "Hard");
		hard.setItemMeta(hardMeta);
		
		inv.setItem(10, easy);
		inv.setItem(13, medium);
		inv.setItem(16, hard);
		
		ItemStack three = new ItemStack(Material.ANVIL, 1);
		ItemMeta threeMeta = hard.getItemMeta();
		threeMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "3x5x7");
		three.setItemMeta(threeMeta);
		
		ItemStack four = new ItemStack(Material.ANVIL, 1);
		ItemMeta fourMeta = hard.getItemMeta();
		fourMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "1x3x5x7");
		four.setItemMeta(fourMeta);
		
		inv.setItem(20, three);
		inv.setItem(24, four);
		
		ComputerGameStorage.setPlayerInv(player.getName(), inv);
		ComputerGameStorage.setPlayerDifficulty(player.getName(), 0);
		ComputerGameStorage.setPlayerGameType(player.getName(), 0);
		
		player.openInventory(inv);
	}
}
