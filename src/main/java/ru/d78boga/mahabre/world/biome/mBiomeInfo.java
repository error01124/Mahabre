package ru.d78boga.mahabre.world.biome;

import net.minecraft.world.biome.Biome;

public class mBiomeInfo {
	public Biome biome;
	public int spawnChance;
	
	public mBiomeInfo(Biome biome, int spawnChance) {
		this.biome = biome;
		this.spawnChance = spawnChance;
	}
}
