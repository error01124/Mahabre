package ru.d78boga.mahabre.world;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenMinable;
import ru.d78boga.mahabre.inits.mBlocks;
import ru.d78boga.mahabre.world.layer.mOreInfo;

public class OresInfoMahabre {
	public List<mOreInfo> list;
	public mOreInfo garbageLaying;
	public mOreInfo constructionDebris;
	public mOreInfo resourceLaying;
	
	public OresInfoMahabre() {
		list = Lists.newArrayList();
		garbageLaying = create(mBlocks.GARBAGE_LAYING, 14, 8, 30, 60);
		constructionDebris = create(mBlocks.CONSTRUCTION_DEBRIS, 10, 7, 20, 55);
		resourceLaying = create(mBlocks.RESOURCE_LAYING, 7, 4, 7, 25);
	}
	
	private mOreInfo create(Block block, int layingCount, int chunckCount, int minHeight, int maxHeight) {
		WorldGenMinable worldGen = new WorldGenMinable(block.getDefaultState(), layingCount);
		mOreInfo result = new mOreInfo(worldGen, chunckCount, minHeight, maxHeight);
		list.add(result);
		return result;
	}
}
