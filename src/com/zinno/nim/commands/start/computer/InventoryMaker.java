package com.zinno.nim.commands.start.computer;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.zinno.nim.commands.start.computer.util.InventoryStorage;

import net.md_5.bungee.api.ChatColor;

public class InventoryMaker implements Listener {
	
	public void createInventory(Player player) {
		Inventory inv = Bukkit.createInventory(player, 36, ChatColor.BOLD + "Dr. NIM");
		
		ItemStack barFiller = new ItemStack(Material.RAILS, 1);
		ItemMeta barFillerMeta = barFiller.getItemMeta();
		barFillerMeta.setDisplayName(ChatColor.BLACK + "|");
		barFiller.setItemMeta(barFillerMeta);
		for(int c = 0; c < inv.getSize(); c+=9) {
			inv.setItem(c, barFiller);
			inv.setItem(c + 8, barFiller);
		}
		
		ItemStack glassFiller = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIGHT_BLUE.getWoolData());
		ItemMeta glassFillerMeta = glassFiller.getItemMeta();
		glassFillerMeta.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Dr. NIM");
		glassFiller.setItemMeta(glassFillerMeta);
		for(int c = 0; c < 9; c++)
			inv.setItem(c, glassFiller);
		
		ItemStack progressGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.PINK.getWoolData());
		ItemMeta progressGlassMeta = progressGlass.getItemMeta();
		progressGlassMeta.setDisplayName(ChatColor.BLACK + "|");
		progressGlass.setItemMeta(progressGlassMeta);
		for(int c = inv.getSize() - 9; c < inv.getSize(); c++)
			inv.setItem(c, progressGlass);
		
		ItemStack easy = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getWoolData());
		ItemMeta easyMeta = easy.getItemMeta();
		easyMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Easy");
		easy.setItemMeta(easyMeta);
		inv.setItem(10, easy);
		
		ItemStack medium = new ItemStack(Material.WOOL, 1, DyeColor.YELLOW.getWoolData());
		ItemMeta mediumMeta = medium.getItemMeta();
		mediumMeta.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Medium");
		medium.setItemMeta(mediumMeta);
		inv.setItem(13, medium);
		
		ItemStack hard = new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());
		ItemMeta hardMeta = hard.getItemMeta();
		hardMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Hard");
		hard.setItemMeta(hardMeta);
		inv.setItem(16, hard);
		
		ItemStack three = new ItemStack(Material.CONCRETE, 1, DyeColor.ORANGE.getWoolData());
		ItemMeta threeMeta = hard.getItemMeta();
		threeMeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD + "3x5x7");
		three.setItemMeta(threeMeta);
		inv.setItem(20, three);
		
		ItemStack four = new ItemStack(Material.CONCRETE, 1, DyeColor.CYAN.getWoolData());
		ItemMeta fourMeta = hard.getItemMeta();
		fourMeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD + "1x3x5x7");
		four.setItemMeta(fourMeta);
		inv.setItem(24, four);
		
		InventoryStorage.setPlayerInv(player.getName(), inv);
		InventoryStorage.setPlayerDifficulty(player.getName(), 0);
		InventoryStorage.setPlayerGameType(player.getName(), 0);
		
		player.openInventory(inv);
	}
}
