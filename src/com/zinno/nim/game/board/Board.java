package com.zinno.nim.game.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.zinno.nim.game.world.Generator;

public class Board {

	private HashMap<List<Integer>, Chip> chips = new HashMap<List<Integer>, Chip>();
	private HashMap<Byte, List<Chip>> rowChips = new HashMap<Byte, List<Chip>>();
	private int selectedChipsRow = -1;
	private int xLoc;
	private byte[] activeChips;
	private byte gameType;

	public Board(int xLoc, int gameType) {
		this.xLoc = xLoc;
		this.gameType = (byte) gameType;
		if(gameType == 3) {
			activeChips = new byte[] {3, 5, 7};
		} else if(gameType == 4) {
			activeChips = new byte[] {1, 3, 5, 7};
		}
		generateChips();
		generateTitle();
	}

	@SuppressWarnings("deprecation")
	private void generateTitle() {
		Location loc = getZeroZero();
		loc.setX(loc.getBlockX() + 2);
		if(gameType == 3) {
			loc.setY(loc.getY() + 13);
		}else if(gameType == 4) {
			loc.setY(loc.getY() + 18);
		}
		int[][] blockType = new int[][] {
			{0,1,1,2,0,0,0,0,0,1,2,0,0,0,0,0,1,2,0,0,1,1,0,0,1,0,0,2,1},
			{2,0,0,0,2,0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,2,0,0,1,0,0,1,0}, 
			{1,0,0,0,1,2,0,0,0,1,2,1,0,0,0,0,0,1,0,0,0,0,1,0,0,2,0,0,2}, 
			{0,0,0,0,0,2,0,0,0,2,0,1,0,0,0,0,0,0,2,0,1,0,1,2,0,1,1,0,2},
			{0,0,0,0,0,1,1,0,0,2,0,2,0,0,0,0,0,1,2,0,0,2,1,0,2,1,0,2,1}, 
			{0,0,0,0,0,0,1,0,0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
			{0,0,0,0,0,0,2,1,0,1,0,0,2,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0}, 
			{0,0,0,0,0,0,0,1,0,1,0,0,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0}, 
			{0,0,0,0,0,0,0,2,1,2,0,0,0,1,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0}, 
			{0,0,0,0,0,0,0,0,1,2,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0}
			};
			
		for(int[] rowBlockType : blockType) {
			for (int length : rowBlockType) {
				switch(length) {
				case 0:
					break;
				case 1:
					loc.getBlock().setTypeIdAndData(251, (byte)14, false);
					break;
				case 2:
					loc.getBlock().setTypeIdAndData(35, (byte)14, false);
					break;
				}
				loc.setX(loc.getBlockX() - 1);
			}
			loc.setY(loc.getBlockY() + 1);
			loc.setX(loc.getBlockX() + 29);
			
		}
	}
	
	public List<Chip> getSelectedChips() {
		List<Chip> cList = new ArrayList<Chip>();
		for(Chip chip : chips.values()) {
			if(chip.isSelected()) {
				cList.add(chip);
			}
		}
		return cList;
	}

	private void generateChips() {
		final int stepY = 5;
		final int addSticks = 2;
		
		int totalSticks = -1;
		if(gameType == 3) {
			totalSticks = 3;
		}else if(gameType == 4) {
			totalSticks = 1;
		}
		int resetX = 0;
		int row = 0;

		Location loc = getZeroZero();
		World world = loc.getWorld();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();

		for (byte rowCounter = 0; rowCounter < gameType; rowCounter++) {
			List<Chip> chipRow = new ArrayList<Chip>();
			for (int stickCounter = 0; stickCounter < totalSticks; stickCounter += 1) {
				Chip chip = new Chip(new Location(world, x, y, z), row);
				chips.put(Arrays.asList(x, y, z), chip);
				chipRow.add(chip);
				x -= 4;
			}
			rowChips.put((byte) row, chipRow);
			if(gameType == 3) {
				if (rowCounter == 0) {
					resetX += 12;
				} else {
					resetX += 8;
				}
			}else if(gameType == 4) {
				if(rowCounter == 0) {
					resetX += 4;
				}else {
					resetX += 8;
				}
			}
			totalSticks += addSticks;
			x += resetX;
			y += stepY;
			row+=1;
		}
	}
	
	private Location getZeroZero() {
		World nimWorld = Bukkit.createWorld(new WorldCreator("NIM").generator(new Generator()));
		int x = xLoc;
		int y = 150;
		int z = 0;
		Location loc = new Location(nimWorld, x, y, z);
		return loc;
	}

	public Chip getChip(Location loc) {
		for (List<Integer> chipLoc : chips.keySet()) { // the problem
			int xChip = chipLoc.get(0);
			int yChip = chipLoc.get(1);
			int zChip = chipLoc.get(2);

			int xCheck = loc.getBlockX();
			int yCheck = loc.getBlockY();
			int zCheck = loc.getBlockZ();

			if (xChip - 2 < xCheck && xCheck < xChip + 2 && yChip - 2 < yCheck && yCheck < yChip + 2
					&& zCheck == zChip) {
				return chips.get(chipLoc);
			}
		}
		return null;
	}
	
	public boolean isChip(Location loc) {
		for (List<Integer> chipLoc : chips.keySet()) { // the problem
			int xChip = chipLoc.get(0);
			int yChip = chipLoc.get(1);
			int zChip = chipLoc.get(2);

			int xCheck = loc.getBlockX();
			int yCheck = loc.getBlockY();
			int zCheck = loc.getBlockZ();

			if (xChip - 2 < xCheck && xCheck < xChip + 2 && yChip - 2 < yCheck && yCheck < yChip + 2
					&& zCheck == zChip) {
				return true;
			}
		}
		return false;
	}

	public boolean isLegalSelection(Chip chip) {
		if(selectedChipsRow == -1) {
			selectedChipsRow = chip.getRow();
			return true;
		} else if(selectedChipsRow == chip.getRow()) {
			return true;
		}
		return false;
	}
	
	public boolean isLegalMove() {
		for(Chip chip : chips.values()) {
			if(chip.isSelected()) {
				return true;
			}
		}
		return false;
	}

	public void checkForReset() {
		boolean reset = true;
		for(Chip chip : chips.values()) {
			if(chip.isSelected()) {
				reset = false;
			}
		}
		if(reset) {
			selectedChipsRow = -1;
		}
	}

	public void eraseAll() {
		for(Chip chip : chips.values()) {
			chip.erase();
		}
		selectedChipsRow = -1;
		eraseLetters();
	}

	@SuppressWarnings("unused")
	private void eraseLetters() {
		Location loc = getZeroZero();
		loc.setX(loc.getBlockX() + 2);
		if(gameType == 3) {
			loc.setY(loc.getY() + 13);
		}else if(gameType == 4) {
			loc.setY(loc.getY() + 18);
		}
		int[][] blockType = new int[10][29];
		for(int[] rowBlockType : blockType) {
			for (int length : rowBlockType) {
				loc.getBlock().setType(Material.AIR);
				loc.setX(loc.getBlockX() - 1);
			}
			loc.setY(loc.getBlockY() + 1);
			loc.setX(loc.getBlockX() + 29);
		}
	}

	public void update() {
		for(Chip chip : chips.values()) {
			if(chip.isErased()) {
				activeChips[chip.getRow()] -=1;
			}
		}
		for(List<Chip> currentRowChips : rowChips.values()) {
			List<Chip> removeChips = new ArrayList<Chip>();
			for(Chip chip : currentRowChips) {
				if(chip.isErased()) {
					removeChips.add(chip);
				}
			}
			for(Chip chip : removeChips) {
				currentRowChips.remove(chip);
			}
		}
		for(Chip chip : chips.values()) {
			chip.removeErase();
		}
	}
	
	public List<Chip> getChips(byte row) {
		return rowChips.get(row);
	}

	public void resetSelectedRow() {
		selectedChipsRow = -1;
	}

	public List<Byte> getActiveChips() {
		List<Byte> cList = new ArrayList<Byte>();
		for(byte rowLength : activeChips) {
			cList.add(rowLength);
		}
		return cList;
	}

	public byte getSize() {
		return gameType;
	}


}
