package ru.d78boga.mahabre.world.layer;

import java.util.List;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.IntCache;
import ru.d78boga.mahabre.inits.mBiomes;
import ru.d78boga.mahabre.world.biome.mBiomeInfo;

public class GenLayerMahabreBiomes extends GenLayerMahabre {
	private List<mBiomeInfo> biomesSpawnInfo;
	
    public GenLayerMahabreBiomes(long p_i45560_1_) {
        super(p_i45560_1_);
    }
    
    public GenLayerMahabreBiomes setBiomesSpawnInfo(List<mBiomeInfo> biomesSpawnInfo) {
    	this.biomesSpawnInfo = biomesSpawnInfo;
    	validateBiomesSpawnChances();
    	return this;
    }
    
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] aint = IntCache.getIntCache(areaWidth * areaHeight);

        for (int i = 0; i < areaHeight; ++i) {
            for (int j = 0; j < areaWidth; ++j) {
                initChunkSeed((long)(areaX + j), (long)(areaY + i));
                int b = getNextBiome();
                aint[j + i * areaWidth] = b;
            }
        }

        return aint;
    }
	
    private void validateBiomesSpawnChances() {
    	int result = 0;
    	
    	for (mBiomeInfo info : biomesSpawnInfo) {
    		result += info.spawnChance;
    	}
    	
    	if (result > 100) throw new IndexOutOfBoundsException();
    }
    
    private int getNextBiome() {
    	int j = 0;
    	int j1 = 0;
    	int k = nextInt(100);
    	Biome biome;
    	
    	for (mBiomeInfo info : biomesSpawnInfo) {
    		biome = info.biome;
    		j1 += info.spawnChance;
    		
    		if (j <= k && k < j1) return Biome.getIdForBiome(biome);
    		
    		j = j1;
    	}
    	
    	return Biome.getIdForBiome(mBiomes.MAHABRE_DESERT);
    }
    
}
