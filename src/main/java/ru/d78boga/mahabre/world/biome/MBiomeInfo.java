package ru.d78boga.mahabre.world.biome;

import net.minecraft.world.biome.Biome;

public class MBiomeInfo {
	public Biome biome;
	public int spawnChance;
	
	public MBiomeInfo(Biome biome, int spawnChance) {
		this.biome = biome;
		this.spawnChance = spawnChance;
	}
}
