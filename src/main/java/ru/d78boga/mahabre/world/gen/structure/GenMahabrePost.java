package ru.d78boga.mahabre.world.gen.structure;

import net.minecraft.world.biome.Biome;
import ru.d78boga.mahabre.entities.EntityBatu;

public class GenMahabrePost extends MGenComplexStructure.Block {
	public GenMahabrePost(GenMahabreCity city) {
		city.super(new MStructureProperties(), "mahabre_post", "Mahabre Post", city.provider);
		spawnList.add(new Biome.SpawnListEntry(EntityBatu.class, 10, 1, 20));
	}
}
