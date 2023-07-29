package ru.d78boga.mahabre.world.gen.structure;

import java.util.List;
import java.util.Random;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import ru.d78boga.mahabre.mMath;
import ru.d78boga.mahabre.util.Util;
import ru.d78boga.mahabre.world.biome.mBiome;

public abstract class mWorldGenStructure extends WorldGenerator implements IStructure {
	public String registryName;
	public String name;
	protected mStructureCache cache;
	protected List<Biome.SpawnListEntry> spawnList = Lists.newArrayList();
	protected List<mBiome> allowedBiomes = Lists.newArrayList();
	protected Random rand = new Random();
	protected World world;
	protected WorldProvider worldProvider;
	private Template template;
	private BlockPos templateSize;
	private ResourceLocation lootLocation;

	public mWorldGenStructure(String registryName, String name, World world) {
		this.registryName = registryName;
		this.name = name;
		this.world = world;
		worldProvider = world.provider;
		cache = new mStructureCache();
		MinecraftServer mcServer = world.getMinecraftServer();
		TemplateManager manager = worldServer.getStructureTemplateManager();
		ResourceLocation location = Util.locate(registryName);
		template = manager.get(mcServer, location);
		templateSize = template.getSize();
		lootLocation = Util.locate("chests/" + registryName);
	}

	public boolean generate(World worldIn, Random rand, int chunkX, int chunkZ) {
		int x = chunkX * 16 + 8 + templateSize.getX() / 2;
		int z = chunkZ * 16 + 8 + templateSize.getZ() / 2;
		BlockPos pos = new BlockPos(x, 0, z);
		return generate(worldIn, rand, pos);
	}
	
	public boolean generate(World worldIn, Random rand, BlockPos pos) {
		if (!canSpawnStructureAtCoords(pos))
			return false;

		generateStructure(worldIn, rand, pos);
		return true;
	}

	private void generateStructure(World world, Random rand, BlockPos pos) {
		if (template != null) {
			if (!world.isRemote) {
				pos = findFlatSurface(pos);
				IBlockState state = world.getBlockState(pos);
				world.notifyBlockUpdate(pos, state, state, 3);
				template.addBlocksToWorldChunk(world, pos, new PlacementSettings());
				generateLoot(pos);
				cache.setStructure(pos, name);
			}
		}
	}

	protected void generateLoot(BlockPos pos) {
		for (int x = 0; x <= template.getSize().getX(); x++) {
			for (int y = 0; y <= template.getSize().getY(); y++) {
				for (int z = 0; z <= template.getSize().getZ(); z++) {
					BlockPos lootContainerPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);

					if (world.getTileEntity(lootContainerPos) != null) {
						if (world.getTileEntity(lootContainerPos) instanceof TileEntityChest) {
							TileEntityChest chest = (TileEntityChest) world.getTileEntity(lootContainerPos);
							chest.setLootTable(lootLocation, rand.nextLong());
						}
					}
				}
			}
		}
	}

	protected BlockPos findFlatSurface(BlockPos pos) {
		int sizeX = templateSize.getX();
		int sizeZ = templateSize.getZ();
		return findFlatSurface(pos.getX(), pos.getZ(), sizeX, sizeZ, pos);
	}

	private BlockPos findFlatSurface(int x, int z, int sizeX, int sizeZ, BlockPos pos) {
		int y = calculateGenerationHeight(x, z);
		boolean flag = true;

		for (int x1 = 0; x1 < sizeX; x1++) {
			for (int z1 = 0; z1 < sizeZ; z1++) {
				pos = new BlockPos(x + x1, y, z + z1);
				boolean isAir = world.isAirBlock(pos);
				flag &= isAir;
			}
		}

		if (!flag)
			return findFlatSurface(x - 1, z - 1, sizeX, sizeZ, pos);

		return pos;
	}

	private int calculateGenerationHeight(int x, int z) {
		int y = world.getHeight();
		boolean foundGround = false;

		while (!foundGround && y-- >= 0) {
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			foundGround = block != Blocks.AIR;
		}
		return y + 1;
	}

	public List<Biome.SpawnListEntry> getSpawnList() {
		return spawnList;
	}

	protected boolean canSpawnStructureAtCoords(BlockPos pos) {
		mBiome biome1 = (mBiome)worldProvider.getBiomeForCoords(pos);
		
		for (mBiome biome : allowedBiomes) {
			if (biome1.equals(biome)) 
				return mMath.roll(1000);
		}
		
		return false;
	}

	public boolean isInsideStructure(BlockPos pos) {
		if (world == null) {
			return false;
		} else {
			return cache.getStructure(pos) == name;
		}
	}
}