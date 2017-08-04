package com.zinno.nim.game.users.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Helper {
	private Helper() {
	}

	public static byte getMinNum(List<Byte> bList) {
		byte minNum = Byte.MAX_VALUE;
		for (byte row : bList) {
			if (row < minNum && row != 0) {
				minNum = row;
			}
		}
		return minNum;
	}

	public static byte getMinSlot(List<Byte> bList) {
		byte minNum = Byte.MAX_VALUE;
		byte minSlot = 0;
		byte counter = 0;
		for (byte row : bList) {
			if (row < minNum && row != 0) {
				minNum = row;
				minSlot = counter;
			}
			counter += 1;
		}
		return minSlot;
	}

	public static byte getMaxNum(List<Byte> bList) {
		byte largest = 0;
		for (byte row : bList) {
			if (row > largest) {
				largest = row;
			}
		}
		return largest;
	}

	public static byte getMaxSlot(List<Byte> bList) {
		byte largestNum = 0;
		byte largestSlot = 0;
		byte counter = 0;
		for (byte row : bList) {
			if (row > largestNum) {
				largestNum = row;
				largestSlot = counter;
			}
			counter += 1;
		}
		return largestSlot;
	}

	public static byte getSize(List<Byte> bList) {
		byte counter = 0;
		for (byte row : bList) {
			if (row != 0) {
				counter += 1;
			}
		}
		return counter;
	}

	public static byte getSum(List<Byte> bList) {
		byte counter = 0;
		for (byte row : bList) {
			counter += row;
		}
		return counter;
	}

	public static byte getSlot(byte activeChip, List<Byte> inactiveChips) {
		byte counter = 0;
		for (byte b : inactiveChips) {
			if (b == activeChip) {
				return counter;
			}
			counter += 1;
		}
		return -1;
	}

	public static List<Byte> getActiveChips(List<Byte> bList) {
		List<Byte> activeChips = new ArrayList<Byte>();
		for (byte row : bList) {
			if (row != 0) {
				activeChips.add(row);
			}
		}
		return activeChips;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean contains(List<Byte> activeChips, List<Byte> recMove) {
		return new HashSet(activeChips).equals(new HashSet(recMove));
	}

	public static List<Byte> almostContains(List<Byte> activeChips, List<Byte> recMove) {
		if (Helper.contains(recMove, activeChips)) {
			return null;
		}
		if(getSize(activeChips) == getSize(recMove)) {
			for(byte removeActiveChip : activeChips) {
				if(removeActiveChip == 0) {
					continue;
				}
				List<Byte> tempActiveChips = getActiveChips(activeChips);
				byte tempActiveChip = removeActiveChip;
				byte tempSlot = getSlot(removeActiveChip, tempActiveChips);
				byte activeSlot = getSlot(removeActiveChip, activeChips);
				while(tempActiveChip > 0) {
					tempActiveChips.set(tempSlot, (byte) (--tempActiveChip));
					List<Byte> tempRecMove = getActiveChips(recMove);
					if(contains(tempActiveChips, tempRecMove)) {
						List<Byte> locationAmount = new ArrayList<Byte>();
						locationAmount.add(activeSlot);
						locationAmount.add((byte) (removeActiveChip-tempActiveChip));
						return locationAmount;
					}
				}
			}
		} else if (getSize(activeChips) == recMove.size()+1) {
				byte counter = 0;
				List<Byte> tempActiveChips = new ArrayList<Byte>();
				for(byte removeActiveChip : activeChips) {
					if(removeActiveChip == 0) {
						counter+=1;
						continue;
					}
					tempActiveChips = getActiveChips(activeChips);
					tempActiveChips.remove(new Byte(removeActiveChip));
					List<Byte> tempRecMove = getActiveChips(recMove);
					if(contains(tempActiveChips, tempRecMove)) {
						List<Byte> locationAmount = new ArrayList<Byte>();
						locationAmount.add(counter);
						locationAmount.add((byte) (removeActiveChip));
						return locationAmount;
					}
					counter+=1;
				}
			}
		return null;
	}
}
