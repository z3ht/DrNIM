package com.zinno.nim.commands.start.computer;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.zinno.nim.commands.start.computer.util.InventoryStorage;
import com.zinno.nim.game.util.GameMaker;
import com.zinno.nim.util.Config;

import java.util.Arrays;

public class InventoryListener implements Listener {

	Player player;

	@EventHandler
	public void onClickEvent(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		this.player = (Player) event.getWhoClicked();
		if (!(event.getInventory().getName().equals(InventoryStorage.getPlayerInv(player.getName()).getName()))) {
			return;
		}
		event.setCancelled(true);
		switch (event.getSlot()) {
		case 10:
			addEnchant("Difficulty", 10);
			InventoryStorage.setPlayerDifficulty(player.getName(), 1);
			if (checkInfo()) {
				player.closeInventory();
				return;
			}
			break;
		case 13:
			addEnchant("Difficulty", 13);
			InventoryStorage.setPlayerDifficulty(player.getName(), 2);
			if (checkInfo()) {
				player.closeInventory();
				return;
			}
			break;
		case 16:
			addEnchant("Difficulty", 16);
			InventoryStorage.setPlayerDifficulty(player.getName(), 3);
			if (checkInfo()) {
				player.closeInventory();
				return;
			}
			break;
		case 20:
			addEnchant("Game type", 20);
			InventoryStorage.setPlayerGameType(player.getName(), 3);
			if (checkInfo()) {
				player.closeInventory();
				return;
			}
			break;
		case 24:
			addEnchant("Game type", 24);
			InventoryStorage.setPlayerGameType(player.getName(), 4);
			if (checkInfo()) {
				player.closeInventory();
				return;
			}
			break;
		}
	}

	private boolean checkInfo() {
		if (InventoryStorage.getPlayerDifficulty(player.getName()) != 0
				&& InventoryStorage.getPlayerGameType(player.getName()) != 0) {
			final byte[] color = new byte[] {DyeColor.YELLOW.getWoolData(), DyeColor.GREEN.getWoolData()};
			for(int c = 0; c < 18; c++) {
				final int cFinal = c; // Desperate times call for desperate measures
				new BukkitRunnable() {
					@Override
					public void run() {
						ItemStack changeTile = new ItemStack(Material.STAINED_GLASS_PANE, 1, color[cFinal/9]);
						ItemMeta changeTileMeta = changeTile.getItemMeta();
						changeTileMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.ITALIC.toString() + ChatColor.BOLD.toString() + "Loading...");
						changeTile.setItemMeta(changeTileMeta);
						InventoryStorage.getPlayerInv(player.getName()).setItem((cFinal%9) + 27, changeTile);
						player.openInventory(InventoryStorage.getPlayerInv(player.getName()));
					}
				}.runTaskLater(Config.getPlugin(), 5*cFinal);
			}
			new BukkitRunnable() {
				@Override
				public void run() {
					GameMaker.createGame(player, InventoryStorage.getPlayerDifficulty(player.getName()),
							InventoryStorage.getPlayerGameType(player.getName()));
				}
			}.runTaskLater(Config.getPlugin(), 90);
			return true;
		}
		return false;
	}
	
	private void addEnchant(String type, int slot) {
		Inventory inv = InventoryStorage.getPlayerInv(player.getName());
		ItemStack newItem = inv.getItem(slot);
		int activeDifficulty = 7 + (3* InventoryStorage.getPlayerDifficulty(player.getName()));
		int activeGameType = 8 + (4* InventoryStorage.getPlayerGameType(player.getName()));
		if (type.equalsIgnoreCase("Game Type")) {
			if (activeGameType != 8) {
				ItemStack oldItem = inv.getItem(activeGameType);
				ItemMeta oldMeta = oldItem.getItemMeta();
				oldMeta.setLore(null);
				oldItem.setItemMeta(oldMeta);
				inv.setItem(activeGameType, oldItem);
			}
			ItemMeta newMeta = newItem.getItemMeta();
			newMeta.setLore(Arrays.asList(ChatColor.GRAY.toString() + ChatColor.ITALIC + ChatColor.ITALIC + "Selected"));
			newItem.setItemMeta(newMeta);
			inv.setItem(slot, newItem);
		} else if (type.equalsIgnoreCase("Difficulty")) {
			if (activeDifficulty != 7) {
				ItemStack oldItem = inv.getItem(activeDifficulty);
				ItemMeta oldMeta = oldItem.getItemMeta();
				oldMeta.setLore(null);
				oldItem.setItemMeta(oldMeta);
				inv.setItem(activeDifficulty, oldItem);
			}
			ItemMeta newMeta = newItem.getItemMeta();
			newMeta.setLore(Arrays.asList(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Selected"));
			newItem.setItemMeta(newMeta);
			inv.setItem(slot, newItem);
		}

	}

}
