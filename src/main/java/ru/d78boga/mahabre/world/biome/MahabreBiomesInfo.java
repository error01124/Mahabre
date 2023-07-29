package ru.d78boga.mahabre.world.biome;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.world.biome.Biome;
import ru.d78boga.mahabre.inits.MBiomes;

public class MahabreBiomesInfo {
	public List<MBiomeInfo> list = Lists.newArrayList();
	
	public MahabreBiomesInfo() {
		create(MBiomes.MAHABRE_DESERT, 30); 
		create(MBiomes.MAHABRE_FLAT_DESERT, 30);
		create(MBiomes.MAHABRE_DESERT_HILLS, 20); 
		create(MBiomes.MAHABRE_DESERT_MOUNTAINS, 10); 
		create(MBiomes.MAHABRE_ROCKY_DESERT, 10);
		validateSpawnChances();
	}
	
	private void create(Biome biome, int chance) {
		MBiomeInfo result = new MBiomeInfo(biome, chance);
		list.add(result);
	}
	
	private void validateSpawnChances() {
		int total = 0;

		for (MBiomeInfo info : list) {
			total += info.spawnChance;
		}

		if (total > 100) {		
			throw new IndexOutOfBoundsException();
		}
	}
}
