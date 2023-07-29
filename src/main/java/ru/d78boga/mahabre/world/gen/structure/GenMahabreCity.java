package ru.d78boga.mahabre.world.gen.structure;

import net.minecraft.world.biome.Biome;
import ru.d78boga.mahabre.entities.EntityBatu;
import ru.d78boga.mahabre.inits.MBiomes;

public class GenMahabreCity extends MGenStructure {
	public GenMahabreCity(MStructuresProvider provider) {
		super("mahabre_city", "Mahabre City", provider);
		spawnList.add(new Biome.SpawnListEntry(EntityBatu.class, 10, 1, 20));
		allowedBiomes.add(MBiomes.MAHABRE_FLAT_DESERT);
	}
}
