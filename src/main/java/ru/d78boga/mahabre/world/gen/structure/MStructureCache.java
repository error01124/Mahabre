package ru.d78boga.mahabre.world.gen.structure;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class MStructureCache {
	private Long2ObjectMap<MStructureCache.Block> cacheMap = new Long2ObjectOpenHashMap<MStructureCache.Block>(4096);
	
	public MStructureCache.Block getStructureCacheBlock(int x, int z) {
		long i = ChunkPos.asLong(x, z);
		MStructureCache.Block structurecache$block = (MStructureCache.Block) cacheMap.get(i);

		if (structurecache$block == null) {
			structurecache$block = new MStructureCache.Block(x, z);
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