package ru.d78boga.mahabre.world.biome;

import net.minecraft.world.biome.Biome;

public class MBiome extends Biome {
	public MBiome(BiomeProperties properties) {
		super(properties);
	}
	
	public boolean equals(Biome biome) {
		return this.getBiomeName() == biome.getBiomeName();
	}
}
