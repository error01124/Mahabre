package ru.d78boga.mahabre.world.gen.structure;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.TemplateManager;
import ru.d78boga.mahabre.util.MMath;
import ru.d78boga.mahabre.world.biome.MBiome;

public abstract class MGenStructure {
	public String registryName;
	public String name;
	protected List<SpawnListEntry> spawnList = Lists.newArrayList();
	protected List<MBiome> allowedBiomes = Lists.newArrayList();
	protected Random rand = new Random();
	protected World world;
	protected WorldProvider worldProvider;
	protected MinecraftServer mcServer;
	protected TemplateManager manager;
	protected MStructuresProvider provider;
	protected MStructureProperties properties;
	protected BlockPos startPos;
	protected PlacementSettings placementSettings = (new PlacementSettings()).setChunk(null).setIgnoreEntities(false).setIgnoreStructureBlock(false).setMirror(Mirror.NONE).setRotation(Rotation.NONE);

	public MGenStructure(MStructureProperties properties, String registryName, String name, MStructuresProvider provider) {
		this.registryName = registryName;
		this.name = name;
		this.provider = provider;
		this.properties = properties;
		this.world = provider.world;
		worldProvider = world.provider;
		mcServer = world.getMinecraftServer();
		WorldServer worldServer = mcServer.getWorld(0);
		manager = worldServer.getStructureTemplateManager();
	}

	public boolean generate(int chunkX, int chunkZ) {
		BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
		return generate(pos);
	}

	public boolean generate(BlockPos pos) {
		if (canSpawnStructureAtCoords(pos)) {			
			pos = calculateSpawnPosition(pos);
			generateStructure(pos);
			generateLoot();
			return true;
		}
		
		return false;
	}

	protected BlockPos calculateSpawnPosition(BlockPos pos) {
		return properties.applyHeight(pos.getX(), pos.getZ(), rand, world);
	}
	
	protected abstract void generateStructure(BlockPos pos);

	protected abstract void generateLoot();
	
	public ResourceLocation getLoot() {
		return properties.getLoot(registryName);
	}
	
	public List<SpawnListEntry> getSpawnList() {
		return spawnList;
	}

	protected boolean canSpawnStructureAtCoords(BlockPos pos) {
		if (canSpawn()) {			
			MBiome biome = (MBiome)worldProvider.getBiomeForCoords(pos);
			
			for (MBiome currentBiome : allowedBiomes) {
				if (biome.equals(currentBiome)) {
					return MMath.roll(100);
				}
			}
			
		}
		
		return false;
	}

	protected boolean canSpawn() {
		return !world.isRemote;
	}
	
	public boolean isInsideStructure(BlockPos pos) {
		return provider.getStructureName(pos).equals(name);
	}
}