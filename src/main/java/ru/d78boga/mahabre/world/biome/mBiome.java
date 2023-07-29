package ru.d78boga.mahabre.world.biome;

import net.minecraft.world.biome.Biome;

public class mBiome extends Biome {
	public mBiome(BiomeProperties properties) {
		super(properties);
	}
	
	public boolean equals(Biome biome) {
		return this.getBiomeName() == biome.getBiomeName();
	}
}
