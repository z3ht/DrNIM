package com.zinno.nim.game.users;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import com.zinno.nim.commands.info.Tutorial;
import com.zinno.nim.game.board.Board;
import com.zinno.nim.game.board.Chip;
import com.zinno.nim.game.game.Game;
import com.zinno.nim.game.world.Generator;
import com.zinno.nim.util.Config;

import net.md_5.bungee.api.ChatColor;

public class User extends MiniGamePlayer {

	private Player player;
	private Chip[] highlightedBlocks = new Chip[3];
	private ItemStack[] playerInv;
	private Location playerLoc;
	private Collection<PotionEffect> playerStats;
	private int xLoc;
	private boolean isCancelled;
	private double health;
	private float saturation;
	private int fireTicks;

	public User(Player player, Board board, Game game, int xLoc) {
		super(game, board);
		this.player = player;
		this.xLoc = xLoc;

		savePlayerInfo();
		highlightBlocks();
	}

	private void savePlayerInfo() {
		playerLoc = player.getLocation();
		playerInv = player.getInventory().getStorageContents();
		playerStats = player.getActivePotionEffects();
		health = player.getHealth();
		saturation = player.getSaturation();
		fireTicks = player.getFireTicks();
	}

	public void returnPlayerInfo() {
		player.setInvulnerable(false);
		player.teleport(playerLoc);
		player.getInventory().setStorageContents(playerInv);
		player.setHealth(health);
		player.setSaturation(saturation);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.addPotionEffects(playerStats);
		player.setFireTicks(fireTicks);
		isCancelled = true;
	}

	private void highlightBlocks() {
		new BukkitRunnable() {

			@SuppressWarnings("deprecation")
			public void run() {
				Block currentBlock = player.getTargetBlock( null, 30);
				if (highlightedBlocks[2] != null && highlightedBlocks[2] != highlightedBlocks[1]
						&& highlightedBlocks[2].CanHighlight()) {
					highlightedBlocks[2].removeColor();
				}
				highlightedBlocks[2] = highlightedBlocks[1];
				highlightedBlocks[1] = highlightedBlocks[0];
				if (!board.isChip(currentBlock.getLocation())) {
					highlightedBlocks[0] = null;
					return;
				}
				highlightedBlocks[0] = board.getChip(currentBlock.getLocation());
				if (highlightedBlocks[0].CanHighlight()) {
					highlightedBlocks[0].highlightChip();
				}
				if(isCancelled) {
					this.cancel();
				}
			}

		}.runTaskTimer(Config.getPlugin(), 0, 10);
	}

	public void alert(String title, String subTitle) {
		player.sendTitle(title, subTitle, 5, 20, 10);
	}

	public Player getPlayer() {
		return player;
	}

	public void clearPlayerInfo(int playerNumber) {
		
		World nimWorld = Bukkit.createWorld(new WorldCreator("NIM").generator(new Generator()));
		int x = xLoc - 4 - (playerNumber * 4);
		int y = -1;
		if(board.getSize() == 3) {
			y = 156;
		}else if(board.getSize() == 4) {
			y = 162;
		}
		int z = -10;
		Location loc = new Location(nimWorld, x, y, z);
		player.setInvulnerable(true);
		alert(ChatColor.GOLD + "Welcome, " + player.getName() + " to NIM!",
				ChatColor.GRAY + "Developed By: Zinno");
		new BukkitRunnable() {
			@Override
			public void run() {
				player.teleport(loc);
			}
		}.runTaskLater(Config.getPlugin(), 5);
		player.setHealth(20);
		player.setSaturation(20);
		
		ItemStack confirmMove = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta moveMeta = confirmMove.getItemMeta();
		moveMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Confirm Move!");
		moveMeta.addEnchant(Enchantment.LUCK, 1, false);
		confirmMove.setItemMeta(moveMeta);
		
		player.getInventory().clear();
		player.getInventory().setItem(4, confirmMove);
		player.getInventory().setItem(0, Tutorial.createTutBook());
		
		ItemStack endGame = new ItemStack(Material.ENDER_PEARL);
		ItemMeta endMeta = endGame.getItemMeta();
		endMeta.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Leave Game");
		endMeta.addEnchant(Enchantment.BINDING_CURSE, 1, false);
		endGame.setItemMeta(endMeta);
		player.getInventory().setItem(8, endGame);
		
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}
	
	public void inputMove(List<Chip> selectedChips) {
		for(Chip chip : selectedChips) {
			chip.erase();
		}
		board.update();
		board.resetSelectedRow();
		game.endTurn(this);
	}

}
