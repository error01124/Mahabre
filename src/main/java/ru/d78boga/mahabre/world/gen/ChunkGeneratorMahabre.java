package ru.d78boga.mahabre.world.gen;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;
import ru.d78boga.mahabre.world.gen.map.MapGenMahabreCaves;
import ru.d78boga.mahabre.world.gen.structure.GenMahabreCity;
import ru.d78boga.mahabre.world.gen.structure.GenMahabrePost;
import ru.d78boga.mahabre.world.gen.structure.MStructureProperties;
import ru.d78boga.mahabre.world.gen.structure.MStructuresProvider;

public class ChunkGeneratorMahabre implements IChunkGenerator {
	private World world;
	private Random random;
	private Biome[] biomesForGeneration;
	private MapGenBase caveGenerator = new MapGenMahabreCaves();
	private TerrainGenMahabre terraingen = new TerrainGenMahabre();
	private boolean generateStructures = true;
	private MStructuresProvider structuresProvider;
	private GenMahabreCity genCity;

	public ChunkGeneratorMahabre(World world) {
		this.world = world;
		long seed = world.getSeed();
		random = new Random((seed + 516) * 314);
		terraingen.setup(world, random);
		caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, EventType.CAVE);
		structuresProvider = new MStructuresProvider(world);
		genCity = new GenMahabreCity(new MStructureProperties().setComplex(10), structuresProvider);
		GenMahabrePost genPost = new GenMahabrePost(new MStructureProperties(), genCity);
	}

	public Chunk generateChunk(int x, int z) {
		ChunkPrimer chunkprimer = new ChunkPrimer();
		biomesForGeneration = world.getBiomeProvider().getBiomesForGeneration(biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
		terraingen.setBiomesForGeneration(biomesForGeneration);
		terraingen.generate(x, z, chunkprimer);
		biomesForGeneration = world.getBiomeProvider().getBiomes(biomesForGeneration, x * 16, z * 16, 16, 16);
		terraingen.replaceBiomeBlocks(x, z, chunkprimer, this, biomesForGeneration);
		caveGenerator.generate(world, x, z, chunkprimer);
		Chunk chunk = new Chunk(world, chunkprimer, x, z);
		byte[] biomeArray = chunk.getBiomeArray();

		for (int i = 0; i < biomeArray.length; ++i) {
			biomeArray[i] = (byte) Biome.getIdForBiome(biomesForGeneration[i]);
		}

		chunk.generateSkylightMap();

		if (generateStructures) {
			genCity.generate(world, random, x, z);
		}

		return chunk;
	}

	public void populate(int x, int z) {
		int i = x * 16;
		int j = z * 16;
		BlockPos blockpos = new BlockPos(i, 0, j);
		Biome biome = world.getBiome(blockpos.add(16, 0, 16));
		biome.decorate(world, random, blockpos);
		WorldEntitySpawner.performWorldGenSpawning(world, biome, i + 8, j + 8, 16, 16, random);

		if (generateStructures) {
			genCity.generate(world, random, x, z);
		}
	}

	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}

	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		if (creatureType == EnumCreatureType.MONSTER) {
			if (genCity.isInsideStructure(pos)) {
				return genCity.getSpawnList();
			}
		}

		return ImmutableList.of();
	}

	public void recreateStructures(Chunk chunkIn, int x, int z) {
		if (generateStructures) {
			genCity.generate(world, random, x, z);
		}
	}

	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
		return null;
	}

	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}
}