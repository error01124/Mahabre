package ru.d78boga.mahabre.world.gen.structure;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class mStructureCache {
	private Long2ObjectMap<mStructureCache.Block> cacheMap = new Long2ObjectOpenHashMap<mStructureCache.Block>(4096);
	
	public mStructureCache.Block getStructureCacheBlock(int x, int z) {
		long i = ChunkPos.asLong(x, z);
		mStructureCache.Block structurecache$block = (mStructureCache.Block) cacheMap.get(i);

		if (structurecache$block == null) {
			structurecache$block = new mStructureCache.Block(x, z);
			cacheMap.put(i, structurecache$block);
		}

		return structurecache$block;
	}

	public String getStructure(BlockPos pos) {
		int x = pos.getX() >> 4;
		int z = pos.getZ() >> 4;
		return getStructureCacheBlock(x, z).structure;
	}

	public void setStructure(BlockPos pos, String structure) {
		int x = pos.getX() >> 4;
		int z = pos.getZ() >> 4;
		getStructureCacheBlock(x, z).structure = structure;
	}

	public class Block {
		public String structure;
		public int x;
		public int z;

		public Block(int x, int z) {
			this.x = x;
			this.z = z;
			structure = "";
		}
	}
}