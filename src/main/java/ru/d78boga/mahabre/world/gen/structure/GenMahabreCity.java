package ru.d78boga.mahabre.world.gen.structure;

import ru.d78boga.mahabre.inits.MBiomes;

public class GenMahabreCity extends MGenComplexStructure {
	public GenMahabreCity(MStructuresProvider provider) {
		super(new MStructureProperties().setComplex(10).setLoot(), "mahabre_city", "Mahabre City", provider);
		allowedBiomes.add(MBiomes.MAHABRE_FLAT_DESERT);
		addBlock(new GenMahabrePost(this));
	}
}
