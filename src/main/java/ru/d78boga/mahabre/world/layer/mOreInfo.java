package ru.d78boga.mahabre.world.layer;

import net.minecraft.world.gen.feature.WorldGenerator;

public class mOreInfo {
	public WorldGenerator generator;
	public int chunkCount;
	public int minHeight;
	public int maxHeight;
	
	public mOreInfo(WorldGenerator generator, int chunkCount, int minHeight, int maxHeight) {
		this.generator = generator;
		this.chunkCount = chunkCount;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}

}
