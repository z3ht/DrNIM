package com.zinno.nim.game.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.zinno.nim.game.board.Board;
import com.zinno.nim.game.board.Chip;
import com.zinno.nim.game.game.Game;
import com.zinno.nim.game.users.util.Helper;
import com.zinno.nim.util.Config;

public class Computer extends MiniGamePlayer{
	
	byte difficulty;
	byte[][] nimNumbers;
	Byte[][] easy = new Byte[][] {
		{2, 2, 1, 1},
		{3, 3, 1, 1},
		{5, 5},
		{4, 4},
		{3, 3},
		{2, 2},
		{1}
	};
	Byte[][] medium = new Byte[][] {
		{6, 4, 3, 1},
		{5, 5, 1, 1},
		{4, 4, 1, 1},
		{3, 2, 1},
		{1, 1, 1},
		{5, 4, 1},
		{6, 4, 2}
	};
	Byte[][] hard = new Byte[][] {
		{7, 4, 2, 1},
		{6, 5, 2, 1},
		{6, 5, 3},
		{7, 4, 3},
		{7, 5, 2}
	};
	
	
	public Computer(Game game, Board board, byte difficulty) {
		super(game, board);
		this.difficulty = difficulty;
	}

	public void beginTurn() {
		List<Byte> locationAmount = findMove();
		if(locationAmount == null) {
			locationAmount = randMove();
		}
		locationAmount = checkLegality(locationAmount);
		List<Chip> chips = randomChipSelcetion(locationAmount);
		animateChipRemoval(chips);
		board.update();
		board.resetSelectedRow();
		game.endTurn();
	}
	
	private List<Byte> randMove() {
		byte maxRemove = 3;
		List<Byte> activeChips = board.getActiveChips();
		List<Byte> tempActiveChips = Helper.getActiveChips(activeChips);
		byte total = tempActiveChips.get((byte) (Math.random() * tempActiveChips.size()));
		byte location = Helper.getSlot(total, activeChips);
		if(total < maxRemove) {
			maxRemove = (byte) (total-1);
		}
		byte removalAmount = (byte) ((Math.random() * maxRemove)+1);
		return Arrays.asList(location, removalAmount);
	}

	private List<Byte> checkLegality(List<Byte> locationAmount) {
		if(locationAmount.get(locationAmount.size()-1) == 0) {
			List<Byte> chips = board.getActiveChips();
			byte location = Helper.getMaxSlot(chips);
			byte removalAmount = 1;
			locationAmount = Arrays.asList(location, removalAmount);
		}
		return locationAmount;
	}

	private List<Byte> findMove() {
		List<Byte> locationAmount = new ArrayList<Byte>();
		List<Byte> activeChips = board.getActiveChips();
		if(Helper.getSize(activeChips) == 1) {
			byte location = Helper.getMaxSlot(activeChips);
			byte amount = (byte) (Helper.getMaxNum(activeChips)-1);
			locationAmount = Arrays.asList(location, amount);
			return locationAmount;
		}
		if(difficulty >= 1) {
			for(Byte[] easyCheck : easy) {
				locationAmount = Helper.almostContains(activeChips, Arrays.asList(easyCheck));
				if(locationAmount != null) {
					return locationAmount;
				}
			}
		}
		if(difficulty >= 2) {
			for(Byte[] medCheck : medium) {
				locationAmount = Helper.almostContains(activeChips, Arrays.asList(medCheck));
				if(locationAmount != null) {
					return locationAmount;
				}
			}
		}
		if(difficulty >= 3) {
			for(Byte[] hardCheck : hard) {
				locationAmount = Helper.almostContains(activeChips, Arrays.asList(hardCheck));
				if(locationAmount != null) {
					return locationAmount;
				}
			}
		}
		return null;
	}

	private void animateChipRemoval(List<Chip> chips) {
		for(Chip chip : chips) {
			chip.highlightChip();
		}
		new BukkitRunnable() {
			byte counter = 0;
			public void run() {
				for(Chip chip : chips) {
					if(counter == 0) {
						chip.highlightChip();
					}else if(counter == 1) {
						chip.selectChip();
					}else if(counter == 2) {
						chip.erase();
					}
				}
				if(counter == 2) {
					board.resetSelectedRow();
					this.cancel();
				}
				counter+=1;
			}
		}.runTaskTimer(Config.getPlugin(), 0, 15);
	}

	private List<Chip> randomChipSelcetion(List<Byte> locationAmount) {
		List<Chip> possibleRemoveChips = board.getChips(locationAmount.get(0));
		Collections.shuffle(possibleRemoveChips);
		return possibleRemoveChips.subList(0, locationAmount.get(1));
	}
	
}
