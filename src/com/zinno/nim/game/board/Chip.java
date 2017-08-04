package com.zinno.nim.game.board;

import org.bukkit.Location;


public class Chip {
	
	Location location;
	private boolean selectedChip;
	private int row;
	private boolean canHighlight;
	private boolean isErased;

	public Chip(Location location, int row) {
		this.location = location;
		this.row = row;
		
		createDefaultChip();
	}

	@SuppressWarnings("deprecation")
	private void createDefaultChip() {
		location.getBlock().setTypeIdAndData(252, (byte) 8, false);
		location.setX(location.getX() + 1);
		location.getBlock().setTypeIdAndData(35, (byte) 0, false);
		location.setX(location.getX() - 2);
		location.getBlock().setTypeIdAndData(35, (byte) 0, false);
		location.setX(location.getX() + 1);
		location.setY(location.getY() + 1);
		location.getBlock().setTypeIdAndData(35, (byte) 0, false);
		location.setY(location.getY() - 2);
		location.getBlock().setTypeIdAndData(35, (byte) 0, false);
		location.setY(location.getY() + 1);
		canHighlight = true;
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void highlightChip() {
		location.getBlock().setTypeIdAndData(252, (byte) 3, false);
		location.setX(location.getX() + 1);
		location.getBlock().setTypeIdAndData(35, (byte) 4, false);
		location.setX(location.getX() - 2);
		location.getBlock().setTypeIdAndData(35, (byte) 4, false);
		location.setX(location.getX() + 1);
		location.setY(location.getY() + 1);
		location.getBlock().setTypeIdAndData(35, (byte) 4, false);
		location.setY(location.getY() - 2);
		location.getBlock().setTypeIdAndData(35, (byte) 4, false);
		location.setY(location.getY() + 1);
		canHighlight = true;
	}

	@SuppressWarnings("deprecation")
	public void selectChip() {
		location.getBlock().setTypeIdAndData(252, (byte) 15, false);
		location.setX(location.getX() + 1);
		location.getBlock().setTypeIdAndData(35, (byte) 14, false);
		location.setX(location.getX() - 2);
		location.getBlock().setTypeIdAndData(35, (byte) 14, false);
		location.setX(location.getX() + 1);
		location.setY(location.getY() + 1);
		location.getBlock().setTypeIdAndData(35, (byte) 14, false);
		location.setY(location.getY() - 2);
		location.getBlock().setTypeIdAndData(35, (byte) 14, false);
		location.setY(location.getY() + 1);
		selectedChip = true;
		canHighlight = false;
	}

	public boolean isSelected() {
		return selectedChip;
	}
	
	public int getRow() {
		return row;
	}

	@SuppressWarnings("deprecation")
	public void removeColor() {
		location.getBlock().setTypeIdAndData(252, (byte) 8, false);
		location.setX(location.getX() + 1);
		location.getBlock().setTypeIdAndData(35, (byte) 0, false);
		location.setX(location.getX() - 2);
		location.getBlock().setTypeIdAndData(35, (byte) 0, false);
		location.setX(location.getX() + 1);
		location.setY(location.getY() + 1);
		location.getBlock().setTypeIdAndData(35, (byte) 0, false);
		location.setY(location.getY() - 2);
		location.getBlock().setTypeIdAndData(35, (byte) 0, false);
		location.setY(location.getY() + 1);
		selectedChip = false;
		canHighlight = true;
	}
	
	@SuppressWarnings("deprecation")
	public void incorrectSelection() {
		location.getBlock().setTypeId(250);
		location.setX(location.getX() + 1);
		location.getBlock().setTypeId(152);
		location.setX(location.getX() - 2);
		location.getBlock().setTypeId(152);
		location.setX(location.getX() + 1);
		location.setY(location.getY() + 1);
		location.getBlock().setTypeId(152);
		location.setY(location.getY() - 2);
		location.getBlock().setTypeId(152);
		location.setY(location.getY() + 1);
		canHighlight = false;
	}

	@SuppressWarnings("deprecation")
	public void erase() {
		location.getBlock().setTypeId(0);
		location.setX(location.getX() + 1);
		location.getBlock().setTypeId(0);
		location.setX(location.getX() - 2);
		location.getBlock().setTypeId(0);
		location.setX(location.getX() + 1);
		location.setY(location.getY() + 1);
		location.getBlock().setTypeId(0);
		location.setY(location.getY() - 2);
		location.getBlock().setTypeId(0);
		location.setY(location.getY() + 1);
		canHighlight = false;
		selectedChip = false;
		isErased = true;
	}

	public boolean CanHighlight() {
		return canHighlight;
	}
	
	public void removeErase() {
		isErased = false;
	}

	public boolean isErased() {
		return isErased;
	}
}
