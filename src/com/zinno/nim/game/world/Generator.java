package com.zinno.nim.game.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class Generator extends ChunkGenerator {
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return new ArrayList<BlockPopulator>(); // Empty list
	}

	@Override
	public boolean canSpawn(World world, int x, int z) {
		return false;
	}

	@Override
	public byte[][] generateBlockSections(World world, Random random, int chunkx, int chunkz,
			ChunkGenerator.BiomeGrid biomes) {
		world.setDifficulty(Difficulty.PEACEFUL);
		world.setAnimalSpawnLimit(0);
		world.setAmbientSpawnLimit(0);
		world.setKeepSpawnInMemory(false);
		world.setMonsterSpawnLimit(0);
		world.setStorm(false);
		world.setTime(0);
		world.setWaterAnimalSpawnLimit(0);
		return new byte[world.getMaxHeight() / 16][];
	}
}
