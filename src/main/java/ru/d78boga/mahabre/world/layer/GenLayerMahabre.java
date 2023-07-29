package ru.d78boga.mahabre.world.layer;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import ru.d78boga.mahabre.inits.mBiomes;
import ru.d78boga.mahabre.world.biome.mBiomeInfo;

public abstract class GenLayerMahabre extends GenLayer {
	public GenLayerMahabre(long p_i2125_1_) {
		super(p_i2125_1_);
	}

	public static GenLayer[] initializeAllBiomeGenerators(long seed) {
		List<mBiomeInfo> biomesInfo = Lists.newArrayList(
				new mBiomeInfo(mBiomes.MAHABRE_FLAT_DESERT, 30),																																																																																																		
				new mBiomeInfo(mBiomes.MAHABRE_DESERT, 30),
				new mBiomeInfo(mBiomes.MAHABRE_DESERT_HILLS, 20),
				new mBiomeInfo(mBiomes.MAHABRE_DESERT_MOUNTAINS, 10),
				new mBiomeInfo(mBiomes.MAHABRE_ROCKY_DESERT, 10)
			);
        GenLayer genLayer = new GenLayerMahabreBiomes(1L).setBiomesSpawnInfo(biomesInfo); 
        genLayer = new GenLayerFuzzyZoom(2000L, genLayer);
        genLayer = GenLayerZoom.magnify(1000L, genLayer, 5); 
        genLayer = new GenLayerSmooth(1000L, genLayer);
        genLayer = new GenLayerVoronoiZoom(10L, genLayer);
        genLayer.initWorldGenSeed(seed);
        return new GenLayer[] {genLayer, genLayer};
    }
}
