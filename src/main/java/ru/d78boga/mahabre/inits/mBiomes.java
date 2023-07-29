package ru.d78boga.mahabre.inits;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import ru.d78boga.mahabre.world.biome.BiomeMahabreDesert;
import ru.d78boga.mahabre.world.biome.mBiome;

public class mBiomes {
	public static mBiome MAHABRE_FLAT_DESERT;
	public static mBiome MAHABRE_DESERT;
	public static mBiome MAHABRE_ROCKY_DESERT;
	public static mBiome MAHABRE_DESERT_HILLS;
	public static mBiome MAHABRE_DESERT_MOUNTAINS;
	
	public static mBiomes.Registry REGISTRY = new mBiomes.Registry();;

	public static class Registry implements IRegistry {
		private IForgeRegistry<Biome> registry;

		public void registerAll() {
			registry = ForgeRegistries.BIOMES;
			MAHABRE_FLAT_DESERT = register("mahabre_flat_desert", new BiomeMahabreDesert(new BiomeProperties("Mahabre Flat Desert").setBaseHeight(0.125F).setHeightVariation(0.0F)));
			MAHABRE_DESERT = register("mahabre_desert", new BiomeMahabreDesert(new BiomeProperties("Mahabre Desert").setBaseHeight(0.125F).setHeightVariation(0.05F)));
			MAHABRE_ROCKY_DESERT = register("mahabre_rocky_desert", new BiomeMahabreDesert(new BiomeProperties("Mahabre Rocky Desert").setBaseHeight(0.125F).setHeightVariation(0.05F), BiomeMahabreDesert.Type.ROCKY));
			MAHABRE_DESERT_HILLS = register("mahabre_desert_hills", new BiomeMahabreDesert(new BiomeProperties("Mahabre Desert Hills").setBaseHeight(0.45F).setHeightVariation(0.3F)));
			MAHABRE_DESERT_MOUNTAINS = register("mahabre_desert_mountains", new BiomeMahabreDesert(new BiomeProperties("Mahabre Desert Mountains").setBaseHeight(1F).setHeightVariation(0.5F)));
		}

		private <T extends mBiome> T register(String name, T biome) {
			biome.setRegistryName(name);
			registry.register(biome);
			BiomeDictionary.makeBestGuess(biome);
			return biome;
		}
	}
}
