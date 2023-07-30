package ru.d78boga.mahabre.world.gen.structure;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.TemplateManager;
import ru.d78boga.mahabre.util.MMath;
import ru.d78boga.mahabre.world.biome.MBiome;

public abstract class MGenStructure extends WorldGenerator implements IStructure {
	public String registryName;
	public String name;
	protected List<Biome.SpawnListEntry> spawnList = Lists.newArrayList();
	protected List<MBiome> allowedBiomes = Lists.newArrayList();
	protected Random rand = new Random();
	protected World world;
	protected WorldProvider worldProvider;
	protected MinecraftServer mcServer;
	protected TemplateManager manager;
	protected MStructuresProvider provider;
	protected boolean underground;
	protected int minHeight = 0;
	protected int maxHeight = 0;

	public MGenStructure(MStructureProperties properties, String registryName, String name, MStructuresProvider provider) {
		this.registryName = registryName;
		this.name = name;
		this.provider = provider;
		this.world = provider.world;
		worldProvider = world.provider;
		mcServer = world.getMinecraftServer();
		manager = worldServer.getStructureTemplateManager();
		underground = properties.underground;
		
		if (underground) {			
			minHeight = properties.minHeight;
			maxHeight = properties.maxHeight;
		}
	}

	public boolean generate(World worldIn, Random rand, int chunkX, int chunkZ) {
		BlockPos pos = calculateSpawnPosition(chunkX, chunkZ);
		return generate(worldIn, rand, pos);
	}

	public boolean generate(World worldIn, Random rand, BlockPos pos) {
		if (!canSpawnStructureAtCoords(pos)) return false;

		int x = pos.getX();
		int z = pos.getZ();
		int y = calculateGenerationHeight(x, z);
		pos = new BlockPos(x, y, z);
		generateStructure(worldIn, rand, pos);
		return true;
	}

	protected BlockPos calculateSpawnPosition(int chunkX, int chunkZ) {
		int x  = chunkX * 16;
		int z = chunkZ * 16;
		int y = 0; //calculateGenerationHeight(x, z);
		return new BlockPos(x, y, z);
	}
	
	protected abstract void generateStructure(World world, Random rand, BlockPos pos);

	protected int calculateGenerationHeight(int x, int z) {
		int y = world.getHeight();
		if (underground) {
			int delta = maxHeight - minHeight;
			y = minHeight + rand.nextInt(delta + 1);
		} else {		
			boolean foundGround = false;
			
			while (!foundGround && y-- >= 0) {
				Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
				foundGround = block != Blocks.AIR;
			}
		}
		
		return MMath.clamp(y + 1, 0, world.getHeight());
	}

	public List<Biome.SpawnListEntry> getSpawnList() {
		return spawnList;
	}

	protected boolean canSpawnStructureAtCoords(BlockPos pos) {
		if (!canSpawn()) { 
			return false; 
		}
		
		MBiome biome = (MBiome)worldProvider.getBiomeForCoords(pos);
		
		for (MBiome currentBiome : allowedBiomes) {
			if (biome.equals(currentBiome)) {
				//return genBase.getNearestStructurePos(world, pos, false) != null;
				return MMath.roll(100);
			}
		}
		
		return false;
	}

	protected boolean canSpawn() {
		return !world.isRemote;
	}
	
	public boolean isInsideStructure(BlockPos pos) {
		return provider.getStructure(pos).equals(name);
	}
}