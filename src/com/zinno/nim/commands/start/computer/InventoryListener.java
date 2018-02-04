package com.zinno.nim.commands.start.computer;

import com.zinno.nim.game.util.GameMaker;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.zinno.nim.util.Config;

import java.util.Arrays;
import java.util.HashMap;

public class InventoryListener implements Listener {
	
	private final static int DELAY = 5;
	private Player player;
	private GameSettings gameSettings;
	
	@EventHandler
	public void onClickEvent(InventoryClickEvent event) {
		if(!(event.getWhoClicked() instanceof Player) ||
				!event.getInventory().getName().equals(InventoryMaker.getInv().getName()))
			return;
		
		event.setCancelled(true);
		
		this.player = (Player) event.getWhoClicked();
		
		if(GameSettings.getPlayerGameSettingsMap() == null ||
				!GameSettings.getPlayerGameSettingsMap().containsKey(player))
			this.gameSettings = new GameSettings();
		else
			this.gameSettings = GameSettings.getPlayerGameSettingsMap().get(player);
		
		switch (event.getSlot()) {
			case 10:
				setSelected(GameSettings.Types.DIFFICULTY, 10);
				gameSettings.setDifficulty(1);
				break;
			case 13:
				setSelected(GameSettings.Types.DIFFICULTY, 13);
				gameSettings.setDifficulty(2);
				break;
			case 16:
				setSelected(GameSettings.Types.DIFFICULTY, 16);
				gameSettings.setDifficulty(3);
				break;
			case 20:
				setSelected(GameSettings.Types.MAPSIZE, 20);
				gameSettings.setMapSize(3);
				break;
			case 24:
				setSelected(GameSettings.Types.MAPSIZE, 24);
				gameSettings.setMapSize(4);
				break;
		}
		
		HashMap<Player, GameSettings> playerGameSettingsMap = GameSettings.getPlayerGameSettingsMap();
		if (gameSettings.isComplete()) {
			runAntiCombatLog();
			beginGame();
			playerGameSettingsMap.remove(player);
		} else
			playerGameSettingsMap.put(player, gameSettings);
		GameSettings.setPlayerGameSettingsMap(playerGameSettingsMap);
	}
	
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent event) {
		if(!(event.getPlayer() instanceof Player) ||
				!event.getInventory().equals(InventoryMaker.getInv()))
			return;
		
		this.player = (Player) event.getPlayer();
		
		if(GameSettings.getPlayerGameSettingsMap() == null ||
				!GameSettings.getPlayerGameSettingsMap().containsKey(player))
			return;
		
		HashMap<Player, GameSettings> playerGameSettingsMap = GameSettings.getPlayerGameSettingsMap();
		playerGameSettingsMap.remove(player);
		GameSettings.setPlayerGameSettingsMap(playerGameSettingsMap);
	}
	
	private void runAntiCombatLog() {
		final byte[] color = new byte[] {DyeColor.YELLOW.getWoolData(), DyeColor.GREEN.getWoolData()};
		final Player currentPlayer = player;
		for(int c = 0; c < 18; c++) {
			final int cFinal = c; // Desperate times call for desperate measures
			final Inventory inventory = InventoryMaker.getInv();
			new BukkitRunnable() {
				@Override
				public void run() {
					ItemStack changeTile = new ItemStack(Material.STAINED_GLASS_PANE, 1, color[cFinal/9]);
					ItemMeta changeTileMeta = changeTile.getItemMeta();
					changeTileMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.ITALIC.toString() + ChatColor.BOLD.toString() + "Loading...");
					changeTile.setItemMeta(changeTileMeta);
					currentPlayer.getOpenInventory().setItem((cFinal%9) + 27, changeTile);
				}
			}.runTaskLater(Config.getPlugin(), DELAY*cFinal);
		}
	}
	
	private void beginGame() {
		final Player currentPlayer = player;
		new BukkitRunnable() {
				@Override
				public void run() {
					if(!currentPlayer.getOpenInventory().getTitle().equals(InventoryMaker.getInv().getTitle()))
						return;
					GameMaker.createGame(currentPlayer, gameSettings.getDifficulty(),
							gameSettings.getMapSize());
					currentPlayer.closeInventory();
				}
			}.runTaskLater(Config.getPlugin(), 18*DELAY + 10);
	}
	
	private void setSelected(GameSettings.Types gameType, int newSlot) {
		Inventory inv = InventoryMaker.getInv();
		for(GameSettings.Types type : GameSettings.Types.values()) { // Written strange for expansion
			if (type.equals(gameType))
				continue;
			int alternateTypeSlot = -1;
			switch (type) {
				case MAPSIZE:
					alternateTypeSlot = 8 + 4*gameSettings.getMapSize();
					break;
				case DIFFICULTY:
					alternateTypeSlot = 7 + 3*gameSettings.getDifficulty();
					break;
			}
			if(alternateTypeSlot == 7 || alternateTypeSlot == 8)
				continue;
			ItemStack alternateItem = inv.getItem(alternateTypeSlot);
			ItemMeta alternateMeta = alternateItem.getItemMeta();
			alternateMeta.setLore(Arrays.asList(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Selected"));
			alternateItem.setItemMeta(alternateMeta);
			inv.setItem(newSlot, alternateItem);
		}
		ItemStack newItem = inv.getItem(newSlot);
		ItemMeta newMeta = newItem.getItemMeta();
		newMeta.setLore(Arrays.asList(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Selected"));
		newItem.setItemMeta(newMeta);
		inv.setItem(newSlot, newItem);
		
		player.openInventory(inv);
	}

}
