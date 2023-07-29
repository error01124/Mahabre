package ru.d78boga.mahabre.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerMahabre extends GenLayer {
	public GenLayerMahabre(long p_i2125_1_) {
		super(p_i2125_1_);
	}

	public static GenLayer[] initializeAllBiomeGenerators(long seed) {
        GenLayer genLayer = new GenLayerMahabreBiomes(1L); 
        genLayer = new GenLayerFuzzyZoom(2000L, genLayer);
        genLayer = GenLayerZoom.magnify(1000L, genLayer, 5); 
        genLayer = new GenLayerSmooth(1000L, genLayer);
        genLayer = new GenLayerVoronoiZoom(10L, genLayer);
        genLayer.initWorldGenSeed(seed);
        return new GenLayer[] {genLayer, genLayer};
    }
}
