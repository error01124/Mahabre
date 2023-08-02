package ru.d78boga.mahabre.world.gen.structure;

import java.util.List;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class MStructureCache {
	private Long2ObjectMap<MStructureCache.Block> cacheMap = new Long2ObjectOpenHashMap<MStructureCache.Block>(4096);
	private MStructureCache.Block lastBlock;
	
	public MStructureCache.Block getStructureCacheBlock(int chunkX, int chunkZ) {
		if (lastBlock != null && lastBlock.posEquals(chunkX, chunkZ)) {
			return lastBlock;
		}
		
		long i = ChunkPos.asLong(chunkX, chunkZ);
		MStructureCache.Block block = (MStructureCache.Block) cacheMap.get(i);

		if (block == null) {
			block = new MStructureCache.Block(chunkX, chunkZ);
			cacheMap.put(i, block);
		}

		return block;
	}

	public String getStructureName(BlockPos pos) {
		int x = pos.getX() >> 4;
		int z = pos.getZ() >> 4;
		return getStructureCacheBlock(x, z).getStructureName(pos);
	}

	public void setStructure(String name, BlockPos pos, BlockPos size) {
		int x = pos.getX() >> 4;
		int z = pos.getZ() >> 4;
		getStructureCacheBlock(x, z).addStructure(new StructureEntry(name, pos, size));
	}

	public class Block {
		private List<StructureEntry> structures = Lists.newArrayList();
		private int x;
		private int z;
		
		public Block(int x, int z) {
			this.x = x;
			this.z = z;
		}
		
		public void addStructure(StructureEntry structure) {
			structures.add(structure);
		}
		
		public String getStructureName(BlockPos pos) {
			for (StructureEntry currentStructure : structures) {
				if (currentStructure.isInside(pos)) {
					return currentStructure.name;
				}
			}
			
			return "";
		}
		
		public boolean posEquals(int x, int z) {
			return x == this.x && z == this.z;
		}
	}
	
	public class StructureEntry {
		public String name;
		public StructureBoundingBox box;
		
		public StructureEntry(String name, BlockPos pos, BlockPos size) {
			this.name = name;
			box = new StructureBoundingBox(pos, pos.add(size));
		}
		
		public boolean isInside(BlockPos pos) {
			return box.isVecInside(pos);
		}
	}
}