package com.zinno.nim.commands.start.computer;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.zinno.nim.commands.start.computer.util.ComputerGameStorage;
import com.zinno.nim.game.util.GameMaker;
import com.zinno.nim.util.Config;

public class ComputerOptions implements Listener {

	Player player;

	@EventHandler
	public void onClickEvent(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		this.player = (Player) event.getWhoClicked();
		if (!(event.getInventory().getName().equals(ComputerGameStorage.getPlayerInv(player.getName()).getName()))) {
			return;
		}
		event.setCancelled(true);
		switch (event.getSlot()) {
		case 10:
			addEnchant("Difficulty", 10);
			ComputerGameStorage.setPlayerDifficulty(player.getName(), 1);
			if (checkInfo()) {
				player.closeInventory();
				return;
			}
			break;
		case 13:
			addEnchant("Difficulty", 13);
			ComputerGameStorage.setPlayerDifficulty(player.getName(), 2);
			if (checkInfo()) {
				player.closeInventory();
				return;
			}
			break;
		case 16:
			addEnchant("Difficulty", 16);
			ComputerGameStorage.setPlayerDifficulty(player.getName(), 3);
			if (checkInfo()) {
				player.closeInventory();
				return;
			}
			break;
		case 20:
			addEnchant("Game type", 20);
			ComputerGameStorage.setPlayerGameType(player.getName(), 3);
			if (checkInfo()) {
				player.closeInventory();
				return;
			}
			break;
		case 24:
			addEnchant("Game type", 24);
			ComputerGameStorage.setPlayerGameType(player.getName(), 4);
			if (checkInfo()) {
				player.closeInventory();
				return;
			}
			break;
		}
	}

	private boolean checkInfo() {
		if (ComputerGameStorage.getPlayerDifficulty(player.getName()) != 0
				&& ComputerGameStorage.getPlayerGameType(player.getName()) != 0) {
			new BukkitRunnable() {
				@Override
				public void run() {
					GameMaker.createGame(player, ComputerGameStorage.getPlayerDifficulty(player.getName()),
							ComputerGameStorage.getPlayerGameType(player.getName()));
				}
			}.runTaskLater(Config.getPlugin(), 5);
			return true;
		}
		return false;
	}
	
	private void addEnchant(String type, int slot) {
		Inventory inv = ComputerGameStorage.getPlayerInv(player.getName());
		ItemStack newItem = inv.getItem(slot);
		int activeDifficulty = 7 + (3*ComputerGameStorage.getPlayerDifficulty(player.getName()));
		int activeGameType = 8 + (4*ComputerGameStorage.getPlayerGameType(player.getName()));
		if (type.equalsIgnoreCase("Game Type")) {
			if (activeGameType != 8) {
				ItemStack oldItem = inv.getItem(activeGameType);
				ItemMeta oldMeta = oldItem.getItemMeta();
				oldMeta.removeEnchant(Enchantment.LUCK);
				oldItem.setItemMeta(oldMeta);
				inv.setItem(activeGameType, oldItem);
			}
			ItemMeta newMeta = newItem.getItemMeta();
			newMeta.addEnchant(Enchantment.LUCK, 1, false);
			newItem.setItemMeta(newMeta);
			inv.setItem(slot, newItem);
		} else if (type.equalsIgnoreCase("Difficulty")) {
			if (activeDifficulty != 7) {
				ItemStack oldItem = inv.getItem(activeDifficulty);
				ItemMeta oldMeta = oldItem.getItemMeta();
				oldMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
				oldItem.setItemMeta(oldMeta);
				inv.setItem(activeDifficulty, oldItem);
			}
			ItemMeta newMeta = newItem.getItemMeta();
			newMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
			newItem.setItemMeta(newMeta);
			inv.setItem(slot, newItem);
		}

	}

}
