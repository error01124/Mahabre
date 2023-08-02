package ru.d78boga.mahabre.world.gen.structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MStructuresProvider {
	public World world;
	private MStructureCache cache;
	
	public MStructuresProvider(World world) {
		this.world = world;
		cache = new MStructureCache();
	}
	
	public void setStructure(String name, BlockPos pos, BlockPos size) {
		cache.setStructure(name, pos, size);
	}
	
	public String getStructureName(BlockPos pos) {
		return cache.getStructureName(pos);
	}
}