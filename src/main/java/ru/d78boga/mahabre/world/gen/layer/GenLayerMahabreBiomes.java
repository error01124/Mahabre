package ru.d78boga.mahabre.world.gen.layer;

import java.util.List;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.IntCache;
import ru.d78boga.mahabre.inits.MBiomes;
import ru.d78boga.mahabre.world.biome.MBiomeInfo;
import ru.d78boga.mahabre.world.biome.MahabreBiomesInfo;

public class GenLayerMahabreBiomes extends GenLayerMahabre {
	private List<MBiomeInfo> biomesInfo;

	public GenLayerMahabreBiomes(long p_i45560_1_) {
		super(p_i45560_1_);
		biomesInfo = new MahabreBiomesInfo().list;
	}

	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
		int[] aint = IntCache.getIntCache(areaWidth * areaHeight);

		for (int i = 0; i < areaHeight; ++i) {
			for (int j = 0; j < areaWidth; ++j) {
				initChunkSeed((long) (areaX + j), (long) (areaY + i));
				int b = getRandomBiome();
				aint[j + i * areaWidth] = b;
			}
		}

		return aint;
	}

	private int getRandomBiome() {
		int start = 0;
		int end = 0;
		int section = nextInt(100);
		Biome biome;

		for (MBiomeInfo info : biomesInfo) {
			biome = info.biome;
			end += info.spawnChance;

			if (start <= section && section < end)
				return Biome.getIdForBiome(biome);

			start = end;
		}

		return Biome.getIdForBiome(MBiomes.DEFAULT);
	}

}
