package ru.d78boga.mahabre.world.gen.structure;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.d78boga.mahabre.util.MMath;
import ru.d78boga.mahabre.util.Util;

public class MStructureProperties {
	public boolean underground = false;
	public int minHeight;
	public int maxHeight;
	public boolean complex = false; 
	public int blocksCount = 1;
	public boolean hasLoot = false;
	private ResourceLocation lootLocation = null;

	public MStructureProperties setHeight(int min, int max) {
		underground = true;
		minHeight = min;
		maxHeight = max;
		return this;
	}

	public BlockPos applyHeight(int x, int z, Random rand, World world) {
		int y = world.getHeight();
		
		if (underground) {
			int delta = maxHeight - minHeight;
			y = minHeight + rand.nextInt(delta + 1);
		} else {		
			boolean foundGround = false;
			
			while (!foundGround && y-- >= 0) {
				Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
				foundGround = block != Blocks.AIR;
			}
		}
		
		y = MMath.clamp(y + 1, 0, world.getHeight());
		return new BlockPos(x, y, z);
	}
	
	public MStructureProperties setComplex(int blocksCount) {
		complex = true;
		
		if (blocksCount < 1) {
			blocksCount = 1;
		}
		
		this.blocksCount = blocksCount;
		return this;
	}
	
	public MStructureProperties setLoot() {
		hasLoot = true;
		return this;
	}
	
	public ResourceLocation getLoot(String registryName) {
		if (hasLoot) {			
			if (lootLocation == null) {
				lootLocation = Util.locate("chests/" + registryName);
			}
			
			return lootLocation;
		}
		
		return null;
	}
}
