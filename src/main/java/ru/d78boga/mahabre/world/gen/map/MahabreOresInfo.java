package ru.d78boga.mahabre.world.gen.map;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenMinable;
import ru.d78boga.mahabre.inits.MBlocks;

public class MahabreOresInfo {
	public List<MOreInfo> list = Lists.newArrayList();
	
	public MahabreOresInfo() {
		create(MBlocks.GARBAGE_LAYING, 14, 8, 30, 60);
		create(MBlocks.CONSTRUCTION_DEBRIS, 10, 7, 20, 55);
		create(MBlocks.RESOURCE_LAYING, 7, 4, 7, 25);
	}
	
	private void create(Block block, int layingCount, int chunckCount, int minHeight, int maxHeight) {
		WorldGenMinable worldGen = new WorldGenMinable(block.getDefaultState(), layingCount);
		MOreInfo result = new MOreInfo(worldGen, chunckCount, minHeight, maxHeight);
		list.add(result);
	}
}
