package ru.d78boga.mahabre.world.biome;

import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import ru.d78boga.mahabre.util.MathUtil;
import ru.d78boga.mahabre.world.gen.map.MOreInfo;
import ru.d78boga.mahabre.world.gen.map.MahabreOresInfo;

public class BiomeMahabreDesert extends MBiome {
	private Type type;
	private MBiomeDecorator decorator;
	private WorldGenBlockBlob rockGenerator;
	
	public BiomeMahabreDesert(BiomeProperties properties) {
		super(properties);
		type = Type.NORMAL;
		properties.setTemperature(2.0F).setRainfall(0.0F).setRainDisabled();
		spawnableCreatureList.clear();
		topBlock = Blocks.SAND.getDefaultState();
		fillerBlock = Blocks.SAND.getDefaultState();
		decorator = new MBiomeDecorator();
		decorator.treesPerChunk = -999;
		decorator.deadBushPerChunk = 0;
		decorator.reedsPerChunk = 0;
		decorator.cactiPerChunk = 0;
		decorator.mushroomsPerChunk = 0;
		List<MOreInfo> oresInfo = new MahabreOresInfo().list;
		decorator.oresInfo = oresInfo;
		rockGenerator = new WorldGenBlockBlob(Blocks.COBBLESTONE, 0);
	}

	public BiomeMahabreDesert(BiomeProperties properties, Type type) {
		this(properties);
		this.type = type;
	}

	public BiomeDecorator createBiomeDecorator() {
		return new MBiomeDecorator();
	}

	public void decorate(World worldIn, Random rand, BlockPos pos) {
		if ((type == Type.ROCKY) && TerrainGen.decorate(worldIn, rand, pos, DecorateBiomeEvent.Decorate.EventType.ROCK)) {
			int i = rand.nextInt(3);

			for (int j = 0; j < i; ++j) {
				int k = rand.nextInt(16) + 8;
				int l = rand.nextInt(16) + 8;
				BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
				rockGenerator.generate(worldIn, rand, blockpos);
			}
		}

		decorator.decorate(worldIn, rand, this, pos);
	}

	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		if (type == Type.ROCKY) {
			topBlock = Blocks.SAND.getDefaultState();
			fillerBlock = Blocks.SANDSTONE.getDefaultState();

			if (noiseVal > 1.75D) {
				if (MathUtil.roll(5))
					topBlock = Blocks.STONE.getDefaultState();
			}
		}

		generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
	}

	public enum Type {
		NORMAL,
		ROCKY;
	}
}
