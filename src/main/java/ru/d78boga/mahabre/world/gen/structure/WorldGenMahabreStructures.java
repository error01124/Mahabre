//package ru.d78boga.mahabre.world.gen.structure;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Random;
//
//import net.minecraft.block.Block;
//import net.minecraft.init.Blocks;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldType;
//import net.minecraft.world.chunk.IChunkProvider;
//import net.minecraft.world.gen.IChunkGenerator;
//import net.minecraft.world.gen.feature.WorldGenerator;
//import net.minecraftforge.fml.common.IWorldGenerator;
//import ru.d78boga.mahabre.world.biome.BiomeMahabreDesert;
//
//public class WorldGenMahabreStructures implements IWorldGenerator {
//	public mWorldGenStructure MAHABRE_CITY = new WorldGenMahabreCity();
//
//	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
//		switch(world.provider.getDimension()) {
//			case 4:
//				generateStructure(MAHABRE_CITY, world, random, chunkX, chunkZ, 10, Blocks.SAND, BiomeMahabreDesert.class); 
//				break;
//			default:
//				break;
//		}
//	}
//
//	private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes) {
//		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
//
//		int x = (chunkX * 16) + random.nextInt(15);
//		int z = (chunkZ * 16) + random.nextInt(15);
//		int y = calculateGenerationHeight(world, x, z, topBlock);
//		BlockPos pos = new BlockPos(x, y, z);
//
//		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
//
//		if (world.getWorldType() != WorldType.FLAT) {
//			if (classesList.contains(biome)) {
//				if (random.nextInt(chance) == 0) {
//					generator.generate(world, random, pos);
//				}
//			}
//		}
//	}
//
//
//}