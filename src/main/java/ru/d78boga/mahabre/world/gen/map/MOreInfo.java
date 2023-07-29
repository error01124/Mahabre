package ru.d78boga.mahabre.world.gen.map;

import net.minecraft.world.gen.feature.WorldGenerator;

public class MOreInfo {
	public WorldGenerator generator;
	public int chunkCount;
	public int minHeight;
	public int maxHeight;
	
	public MOreInfo(WorldGenerator generator, int chunkCount, int minHeight, int maxHeight) {
		this.generator = generator;
		this.chunkCount = chunkCount;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}

}
