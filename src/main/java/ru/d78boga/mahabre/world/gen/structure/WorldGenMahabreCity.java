package ru.d78boga.mahabre.world.gen.structure;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import ru.d78boga.mahabre.entities.EntityBatu;
import ru.d78boga.mahabre.inits.mBiomes;

public class WorldGenMahabreCity extends mWorldGenStructure {
	public WorldGenMahabreCity(World world) {
		super("mahabre_city", "Mahabre City", world);
		spawnList.add(new Biome.SpawnListEntry(EntityBatu.class, 10, 1, 20));
		allowedBiomes.add(mBiomes.MAHABRE_FLAT_DESERT);
	}
}
